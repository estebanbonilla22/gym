package com.gym.shared.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import java.time.Duration;

@Configuration
public class MySqlR2dbcConfig {

    @Bean(name = "mysqlConnectionFactory")
    public ConnectionFactory mysqlConnectionFactory(
            @Value("${app.r2dbc.mysql.host}") String host,
            @Value("${app.r2dbc.mysql.port}") int port,
            @Value("${app.r2dbc.mysql.database}") String database,
            @Value("${app.r2dbc.mysql.username}") String username,
            @Value("${app.r2dbc.mysql.password}") String password,
            @Value("${app.r2dbc.mysql.pool.initial-size}") int initialSize,
            @Value("${app.r2dbc.mysql.pool.max-size}") int maxSize
    ) {
        ConnectionFactory connectionFactory = io.asyncer.r2dbc.mysql.MySqlConnectionFactory.from(
                io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .database(database)
                        .user(username)
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
            @Qualifier("mysqlConnectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }
}