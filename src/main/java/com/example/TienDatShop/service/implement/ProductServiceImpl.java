package com.example.TienDatShop.service.implement;


import com.example.TienDatShop.dto.product.ProductRequestDTO;
import com.example.TienDatShop.dto.product.ProductResponseDTO;
import com.example.TienDatShop.entity.Brand;
import com.example.TienDatShop.entity.Image;
import com.example.TienDatShop.entity.Product;
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
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with id: " + id));
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
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Product product = mapper.toEntity(dto);

        if (product.getDetail() != null) {
            // Thiết lập tham chiếu ngược: ProductDetail.product = Product
            product.getDetail().setProduct(product);
        } else {
            // Tùy chọn: ném lỗi nếu ProductDetail là bắt buộc
            throw new IllegalStateException("ProductDetail entity was not initialized.");
        }
        
        product.setBrand(brand);
        product.setStatus(ProductStatus.AVAILABLE);


        // 3. XỬ LÝ ẢNH (Image)
        List<String> imageUrls = dto.getImageUrls();
        if (imageUrls != null && !imageUrls.isEmpty()) {
            Product finalProduct = product;
            List<Image> images = imageUrls.stream()
                    .map(url -> {
                        Image image = new Image();
                        image.setImageUrl(url);
                        // Thiết lập mối quan hệ: Image.product = Product
                        image.setProductId(finalProduct);
                        return image;
                    })
                    .collect(Collectors.toList());

            product.setImages(images);
        }

        // 4. Lưu Entity (Hibernate sẽ lưu ProductDetail và Images nhờ CascadeType.ALL)
        product = repository.save(product);

        // 5. Ánh xạ Entity đã lưu sang DTO phản hồi
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

