package com.recuperat.adminpro.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String folio;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String telefono;

    private String correo;
    
    private String direccion;

    @Column(name = "contacto_emergencia")
    private String contactoEmergencia;

    @ManyToOne
    @JoinColumn(name = "sucursal_origen_id")
    private Sucursal sucursalOrigen;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "tipo_ingreso")
    private String tipoIngreso; // derivacion / valoracion interna

    private boolean aseguradora;
}
