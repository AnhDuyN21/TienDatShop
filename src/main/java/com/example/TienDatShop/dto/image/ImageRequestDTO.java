package com.example.TienDatShop.dto.image;

import lombok.Data;

import java.util.List;

@Data
public class ImageRequestDTO {
    private Long productId;
    private List<String> imageUrl;
}
