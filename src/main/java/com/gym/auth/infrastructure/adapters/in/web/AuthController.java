package com.gym.auth.infrastructure.adapters.in.web;

import com.gym.auth.application.dto.AuthResponse;
import com.gym.auth.application.dto.LoginRequest;
import com.gym.auth.application.dto.RegisterRequest;
import com.gym.auth.application.usecase.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@Valid @RequestBody Mono<RegisterRequest> requestMono) {
        return requestMono
                .flatMap(authUseCase::register)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody Mono<LoginRequest> requestMono) {
        return requestMono
                .flatMap(authUseCase::login)
                .map(ResponseEntity::ok);
    }
}