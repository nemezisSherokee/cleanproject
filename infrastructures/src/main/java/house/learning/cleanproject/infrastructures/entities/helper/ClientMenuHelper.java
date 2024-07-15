package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Client;
import house.learning.cleanproject.infrastructures.entities.ClientMenu;
import house.learning.cleanproject.infrastructures.entities.DrinkMenu;
import house.learning.cleanproject.infrastructures.entities.FoodMenu;

import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClientMenuHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static ClientMenu createRandomClientMenu(Client client, List<FoodMenu> foodMenus, List<DrinkMenu> drinkMenus) {
        return ClientMenu.builder()
                .client(client)
                .foodMenus(IntStream.range(0, random.nextInt(foodMenus.size()))
                        .mapToObj(i -> foodMenus.get(random.nextInt(foodMenus.size())))
                        .collect(Collectors.toSet()))
                .drinkMenus(IntStream.range(0, random.nextInt(drinkMenus.size()))
                        .mapToObj(i -> drinkMenus.get(random.nextInt(drinkMenus.size())))
                        .collect(Collectors.toSet()))
                .description(faker.lorem().sentence())
                .build();
    }
}
