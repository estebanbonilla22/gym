package com.gym.maquinas.infrastructure.adapters.out.persistence;

import com.gym.maquinas.application.mapper.MaquinaMapper;
import com.gym.maquinas.domain.model.Maquina;
import com.gym.maquinas.domain.ports.out.MaquinaRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MaquinaRepositoryAdapter implements MaquinaRepositoryPort {

    private final R2dbcEntityTemplate template;
    private final MaquinaMapper mapper;

    public MaquinaRepositoryAdapter(
            @Qualifier("postgresR2dbcEntityTemplate") R2dbcEntityTemplate template,
            MaquinaMapper mapper
    ) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public Mono<Maquina> save(Maquina maquina) {
        MaquinaEntity entity = mapper.toEntity(maquina);

        if (entity.getId() == null) {
            return template.insert(MaquinaEntity.class)
                    .using(entity)
                    .map(mapper::toDomain);
        }

        return template.update(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Maquina> findAll() {
        return template.select(MaquinaEntity.class)
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Maquina> findById(Long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return template.selectOne(query, MaquinaEntity.class)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Maquina> findBySedeId(Long sedeId) {
        Query query = Query.query(Criteria.where("sede_id").is(sedeId));
        return template.select(query, MaquinaEntity.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return template.delete(query, MaquinaEntity.class).then();
    }
}