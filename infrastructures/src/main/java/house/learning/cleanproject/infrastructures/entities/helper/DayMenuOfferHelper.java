package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.DayMenuOffer;
import house.learning.cleanproject.infrastructures.entities.DrinkMenu;
import house.learning.cleanproject.infrastructures.entities.FoodMenu;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.Set;

public class DayMenuOfferHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static DayMenuOffer createRandomDayMenuOffer(Set<FoodMenu> foodMenus, Set<DrinkMenu> drinkMenus) {
        return DayMenuOffer.builder()
                .foodMenus(foodMenus)
                .drinkMenus(drinkMenus)
                .date(LocalDateTime.now())
                .build();
    }
}
