package com.gym.sedes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sede {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String estado;
}