package com.gym.sedes.domain.ports.out;

import com.gym.sedes.domain.model.Sede;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SedeRepositoryPort {
    Mono<Sede> save(Sede sede);
    Flux<Sede> findAll();
    Mono<Sede> findById(Long id);
    Mono<Void> deleteById(Long id);
}