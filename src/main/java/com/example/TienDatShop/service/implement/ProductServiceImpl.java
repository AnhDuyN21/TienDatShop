package com.example.TienDatShop.service.implement;


import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;
import com.example.TienDatShop.entity.Brand;
import com.example.TienDatShop.entity.Image;
import com.example.TienDatShop.entity.Product;
import com.example.TienDatShop.entity.ProductBrand;
import com.example.TienDatShop.entity.enumeration.ProductStatus;
import com.example.TienDatShop.exception.BadRequestException;
import com.example.TienDatShop.repository.BrandRepository;
import com.example.TienDatShop.repository.ProductBrandRepository;
import com.example.TienDatShop.repository.ProductRepository;
import com.example.TienDatShop.service.ProductService;
import com.example.TienDatShop.service.mapper.ProductBrandMapper;
import com.example.TienDatShop.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final BrandRepository brandRepository;
    private final ProductBrandMapper productBrandMapper;
    private final ProductBrandRepository productBrandRepo;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO getById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("product not found with id: " + id));
        return mapper.toDto(product);
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        List<Product> products = repository.findAll();
        return mapper.toDto(products);
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = mapper.toEntity(dto);

        if (product.getDetail() != null) product.getDetail().setProduct(product);
        else throw new IllegalStateException("ProductDetail entity was not initialized.");

        product.setStatus(ProductStatus.AVAILABLE);

        List<String> imageUrls = dto.getImageUrls();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            Product finalProduct = product;
            List<Image> images = imageUrls.stream()
                    .map(url -> {
                        Image image = new Image();
                        image.setImageUrl(url);
                        image.setProductId(finalProduct);
                        return image;
                    })
                    .collect(Collectors.toList());

            product.setImages(images);
        }

        product = repository.save(product);

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new BadRequestException("Brand not found"));

        ProductBrand productBrand = productBrandMapper.toEntity(product, brand);
        productBrandRepo.save(productBrand);

        return mapper.toDto(product);
    }


//    @Override
//    @Transactional
//    public ProductResponseDTO update(ProductRequestDTO dto, Long productId) {
//
//        Product product = repository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        if (dto.getBrandId() != null) {
//            Brand brand = brandRepository.findById(dto.getBrandId())
//                    .orElseThrow(() -> new RuntimeException("Brand not found"));
//            product.setBrandId(brand);
//        }
//        mapper.updateProductFromDTO(dto, product);
//        product = repository.save(product);
//
//        // ProductDetail
//        ProductDetail detail = productDetailRepository.findByProductId(product)
//                .orElseThrow(() -> new RuntimeException("ProductDetail not found"));
//
//        mapper.updateDetailsFromDTO(dto, detail);
//        productDetailRepository.save(detail);
//
//        // Map sang DTO
//        return mapper.toDto(product, detail);
//    }

}

