package com.gym.sedes.infrastructure.adapters.out.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SedeR2dbcRepository extends ReactiveCrudRepository<SedeEntity, Long> {
}