package com.example.TienDatShop.entity.enumeration;

public enum ReviewStatus {
    PUBLISHED, //Nếu review hợp lệ, nó sẽ được hiển thị công khai cho mọi người thấy.
    REJECTED //Nếu review vi phạm nội dung (spam, xúc phạm…), nó sẽ bị từ chối và không hiển thị.
}
