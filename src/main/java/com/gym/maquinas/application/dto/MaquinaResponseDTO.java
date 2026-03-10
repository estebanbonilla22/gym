package com.gym.maquinas.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaquinaResponseDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String marca;
    private String estado;
    private Long sedeId;
}