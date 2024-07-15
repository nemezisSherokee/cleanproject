package house.learning.cleanproject.infrastructures.repositories;

import house.learning.cleanproject.infrastructures.entities.FoodMenu;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.FoodMenu.FOOD_MENU;

@Repository
public class FoodMenuRepository {

    private final DSLContext dslContext;

    @Autowired
    public FoodMenuRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Mono<FoodMenu> createFoodMenu(FoodMenu foodMenu) {
        return Mono.fromCallable(() -> dslContext.insertInto(FOOD_MENU)
                .set(FOOD_MENU.NAME, foodMenu.getName())
                .set(FOOD_MENU.PRICE, foodMenu.getPrice())
                .returning()
                .fetchOne(record -> {
                    foodMenu.setId(record.getId());
                    return foodMenu;
                }));
    }

}

