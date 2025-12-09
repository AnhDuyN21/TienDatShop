package com.example.TienDatShop.service;

import com.example.TienDatShop.dto.payment.PaymentRequestDTO;
import com.example.TienDatShop.dto.payment.PaymentResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    String createPaymentUrl_VNPay(PaymentRequestDTO dto, HttpServletRequest request);

    PaymentResponseDTO processVNPayReturn(Map<String, String> params);


}
