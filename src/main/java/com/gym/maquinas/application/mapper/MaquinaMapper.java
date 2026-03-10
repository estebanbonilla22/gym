package com.gym.maquinas.application.mapper;

import com.gym.maquinas.application.dto.MaquinaRequestDTO;
import com.gym.maquinas.application.dto.MaquinaResponseDTO;
import com.gym.maquinas.domain.model.Maquina;
import com.gym.maquinas.infrastructure.adapters.out.persistence.MaquinaEntity;
import org.springframework.stereotype.Component;

@Component
public class MaquinaMapper {

    public Maquina toDomain(MaquinaRequestDTO dto) {
        return Maquina.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .marca(dto.getMarca())
                .estado(dto.getEstado())
                .sedeId(dto.getSedeId())
                .build();
    }

    public MaquinaResponseDTO toResponse(Maquina maquina) {
        return MaquinaResponseDTO.builder()
                .id(maquina.getId())
                .nombre(maquina.getNombre())
                .tipo(maquina.getTipo())
                .marca(maquina.getMarca())
                .estado(maquina.getEstado())
                .sedeId(maquina.getSedeId())
                .build();
    }

    public MaquinaEntity toEntity(Maquina maquina) {
        return MaquinaEntity.builder()
                .id(maquina.getId())
                .nombre(maquina.getNombre())
                .tipo(maquina.getTipo())
                .marca(maquina.getMarca())
                .estado(maquina.getEstado())
                .sedeId(maquina.getSedeId())
                .build();
    }

    public Maquina toDomain(MaquinaEntity entity) {
        return Maquina.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .tipo(entity.getTipo())
                .marca(entity.getMarca())
                .estado(entity.getEstado())
                .sedeId(entity.getSedeId())
                .build();
    }
}