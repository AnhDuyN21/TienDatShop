package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.payment.PaymentRequestDTO;
import com.example.TienDatShop.dto.payment.PaymentResponseDTO;
import com.example.TienDatShop.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO dto, HttpServletRequest request) {
        try {
            String paymentUrl = service.createPaymentUrl_VNPay(dto, request);

            Map<String, String> response = new HashMap<>();
            response.put("paymentUrl", paymentUrl);
            response.put("message", "Tạo URL thanh toán thành công");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi tạo URL thanh toán: " + e.getMessage()));
        }
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<PaymentResponseDTO> vnpayReturn(@RequestParam Map<String, String> params) {

        PaymentResponseDTO response = service.processVNPayReturn(params);

        return ResponseEntity
                .status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
