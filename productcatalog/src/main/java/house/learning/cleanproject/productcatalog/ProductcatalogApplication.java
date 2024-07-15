package house.learning.cleanproject.productcatalog;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@RequiredArgsConstructor
@EnableR2dbcRepositories
@EnableWebFlux
public class ProductcatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductcatalogApplication.class, args);
    }

}
