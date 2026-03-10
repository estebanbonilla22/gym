package com.gym.maquinas.domain.ports.out;

import com.gym.maquinas.domain.model.Maquina;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MaquinaRepositoryPort {
    Mono<Maquina> save(Maquina maquina);
    Flux<Maquina> findAll();
    Mono<Maquina> findById(Long id);
    Flux<Maquina> findBySedeId(Long sedeId);
    Mono<Void> deleteById(Long id);
}