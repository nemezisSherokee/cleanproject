package house.learning.cleanproject.inventoryservice.repositories;

import house.learning.cleanproject.infrastructures.entities.DrinkMenu;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.DrinkMenu.DRINK_MENU;

@Repository
public class DrinkMenuRepository {

    private final DSLContext dslContext;

    @Autowired
    public DrinkMenuRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Mono<DrinkMenu> createDrinkMenu(DrinkMenu drinkMenu) {
        return Mono.fromCallable(() -> dslContext.insertInto(DRINK_MENU)
                .set(DRINK_MENU.NAME, drinkMenu.getName())
                .set(DRINK_MENU.PRICE, drinkMenu.getPrice())
                .returning()
                .fetchOne(record -> {
                            drinkMenu.setId(record.getId());
                            return drinkMenu;
                        }
                ));
    }
}
