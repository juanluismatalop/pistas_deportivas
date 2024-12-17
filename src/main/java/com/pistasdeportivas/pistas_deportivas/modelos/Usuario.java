package com.pistasdeportivas.pistas_deportivas.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true,length = 20)
    private String username;
    @Column(nullable = false, length = 80)
    private String password;    
    @Column(nullable = false, unique = true, length = 80)
    private String email; 
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Rol tipo;
}
