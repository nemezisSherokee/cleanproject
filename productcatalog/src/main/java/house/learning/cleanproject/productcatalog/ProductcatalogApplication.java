package house.learning.cleanproject.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"house.learning.cleanproject.infrastructures.entities"}) // TODO: could be added to a config as well
public class ProductcatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductcatalogApplication.class, args);
    }

}
