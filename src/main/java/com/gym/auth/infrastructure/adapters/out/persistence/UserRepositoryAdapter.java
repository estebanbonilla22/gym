package com.gym.auth.infrastructure.adapters.out.persistence;

import com.gym.auth.application.mapper.UserMapper;
import com.gym.auth.domain.model.User;
import com.gym.auth.domain.ports.out.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final R2dbcEntityTemplate template;
    private final UserMapper mapper;

    public UserRepositoryAdapter(
            @Qualifier("postgresR2dbcEntityTemplate") R2dbcEntityTemplate template,
            UserMapper mapper
    ) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public Mono<User> save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return template.insert(UserEntity.class)
                .using(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return template.selectOne(query, UserEntity.class)
                .map(mapper::toDomain);
    }
}