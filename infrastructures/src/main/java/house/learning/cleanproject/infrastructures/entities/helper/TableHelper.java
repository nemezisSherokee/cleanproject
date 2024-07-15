package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Client;
import house.learning.cleanproject.infrastructures.entities.RestaurantTable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Set;

public class TableHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static RestaurantTable createRandomTable() {

        return RestaurantTable.builder()
                .date(LocalDateTime.now())
                .description(faker.country().name())
                .name(faker.country().capital())
                .build();
    }

}
