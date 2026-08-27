package com.recuperat.adminpro.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String credenciales; // email/username

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToMany
    @JoinTable(
        name = "usuario_sucursal",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "sucursal_id")
    )
    private List<Sucursal> sucursalesAsignadas;

    private boolean activo;
    
    @Column(name = "two_fa_habilitado")
    private boolean twoFaHabilitado;
}
