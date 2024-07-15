package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.FoodMenu;

import java.util.Random;

public class FoodMenuHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static FoodMenu createRandomFoodMenu() {
        return FoodMenu.builder()
                .name(faker.food().dish())
                .price(RandomDoubleGenerator.generate(5.0, 50.0))
                .build();
    }
}
