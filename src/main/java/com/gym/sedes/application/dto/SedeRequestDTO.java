package com.gym.sedes.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SedeRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}