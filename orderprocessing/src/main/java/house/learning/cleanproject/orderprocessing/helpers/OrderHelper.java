package house.learning.cleanproject.orderprocessing.helpers;


import house.learning.cleanproject.infrastructures.entities.Order;
import com.github.javafaker.Faker;

import java.util.Date;
import java.util.Random;


public class OrderHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Order createRandomOrder() {
        return Order.builder()
                .customerName(faker.name().fullName())
                .product(faker.commerce().productName())
                .quantity(random.nextInt(100) + 1) // Random quantity between 1 and 100
                .price(Double.parseDouble("566"))
                .orderDate(new Date())
                .build();
    }
}

