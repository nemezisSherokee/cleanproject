package house.learning.cleanproject.inventoryservice.repositories;

import house.learning.cleanproject.infrastructures.entities.Bill;
import house.learning.cleanproject.infrastructures.entities.ClientMenu;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.Bills.BILLS;

@Repository
public class BillRepository {
    private final DSLContext dslContext;

    @Autowired
    public BillRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    public Mono<Bill> createClientBill(ClientMenu clientMenu, Double amount) {
        return Mono.fromCallable(() -> dslContext.insertInto(BILLS)
                .set(BILLS.CLIENT_MENU_ID, clientMenu.getId())
                .set(BILLS.TOTAL_AMOUNT, amount)
                .returning()
                .fetchOne(record -> new Bill(
                        record.get(BILLS.ID),
                        clientMenu,
                        amount))
        );
    }
}
