package house.learning.cleanproject.infrastructures.repositories;

import house.learning.cleanproject.infrastructures.entities.RestaurantTable;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.RestaurantTable.RESTAURANT_TABLE;

@Repository
public class RestaurantTableRepository {
    private final DSLContext dslContext;

    @Autowired
    public RestaurantTableRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Mono<RestaurantTable> createRestaurantTable(RestaurantTable restaurantTable) {
        return Mono.fromCallable(() -> dslContext.insertInto(RESTAURANT_TABLE)
                .set(RESTAURANT_TABLE.DATE, restaurantTable.getDate())
                .set(RESTAURANT_TABLE.NAME, restaurantTable.getName())
                .set(RESTAURANT_TABLE.DESCRIPTION, restaurantTable.getDescription())
                .returning()
                .fetchOne(record -> {
                            restaurantTable.setId(record.get(RESTAURANT_TABLE.ID));
                            return restaurantTable;
                        }
                ));
    }

}
