package com.recuperat.adminpro.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    private String direccion;
    
    private String telefono;
    
    @Column(name = "whatsapp_business")
    private String whatsappBusiness;
    
    // Getters and Setters omitted for brevity
}
