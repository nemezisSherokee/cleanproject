package com.example.orderprocessing.base;


import com.example.orderprocessing.configurations.OrderPressingTestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@Import({OrderPressingTestConfiguration.class})
public abstract class BaseTestClass {

    private static final String POSTGRES_IMAGE = "postgres:latest";
    private static final String POSTGRES_DB = "orderprocessingtest";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "postgrespass";
    private static final DockerImageName dockerImageName = DockerImageName.parse(POSTGRES_IMAGE);

    @Container
    private static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>(dockerImageName)
            .withDatabaseName(POSTGRES_DB)
            .withUsername(POSTGRES_USER)
            .withPassword(POSTGRES_PASSWORD);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresql::getJdbcUrl);
        registry.add("spring.datasource.username", postgresql::getUsername);
        registry.add("spring.datasource.password", postgresql::getPassword);
    }

}
