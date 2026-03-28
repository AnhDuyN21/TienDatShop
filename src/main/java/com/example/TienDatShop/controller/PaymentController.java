package com.example.TienDatShop.controller;

import com.example.TienDatShop.dto.payment.PaymentRequestDTO;
import com.example.TienDatShop.dto.payment.PaymentResponseDTO;
import com.example.TienDatShop.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    @Value("${app.frontend.url}")
    private String frontendUrl;

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
    public void vnpayReturn(@RequestParam Map<String, String> params, HttpServletResponse redirect) throws IOException {

        PaymentResponseDTO response = service.processVNPayReturn(params);
        String status = response.isSuccess() ? "success" : "failed";
        String responseCode = response.getResponseCode();
        Long cartId = response.getCartId();
        String message = response.getMessage();

        String redirectUrl = String.format("%s/payment-result?status=%s&cartId=%s&code=%s&message=%s",
                frontendUrl, status, cartId, responseCode, message);

        redirect.sendRedirect(redirectUrl);
    }
}
