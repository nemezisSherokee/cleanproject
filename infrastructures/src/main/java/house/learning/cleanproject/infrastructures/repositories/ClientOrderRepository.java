package house.learning.cleanproject.infrastructures.repositories;

import house.learning.cleanproject.infrastructures.entities.ClientOrder;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static house.learning.cleanproject.inventoryservice.jooq.tables.Clientorders.CLIENTORDERS;

@Repository
public class ClientOrderRepository {
    private final DSLContext dslContext;

    @Autowired
    public ClientOrderRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Mono<ClientOrder> createClientOder(ClientOrder clientOrder, LocalDateTime date) {
        return Mono.fromCallable(() -> dslContext.insertInto(CLIENTORDERS)
                .set(CLIENTORDERS.BILL_ID, clientOrder.getBill().getId())
                .set(CLIENTORDERS.CLIENT_ID, clientOrder.getClient().getId())
                .set(CLIENTORDERS.ORDER_DATE, date)
                .returning()
                .fetchOne(record -> {
                    clientOrder.setId(record.getId());
                    return clientOrder;
                })
        );
    }
}
