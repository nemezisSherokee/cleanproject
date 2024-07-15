package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.DrinkMenu;

import java.util.Random;

public class DrinkMenuHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static DrinkMenu createRandomDrinkMenu() {
        return DrinkMenu.builder()
                .name(faker.beer().name())
                .price(RandomDoubleGenerator.generate(2.0, 20.0))
                .build();
    }
}
