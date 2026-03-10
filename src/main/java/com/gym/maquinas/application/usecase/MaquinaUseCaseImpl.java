package com.gym.maquinas.application.usecase;

import com.gym.maquinas.domain.model.Maquina;
import com.gym.maquinas.domain.ports.out.MaquinaRepositoryPort;
import com.gym.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MaquinaUseCaseImpl implements MaquinaUseCase {

    private final MaquinaRepositoryPort repositoryPort;

    @Override
    public Mono<Maquina> create(Maquina maquina) {
        return repositoryPort.save(maquina);
    }

    @Override
    public Flux<Maquina> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Mono<Maquina> findById(Long id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Máquina no encontrada con id: " + id)));
    }

    @Override
    public Mono<Maquina> update(Long id, Maquina maquina) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Máquina no encontrada con id: " + id)))
                .flatMap(existing -> {
                    existing.setNombre(maquina.getNombre());
                    existing.setTipo(maquina.getTipo());
                    existing.setMarca(maquina.getMarca());
                    existing.setEstado(maquina.getEstado());
                    existing.setSedeId(maquina.getSedeId());
                    return repositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Máquina no encontrada con id: " + id)))
                .flatMap(existing -> repositoryPort.deleteById(id));
    }

    @Override
    public Flux<Maquina> findBySedeId(Long sedeId) {
        return repositoryPort.findBySedeId(sedeId);
    }
}