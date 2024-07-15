package house.learning.cleanproject.inventoryservice.repositories;

import house.learning.cleanproject.infrastructures.entities.Client;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.Clients.CLIENTS;


@Repository
public class ClientRepository {
    private final DSLContext dslContext;

    @Autowired
    public ClientRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Mono<Client> createClient(Client client) {
        return Mono.fromCallable(() -> dslContext.insertInto(CLIENTS)
                .set(CLIENTS.NAME, client.getName())
                .set(CLIENTS.EMAIL, client.getEmail())
                .returning()
                .fetchOne(record -> {
                    client.setId(record.getId());
                    return client;
                }));
    }

}
