package com.gym.sedes.infrastructure.adapters.out.persistence;

import com.gym.sedes.application.mapper.SedeMapper;
import com.gym.sedes.domain.model.Sede;
import com.gym.sedes.domain.ports.out.SedeRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SedeRepositoryAdapter implements SedeRepositoryPort {

    private final R2dbcEntityTemplate template;
    private final SedeMapper mapper;

    public SedeRepositoryAdapter(
            @Qualifier("postgresR2dbcEntityTemplate") R2dbcEntityTemplate template,
            SedeMapper mapper
    ) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public Mono<Sede> save(Sede sede) {
        SedeEntity entity = mapper.toEntity(sede);

        if (entity.getId() == null) {
            return template.insert(SedeEntity.class)
                    .using(entity)
                    .map(mapper::toDomain);
        }

        return template.update(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Sede> findAll() {
        return template.select(SedeEntity.class)
                .all()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Sede> findById(Long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return template.selectOne(query, SedeEntity.class)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return template.delete(query, SedeEntity.class).then();
    }
}