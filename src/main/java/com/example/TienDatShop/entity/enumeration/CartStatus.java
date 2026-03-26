package com.example.TienDatShop.entity.enumeration;

public enum CartStatus {
    CREATED, // Giỏ hàng vừa được tạo
    PENDING_PAYMENT,// Đang chờ thanh toán - Khi chọn phương thức thanh toán online,
    PAYMENT_FAILED, // Thanh toán thất bại
    PAID,           // Đã thanh toán thành công - Cổng thanh toán báo về thành công
    INACTIVE // Đã bị bỏ quên, có thể bị xóa sau này.
}
