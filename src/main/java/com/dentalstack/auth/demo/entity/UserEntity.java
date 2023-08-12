package com.dentalstack.auth.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "user")
@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number",unique = true)
    private String phoneNumber;

}
