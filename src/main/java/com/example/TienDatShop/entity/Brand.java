package com.example.TienDatShop.entity;

import com.example.TienDatShop.entity.enumeration.BrandStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Brand")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BrandStatus status;

    @OneToMany(mappedBy = "brand")
    private List<ProductBrand> productBrands;
}
