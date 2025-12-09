package com.example.TienDatShop.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {
    private boolean success;
    private String message;
    private Long orderId;
    private Long amount;
    private String bankCode;
    private String transactionNo;
    private String responseCode;

    public static PaymentResponseDTO success(
            Long orderId,
            Long amount,
            String bankCode,
            String transactionNo,
            String responseCode) {
        String message = getMessageByResponseCode(responseCode);
        return PaymentResponseDTO.builder()
                .success(true)
                .message(message)
                .orderId(orderId)
                .amount(amount)
                .bankCode(bankCode)
                .transactionNo(transactionNo)
                .responseCode(responseCode)
                .build();
    }

    public static PaymentResponseDTO failure(String responseCode, Long orderId) {
        String message = getMessageByResponseCode(responseCode);
        return PaymentResponseDTO.builder()
                .success(false)
                .message(message)
                .responseCode(responseCode)
                .orderId(orderId)
                .build();
    }

    public static PaymentResponseDTO invalidSignature() {
        return PaymentResponseDTO.builder()
                .success(false)
                .message("Invalid Signature")
                .build();
    }

    private static String getMessageByResponseCode(String code) {
        return switch (code) {
            case "00" -> "Payment success";
            case "07" -> "Transaction suspicious (blocked)";
            case "09" -> "Card not registered for Internet Banking";
            case "10" -> "Card authentication failed";
            case "11" -> "Transaction timeout";
            case "12" -> "Card locked";
            case "13" -> "Wrong OTP";
            case "24" -> "Transaction cancelled";
            case "51" -> "Insufficient balance";
            case "65" -> "Daily transaction limit exceeded";
            case "75" -> "Banking system maintenance";
            case "79" -> "Wrong password too many times";
            default -> "Payment failed";
        };
    }

}
