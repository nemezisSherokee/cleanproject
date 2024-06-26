package com.example.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.infrastructures.entities"}) // TODO: could be added to a config as well
public class ProductcatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductcatalogApplication.class, args);
    }

}
