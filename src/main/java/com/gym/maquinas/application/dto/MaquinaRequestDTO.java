package com.gym.maquinas.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaquinaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El sedeId es obligatorio")
    private Long sedeId;
}