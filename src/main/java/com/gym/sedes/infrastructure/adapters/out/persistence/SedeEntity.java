package com.gym.sedes.infrastructure.adapters.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("sedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SedeEntity {

    @Id
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String estado;
}