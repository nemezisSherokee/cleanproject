package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Client;
import house.learning.cleanproject.infrastructures.entities.RestaurantTable;

import java.util.Random;

public class ClientHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Client createRandomClient(RestaurantTable restaurantTable) {
        return Client.builder()
                .name(faker.name().fullName())
                .restaurantTable(restaurantTable)
                .email(faker.internet().emailAddress())
                .build();
    }
}
