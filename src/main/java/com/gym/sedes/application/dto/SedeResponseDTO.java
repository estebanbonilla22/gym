package com.gym.sedes.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SedeResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String estado;
}