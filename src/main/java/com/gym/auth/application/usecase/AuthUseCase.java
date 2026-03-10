package com.gym.auth.application.usecase;

import com.gym.auth.application.dto.AuthResponse;
import com.gym.auth.application.dto.LoginRequest;
import com.gym.auth.application.dto.RegisterRequest;
import reactor.core.publisher.Mono;

public interface AuthUseCase {
    Mono<AuthResponse> register(RegisterRequest request);
    Mono<AuthResponse> login(LoginRequest request);
}