package com.gym.sedes.application.usecase;

import com.gym.sedes.domain.model.Sede;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SedeUseCase {
    Mono<Sede> create(Sede sede);
    Flux<Sede> findAll();
    Mono<Sede> findById(Long id);
    Mono<Sede> update(Long id, Sede sede);
    Mono<Void> delete(Long id);
}