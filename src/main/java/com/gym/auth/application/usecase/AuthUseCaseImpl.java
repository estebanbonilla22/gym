package com.gym.auth.application.usecase;

import com.gym.auth.application.dto.AuthResponse;
import com.gym.auth.application.dto.LoginRequest;
import com.gym.auth.application.dto.RegisterRequest;
import com.gym.auth.domain.model.User;
import com.gym.auth.domain.ports.out.UserRepositoryPort;
import com.gym.shared.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public Mono<AuthResponse> register(RegisterRequest request) {
        return userRepositoryPort.findByEmail(request.getEmail())
                .flatMap(existing -> Mono.<AuthResponse>error(new RuntimeException("El email ya está registrado")))
                .switchIfEmpty(Mono.defer(() -> {
                    User user = User.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(request.getRole())
                            .build();

                    return userRepositoryPort.save(user)
                            .map(saved -> new AuthResponse(
                                    jwtService.generateToken(saved.getEmail(), saved.getRole()),
                                    "Bearer",
                                    saved.getRole()
                            ));
                }));
    }

    @Override
    public Mono<AuthResponse> login(LoginRequest request) {
        return userRepositoryPort.findByEmail(request.getEmail())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        return Mono.error(new RuntimeException("Credenciales inválidas"));
                    }

                    return Mono.just(new AuthResponse(
                            jwtService.generateToken(user.getEmail(), user.getRole()),
                            "Bearer",
                            user.getRole()
                    ));
                });
    }
}