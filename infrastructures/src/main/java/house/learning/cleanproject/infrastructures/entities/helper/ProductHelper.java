package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Product;

import java.util.Random;


public class ProductHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();


    public static double generateRandomPrice(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static Product createRandomProduct() {
        return Product.builder()
                .name(faker.commerce().productName())
                .description(faker.commerce().material())
                .price(RandomDoubleGenerator.generate(50.0, 1500.0))
                .build();
    }
}

