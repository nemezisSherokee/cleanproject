package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Bill;
import house.learning.cleanproject.infrastructures.entities.Client;
import house.learning.cleanproject.infrastructures.entities.ClientMenu;
import house.learning.cleanproject.infrastructures.entities.ClientOrder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.List;

public class ClientOrderHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static ClientOrder createRandomClientOrder(Client client, Bill bill) {
        return ClientOrder.builder()
                .client(client)
                .bill(bill)
                .orderDate(LocalDateTime.now())
                .build();
    }
}
