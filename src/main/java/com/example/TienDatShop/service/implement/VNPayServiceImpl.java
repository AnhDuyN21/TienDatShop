package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.config.VNPayConfig;
import com.example.TienDatShop.dto.payment.PaymentRequestDTO;
import com.example.TienDatShop.dto.payment.PaymentResponseDTO;
import com.example.TienDatShop.entity.*;
import com.example.TienDatShop.entity.enumeration.CartStatus;
import com.example.TienDatShop.entity.enumeration.OrderStatus;
import com.example.TienDatShop.exception.BadRequestException;
import com.example.TienDatShop.repository.CartRepository;
import com.example.TienDatShop.repository.OrderRepository;
import com.example.TienDatShop.repository.ProductRepository;
import com.example.TienDatShop.repository.PromotionRepository;
import com.example.TienDatShop.service.PaymentService;
import com.example.TienDatShop.service.mapper.OrderMapper;
import com.example.TienDatShop.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VNPayServiceImpl implements PaymentService {
    private final VNPayConfig vnPayConfig;
    private final CartRepository cartRepository;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepository;
    private final PromotionRepository promotionRepository;
    private final OrderMapper mapper;

    @Override
    @Transactional
    public String createPaymentUrl_VNPay(PaymentRequestDTO dto, HttpServletRequest request) {

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new BadRequestException("Cart id not found"));
        cart.setStatus(CartStatus.PENDING_PAYMENT);
        cartRepository.save(cart);

        long vnpAmount = dto.getAmount() * 100;

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(vnpAmount));
        vnpParams.put("vnp_CurrCode", "VND");

        String vnpTxnRef = dto.getCartId() + "_" + System.currentTimeMillis();
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", dto.getCartInfo());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnpParams.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
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
        String responseCode = params.get("vnp_ResponseCode");
        String transactionStatus = params.get("vnp_TransactionStatus");
        String TxnRef = params.get("vnp_TxnRef");
        Long cartId = Long.valueOf(TxnRef.split("_")[0]);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new BadRequestException("Cart id not found"));
        boolean isSuccess = "00".equals(responseCode) && "00".equals(transactionStatus);
        if (isSuccess) {
            cart.setStatus(CartStatus.PAID);
            cartRepository.save(cart);
            createOrder(cart);
            return PaymentResponseDTO.success(cartId,
                    Long.valueOf(params.get("vnp_Amount")),
                    params.get("vnp_BankCode"),
                    params.get("vnp_TransactionNo"),
                    responseCode);
        } else {
            cart.setStatus(CartStatus.CREATED);
            cartRepository.save(cart);
        }
        return PaymentResponseDTO.failure(
                responseCode != null ? responseCode : transactionStatus,
                cartId
        );
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

    @Transactional
    protected void processInventory(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Long productId = item.getProduct().getId();
            Product product = productRepo.findByIdWithLock(productId)
                    .orElseThrow(() -> new BadRequestException("Product not found"));
            int orderedQuantity = item.getQuantity();
            int productQuantity = product.getDetail().getStockQuantity();
            if (productQuantity < orderedQuantity) {
                throw new RuntimeException("Product '" + product.getName() + "' is out of stock.");
            }
            product.getDetail().setStockQuantity(productQuantity - orderedQuantity);
        }
    }

    @Transactional
    protected void createOrder(Cart cart){
        Order order = mapper.mapCartToOrder(cart);
        order.setOrderDate(LocalDateTime.now());
        processInventory(cart);
        if (cart.getPromotionCode() != null && !cart.getPromotionCode().isEmpty()) {
            updatePromotionUsage(cart);
        }
        orderRepository.save(order);
    }

    @Transactional
    protected void updatePromotionUsage(Cart cart) {
        Promotion promo = promotionRepository.findByCodeForUpdate(cart.getPromotionCode())
                .orElseThrow(() -> new BadRequestException("Promotion code not found"));

        if (promo.getUsageLimit() <= 0) {
            throw new BadRequestException("Promotion code has expired.");
        }
        promo.setUsageLimit(promo.getUsageLimit() - 1);
        promotionRepository.save(promo);
    }

}
