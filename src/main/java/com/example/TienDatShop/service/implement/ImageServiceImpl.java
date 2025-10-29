package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.image.ImageRequestDTO;
import com.example.TienDatShop.dto.image.ImageResponseDTO;
import com.example.TienDatShop.entity.Images;
import com.example.TienDatShop.entity.Products;
import com.example.TienDatShop.repository.ImageRepository;
import com.example.TienDatShop.repository.ProductRepository;
import com.example.TienDatShop.service.ImageService;
import com.example.TienDatShop.service.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    private final ProductRepository productRepository;
    private final ImageMapper mapper;

    @Override
    @Transactional
    public List<ImageResponseDTO> createImages(ImageRequestDTO dto) {
        var product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Images> images = dto.getImageUrl().stream()
                .map(url -> {
                    Images img = new Images();
                    img.setImageUrl(url);
                    img.setProductId(product);
                    return img;
                })
                .collect(Collectors.toList());

        repository.saveAll(images);
        return images.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ImageResponseDTO> getByProduct(Long productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return repository.findByProductId(product)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
