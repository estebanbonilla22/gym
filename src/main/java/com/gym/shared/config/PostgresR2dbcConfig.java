package com.gym.shared.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import java.time.Duration;

@Configuration
public class PostgresR2dbcConfig {

    @Bean(name = "postgresConnectionFactory")
    public ConnectionFactory postgresConnectionFactory(
            @Value("${app.r2dbc.postgres.host}") String host,
            @Value("${app.r2dbc.postgres.port}") int port,
            @Value("${app.r2dbc.postgres.database}") String database,
            @Value("${app.r2dbc.postgres.username}") String username,
            @Value("${app.r2dbc.postgres.password}") String password,
            @Value("${app.r2dbc.postgres.pool.initial-size}") int initialSize,
            @Value("${app.r2dbc.postgres.pool.max-size}") int maxSize
    ) {
        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .database(database)
                        .username(username)
                        .password(password)
                        .build()
        );

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
                .initialSize(initialSize)
                .maxSize(maxSize)
                .maxIdleTime(Duration.ofMinutes(30))
                .build();

        return new ConnectionPool(poolConfiguration);
    }

    @Bean(name = "postgresR2dbcEntityTemplate")
    public R2dbcEntityTemplate postgresR2dbcEntityTemplate(
            @Qualifier("postgresConnectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }
}