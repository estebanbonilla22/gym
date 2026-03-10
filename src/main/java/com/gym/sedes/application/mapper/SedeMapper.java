package com.gym.sedes.application.mapper;

import com.gym.sedes.application.dto.SedeRequestDTO;
import com.gym.sedes.application.dto.SedeResponseDTO;
import com.gym.sedes.domain.model.Sede;
import com.gym.sedes.infrastructure.adapters.out.persistence.SedeEntity;
import org.springframework.stereotype.Component;

@Component
public class SedeMapper {

    public Sede toDomain(SedeRequestDTO dto) {
        return Sede.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .ciudad(dto.getCiudad())
                .telefono(dto.getTelefono())
                .estado(dto.getEstado())
                .build();
    }

    public SedeResponseDTO toResponse(Sede sede) {
        return SedeResponseDTO.builder()
                .id(sede.getId())
                .nombre(sede.getNombre())
                .direccion(sede.getDireccion())
                .ciudad(sede.getCiudad())
                .telefono(sede.getTelefono())
                .estado(sede.getEstado())
                .build();
    }

    public SedeEntity toEntity(Sede sede) {
        return SedeEntity.builder()
                .id(sede.getId())
                .nombre(sede.getNombre())
                .direccion(sede.getDireccion())
                .ciudad(sede.getCiudad())
                .telefono(sede.getTelefono())
                .estado(sede.getEstado())
                .build();
    }

    public Sede toDomain(SedeEntity entity) {
        return Sede.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .direccion(entity.getDireccion())
                .ciudad(entity.getCiudad())
                .telefono(entity.getTelefono())
                .estado(entity.getEstado())
                .build();
    }
}