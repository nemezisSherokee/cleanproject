package house.learning.cleanproject.infrastructures.entities.helper;

import com.github.javafaker.Faker;
import house.learning.cleanproject.infrastructures.entities.Bill;
import house.learning.cleanproject.infrastructures.entities.Payment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class PaymentHelper {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Payment createRandomPayment(Bill bill) {
        return Payment.builder()
                .bill(bill)
                .paymentMethod(faker.business().creditCardType())
                .paymentDate(LocalDateTime.now())
                .amount(RandomDoubleGenerator.generate(0, bill.getTotalAmount()))
                .build();
    }
}
