package com.gym.auth.domain.ports.out;

import com.gym.auth.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
    Mono<User> save(User user);
    Mono<User> findByEmail(String email);
}