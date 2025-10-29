package com.example.TienDatShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Brands")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brands {
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
}
