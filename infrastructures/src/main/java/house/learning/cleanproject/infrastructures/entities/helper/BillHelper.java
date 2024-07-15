package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Bill;
import house.learning.cleanproject.infrastructures.entities.ClientMenu;
import house.learning.cleanproject.infrastructures.entities.DrinkMenu;
import house.learning.cleanproject.infrastructures.entities.FoodMenu;

import java.util.Random;

public class BillHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Bill createRandomBill(ClientMenu clientMenu) {
        return Bill.builder()
                .clientMenu(clientMenu)
                .totalAmount(clientMenu.getFoodMenus().stream().mapToDouble(FoodMenu::getPrice).sum()
                            + clientMenu.getDrinkMenus().stream().mapToDouble(DrinkMenu::getPrice).sum())
                .build();
    }
}
