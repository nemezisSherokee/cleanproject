package house.learning.cleanproject.productcatalog.configurations;

import house.learning.cleanproject.infrastructures.entities.Product;
import house.learning.cleanproject.infrastructures.entities.config.EntitiesScanConfiguration;
import house.learning.cleanproject.infrastructures.entities.helper.ProductHelper;
import house.learning.cleanproject.productcatalog.repositories.ProductRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Flux;

import java.util.Random;
import java.util.stream.IntStream;

@Import({
        EntitiesScanConfiguration.class
})
@ComponentScan(basePackages = "house.learning.cleanproject")
@Configuration
public class GeneralConfig {


    @Bean
    @Order(1)
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    @Order(2)
    public CommandLineRunner init(ProductRepository productRepository) {
        return args -> {

            Random random = new Random();
            int productCount = random.nextInt(10) + 1; // Generates a random number between 1 and 10
            Flux<Product> products = Flux.fromStream(IntStream.range(0, productCount).mapToObj(i -> {
                return ProductHelper.createRandomProduct();
            }));

            productRepository.saveAll(products)
                    .doOnComplete(() -> System.out.println("Initialized " + productCount + " products"))
                    .subscribe();
        };
    }
}