package com.gym.shared.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping(value = {"/", "/health"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of(
                "status", "ok",
                "service", "gym-management-api",
                "message", "API running. Use /auth/login, /api/sedes, /api/maquinas"
        ));
    }
}
