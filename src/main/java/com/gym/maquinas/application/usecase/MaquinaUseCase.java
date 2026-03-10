package com.gym.maquinas.application.usecase;

import com.gym.maquinas.domain.model.Maquina;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MaquinaUseCase {
    Mono<Maquina> create(Maquina maquina);
    Flux<Maquina> findAll();
    Mono<Maquina> findById(Long id);
    Mono<Maquina> update(Long id, Maquina maquina);
    Mono<Void> delete(Long id);
    Flux<Maquina> findBySedeId(Long sedeId);
}