package com.example.TienDatShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", nullable = false)
    private Products productId;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;


}
