package house.learning.cleanproject.infrastructures.repositories;

import house.learning.cleanproject.infrastructures.entities.Payment;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.Payments.PAYMENTS;

@Repository
public class PaymentRepository {
    private final DSLContext dslContext;

    @Autowired
    public PaymentRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    public Mono<Payment> createClientBillPayment(Payment payment, Double amount) {
        return Mono.fromCallable(() -> dslContext.insertInto(PAYMENTS)
                .set(PAYMENTS.PAYMENT_DATE, payment.getPaymentDate())
                .set(PAYMENTS.PAYMENT_METHOD, payment.getPaymentMethod())
                .set(PAYMENTS.AMOUNT, amount)
                .set(PAYMENTS.BILL_ID, payment.getBill().getId())
                .returning()
                .fetchOne(record -> {
                    payment.setId(record.getId());
                    payment.setAmount(amount);
                    return payment;
                })
        );
    }

}
