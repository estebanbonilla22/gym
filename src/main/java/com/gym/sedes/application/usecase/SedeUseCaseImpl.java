package com.gym.sedes.application.usecase;

import com.gym.sedes.domain.model.Sede;
import com.gym.sedes.domain.ports.out.SedeRepositoryPort;
import com.gym.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SedeUseCaseImpl implements SedeUseCase {

    private final SedeRepositoryPort repositoryPort;

    @Override
    public Mono<Sede> create(Sede sede) {
        return repositoryPort.save(sede);
    }

    @Override
    public Flux<Sede> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Mono<Sede> findById(Long id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sede no encontrada con id: " + id)));
    }

    @Override
    public Mono<Sede> update(Long id, Sede sede) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sede no encontrada con id: " + id)))
                .flatMap(existing -> {
                    existing.setNombre(sede.getNombre());
                    existing.setDireccion(sede.getDireccion());
                    existing.setCiudad(sede.getCiudad());
                    existing.setTelefono(sede.getTelefono());
                    existing.setEstado(sede.getEstado());
                    return repositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sede no encontrada con id: " + id)))
                .flatMap(existing -> repositoryPort.deleteById(id));
    }
}