package com.gym.maquinas.infrastructure.adapters.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("maquinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaquinaEntity {

    @Id
    private Long id;
    private String nombre;
    private String tipo;
    private String marca;
    private String estado;

    @Column("sede_id")
    private Long sedeId;
}