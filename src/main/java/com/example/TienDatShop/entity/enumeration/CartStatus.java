package com.example.TienDatShop.entity.enumeration;

public enum CartStatus {
    ACTIVE, //Người dùng đang thêm sản phẩm.
    COMPLETED, //Đã tạo thành Order, không thể chỉnh sửa
    INACTIVE // Đã bị bỏ quên, có thể bị xóa sau này.
}
