package com.gym.maquinas.infrastructure.adapters.in.web;

import com.gym.maquinas.application.dto.MaquinaRequestDTO;
import com.gym.maquinas.application.dto.MaquinaResponseDTO;
import com.gym.maquinas.application.mapper.MaquinaMapper;
import com.gym.maquinas.application.usecase.MaquinaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/maquinas")
@RequiredArgsConstructor
public class MaquinaController {

    private final MaquinaUseCase useCase;
    private final MaquinaMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<MaquinaResponseDTO>> create(@Valid @RequestBody Mono<MaquinaRequestDTO> requestMono) {
        return requestMono
                .map(mapper::toDomain)
                .flatMap(useCase::create)
                .map(mapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping
    public Flux<MaquinaResponseDTO> findAll() {
        return useCase.findAll()
                .map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MaquinaResponseDTO>> findById(@PathVariable Long id) {
        return useCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MaquinaResponseDTO>> update(@PathVariable Long id,
                                                           @Valid @RequestBody Mono<MaquinaRequestDTO> requestMono) {
        return requestMono
                .map(mapper::toDomain)
                .flatMap(maquina -> useCase.update(id, maquina))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return useCase.delete(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/sede/{sedeId}")
    public Flux<MaquinaResponseDTO> findBySedeId(@PathVariable Long sedeId) {
        return useCase.findBySedeId(sedeId)
                .map(mapper::toResponse);
    }
}