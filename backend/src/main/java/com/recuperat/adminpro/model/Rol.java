package com.recuperat.adminpro.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // JSON or separate table for matrix of permissions could be used
}
