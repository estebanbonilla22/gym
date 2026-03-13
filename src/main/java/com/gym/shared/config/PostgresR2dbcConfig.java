package com.gym.shared.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class PostgresR2dbcConfig {

    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory(
            @Value("${app.r2dbc.postgres.host}") String host,
            @Value("${app.r2dbc.postgres.port}") int port,
            @Value("${app.r2dbc.postgres.database}") String database,
            @Value("${app.r2dbc.postgres.username}") String username,
            @Value("${app.r2dbc.postgres.password}") String password,
            @Value("${app.r2dbc.postgres.ssl-mode:require}") String sslMode
    ) {
        String sslParam = "disable".equalsIgnoreCase(sslMode) ? "disable" : "require";
        String encodedUser = URLEncoder.encode(username, StandardCharsets.UTF_8);
        String encodedPass = URLEncoder.encode(password, StandardCharsets.UTF_8);
        String url = "r2dbc:postgresql://%s:%s@%s:%d/%s?sslMode=%s"
                .formatted(encodedUser, encodedPass, host, port, database, sslParam);

        ConnectionFactory raw = ConnectionFactories.get(url);
        return new ConnectionPool(
                ConnectionPoolConfiguration.builder(raw)
                        .initialSize(2)
                        .maxSize(10)
                        .maxIdleTime(java.time.Duration.ofMinutes(10))
                        .build()
        );
    }

    @Bean(name = "postgresR2dbcEntityTemplate")
    public R2dbcEntityTemplate postgresR2dbcEntityTemplate(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory
    ) {
        return new R2dbcEntityTemplate(connectionFactory);
    }
}