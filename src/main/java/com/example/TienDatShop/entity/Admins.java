package com.example.TienDatShop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts accountId;

}
