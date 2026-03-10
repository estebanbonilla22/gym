package com.gym.sedes.infrastructure.adapters.in.web;

import com.gym.sedes.application.dto.SedeRequestDTO;
import com.gym.sedes.application.dto.SedeResponseDTO;
import com.gym.sedes.application.mapper.SedeMapper;
import com.gym.sedes.application.usecase.SedeUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sedes")
@RequiredArgsConstructor
public class SedeController {

    private final SedeUseCase useCase;
    private final SedeMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<SedeResponseDTO>> create(@Valid @RequestBody Mono<SedeRequestDTO> requestMono) {
        return requestMono
                .map(mapper::toDomain)
                .flatMap(useCase::create)
                .map(mapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping
    public Flux<SedeResponseDTO> findAll() {
        return useCase.findAll()
                .map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<SedeResponseDTO>> findById(@PathVariable Long id) {
        return useCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<SedeResponseDTO>> update(@PathVariable Long id,
                                                        @Valid @RequestBody Mono<SedeRequestDTO> requestMono) {
        return requestMono
                .map(mapper::toDomain)
                .flatMap(sede -> useCase.update(id, sede))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return useCase.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}