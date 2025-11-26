package com.example.TienDatShop.entity.enumeration;

public enum CartStatus {
    WAITING, // Đợi nhân viên xác nhận
    APPROVED, // Nhân viên đã xác nhận
    COMPLETED, //Đã tạo thành Order, không thể chỉnh sửa
    INACTIVE // Đã bị bỏ quên, có thể bị xóa sau này.
}
