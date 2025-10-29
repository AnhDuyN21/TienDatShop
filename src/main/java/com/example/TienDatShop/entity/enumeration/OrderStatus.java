package com.example.TienDatShop.entity.enumeration;

public enum OrderStatus {
    CREATED,        // Đơn hàng vừa được tạo (chưa thanh toán) - Khi user nhấn "Đặt hàng"
    PENDING_PAYMENT,// Đang chờ thanh toán - Khi chọn phương thức thanh toán online
    PAID,           // Đã thanh toán thành công - Cổng thanh toán báo về thành công
    PROCESSING,     // Đang xử lý đơn hàng - Nhân viên/ hệ thống xác nhận và chuẩn bị hàng
    SHIPPED,        // Đã giao cho đơn vị vận chuyển - Khi đơn hàng được giao đi
    DELIVERED,      // Đã giao tới khách hàng - Bên vận chuyển báo giao thành công
    COMPLETED,      // Hoàn tất - Khi khách xác nhận hoặc quá hạn đổi trả
    CANCELLED,      // Đơn bị hủy - User hoặc hệ thống hủy đơn (chưa giao)
    REFUNDED        // Hoàn tiền - Khi đơn bị trả hàng/hoàn tiền sau khi giao
}
