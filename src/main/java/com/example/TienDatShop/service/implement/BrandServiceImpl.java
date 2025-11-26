package com.example.TienDatShop.service.implement;

import com.example.TienDatShop.dto.brand.BrandRequestDTO;
import com.example.TienDatShop.dto.brand.BrandResponseDTO;
import com.example.TienDatShop.entity.Brand;
import com.example.TienDatShop.entity.enumeration.BrandStatus;
import com.example.TienDatShop.exception.BadRequestException;
import com.example.TienDatShop.repository.BrandRepository;
import com.example.TienDatShop.service.BrandService;
import com.example.TienDatShop.service.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;
    private final BrandMapper mapper;

    @Override
    public List<BrandResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new BadRequestException("Brand với id {" + id + "} không tìm thấy"));
    }

    @Override
    @Transactional
    public BrandResponseDTO create(BrandRequestDTO dto) {
        //check trùng phone & email
        if (dto.getEmail() != null && repository.existsByEmail(dto.getEmail()))
            throw new BadRequestException("Email đã được sử dụng bởi brand khác");
        else if (dto.getPhone() != null && repository.existsByPhone(dto.getPhone()))
            throw new BadRequestException("Số điện thoại đã được sử dụng bởi brand khác");

        Brand brand = mapper.toEntity(dto);
        brand.setStatus(BrandStatus.ACTIVE);
        repository.save(brand);
        return mapper.toDto(brand);
    }

    @Override
    @Transactional
    public BrandResponseDTO update(Long id, BrandRequestDTO dto) {
        Brand brand = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Brand với id {" + id + "} không tìm thấy"));

        //check trùng phone & email
        if (dto.getEmail() != null && repository.existsByEmail(dto.getEmail()))
            throw new BadRequestException("Email đã được sử dụng bởi brand khác");
        else if (dto.getPhone() != null && repository.existsByPhone(dto.getPhone()))
            throw new BadRequestException("Số điện thoại đã được sử dụng bởi brand khác");

        mapper.updateBrand(brand, dto);
        repository.save(brand);
        return mapper.toDto(brand);
    }
}
