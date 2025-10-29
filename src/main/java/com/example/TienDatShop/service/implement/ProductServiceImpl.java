package com.example.TienDatShop.service.implement;


import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;
import com.example.TienDatShop.entity.Brands;
import com.example.TienDatShop.entity.Images;
import com.example.TienDatShop.entity.ProductDetails;
import com.example.TienDatShop.entity.Products;
import com.example.TienDatShop.entity.enumeration.ProductStatus;
import com.example.TienDatShop.repository.BrandRepository;
import com.example.TienDatShop.repository.ProductDetailRepository;
import com.example.TienDatShop.repository.ProductRepository;
import com.example.TienDatShop.service.ProductService;
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
    private final ProductDetailRepository productDetailRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO getById(Long id) {
        Products product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with id: " + id));
        ProductDetails details = productDetailRepository.findByProductId(product)
                .orElse(new ProductDetails()); // nếu chưa có details, để trống
        return mapper.toDto(product, details);
    }

    @Override
    public List<ProductResponseDTO> getAll() {
        List<Products> products = repository.findAll();

        return products.stream().map(product -> {
            // Lấy ProductDetails theo productId
            ProductDetails details = productDetailRepository.findByProductId(product)
                    .orElseThrow(() -> new RuntimeException("product detail not found"));

            // Map sang DTO
            return mapper.toDto(product, details);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Brands brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Products product = mapper.toEntity(dto);
        product.setBrandId(brand);

        // Nếu có danh sách ảnh thì tạo entity Images
        if (dto.getImageUrls() != null && !dto.getImageUrls().isEmpty()) {
            Products finalProduct = product;
            List<Images> imageList = dto.getImageUrls().stream()
                    .map(url -> {
                        Images img = new Images();
                        img.setImageUrl(url);
                        img.setProductId(finalProduct);
                        return img;
                    })
                    .collect(Collectors.toList());
            product.setImages(imageList);
        }

        product = repository.save(product);
        // Tạo ProductDetails
        ProductDetails detail = new ProductDetails();
        detail.setProductId(product);
        detail.setIngredients(dto.getIngredients());
        detail.setUsageInstruction(dto.getUsageInstructions());
        detail.setWeight(dto.getWeight());
        detail.setStorageCondition(dto.getStorageCondition());
        detail.setStockQuantity(dto.getStockQuantity());

        productDetailRepository.save(detail);

        //Tạo ResponseDTO kết hợp Product + Detail
        ProductResponseDTO response = mapper.toDto(product, detail);

        return response;
    }

    @Override
    @Transactional
    public ProductResponseDTO update(ProductRequestDTO dto, Long productId) {

        Products product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getBrandId() != null) {
            Brands brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrandId(brand);
        }
        mapper.updateProductFromDTO(dto, product);
        product = repository.save(product);

        // ProductDetails
        ProductDetails detail = productDetailRepository.findByProductId(product)
                .orElseThrow(() -> new RuntimeException("ProductDetail not found"));

        mapper.updateDetailsFromDTO(dto, detail);
        productDetailRepository.save(detail);

        // Map sang DTO
        return mapper.toDto(product, detail);
    }

    @Override
    @Transactional
    public void toggle(Long id, String status) {
        Products product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        if (status == null) return;
        try {
            ProductStatus newStatus = ProductStatus.valueOf(status.toUpperCase());
            product.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + status);
        }

        repository.save(product);
    }
}
