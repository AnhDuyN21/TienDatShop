package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.config.VNPayConfig;
import com.example.TienDatShop.dto.payment.PaymentRequestDTO;
import com.example.TienDatShop.dto.payment.PaymentResponseDTO;
import com.example.TienDatShop.entity.Order;
import com.example.TienDatShop.entity.enumeration.OrderStatus;
import com.example.TienDatShop.exception.BadRequestException;
import com.example.TienDatShop.repository.OrderRepository;
import com.example.TienDatShop.service.PaymentService;
import com.example.TienDatShop.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements PaymentService {
    private final VNPayConfig vnPayConfig;
    private final OrderRepository orderRepo;

    @Override
    @Transactional
    public String createPaymentUrl_VNPay(PaymentRequestDTO dto, HttpServletRequest request) {

        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new BadRequestException("Order id not found"));
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        orderRepo.save(order);

        long vnpAmount = dto.getAmount() * 100;

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(vnpAmount));
        vnpParams.put("vnp_CurrCode", "VND");

        String vnpTxnRef = dto.getOrderId() + "_" + System.currentTimeMillis();
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", dto.getOrderInfo());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnpParams.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParams.put("vnp_CreateDate", vnpCreateDate);

        calendar.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(calendar.getTime());
        vnpParams.put("vnp_ExpireDate", vnpExpireDate);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();
            if (fieldValue != null && !fieldValue.isEmpty()) {

                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII))
                        .append('&');

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII))
                        .append('&');
            }
        }

        if (!query.isEmpty()) {
            query.setLength(query.length() - 1);
            hashData.setLength(hashData.length() - 1);
        }

        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
        String queryUrl = query + "&vnp_SecureHash=" + vnpSecureHash;

        return vnPayConfig.getPaymentUrl() + "?" + queryUrl;

    }

    @Override
    @Transactional
    public PaymentResponseDTO processVNPayReturn(Map<String, String> params) {
        if (!verifyReturn_VNPay(params)) {
            return PaymentResponseDTO.invalidSignature();
        }
        Long amount = Long.valueOf(params.get("vnp_Amount"));
        String bankCode = params.get("vnp_BankCode");
        String transactionNo = params.get("vnp_TransactionNo");
        String responseCode = params.get("vnp_ResponseCode");
        String TxnRef = params.get("vnp_TxnRef");
        Long orderId = Long.valueOf(TxnRef.split("_")[0]);
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new BadRequestException("Order id not found"));
        if ("00".equals(responseCode)) {
            order.setStatus(OrderStatus.PAID);
            orderRepo.save(order);
            return PaymentResponseDTO.success(orderId, amount, bankCode, transactionNo, responseCode);
        } else {
            order.setStatus(OrderStatus.PENDING_PAYMENT);
            orderRepo.save(order);
        }
        return PaymentResponseDTO.failure(responseCode, orderId);
    }

    boolean verifyReturn_VNPay(Map<String, String> params) {
        String receivedHash = params.get("vnp_SecureHash");
        if (receivedHash == null) {
            return false;
        }

        String calculatedHash = calculateSecureHash(params);
        return calculatedHash.equals(receivedHash);
    }

    private String calculateSecureHash(Map<String, String> params) {
        Map<String, String> sortedParams = params.entrySet().stream()
                .filter(e -> !e.getKey().equals("vnp_SecureHash"))
                .filter(e -> !e.getKey().equals("vnp_SecureHashType"))
                .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        TreeMap::new
                ));

        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            hashData.append(entry.getKey())
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII))
                    .append('&');
        }

        if (!hashData.isEmpty()) {
            hashData.setLength(hashData.length() - 1);
        }

        return VNPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
    }

}
