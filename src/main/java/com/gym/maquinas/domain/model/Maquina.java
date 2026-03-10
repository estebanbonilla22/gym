package com.gym.maquinas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Maquina {
    private Long id;
    private String nombre;
    private String tipo;
    private String marca;
    private String estado;
    private Long sedeId;
}