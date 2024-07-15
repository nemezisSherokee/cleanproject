package house.learning.cleanproject.infrastructures.repositories;

import house.learning.cleanproject.infrastructures.entities.ClientMenu;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.ClientMenuDrinkMenus.CLIENT_MENU_DRINK_MENUS;
import static house.learning.cleanproject.inventoryservice.jooq.tables.ClientMenuFoodMenus.CLIENT_MENU_FOOD_MENUS;
import static house.learning.cleanproject.inventoryservice.jooq.tables.Clientmenus.CLIENTMENUS;

@Repository
public class ClientMenuRepository {

    private final DSLContext dslContext;

    @Autowired
    public ClientMenuRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public Mono<ClientMenu> createClientMenu(ClientMenu clientMenu) {
        return Mono.fromCallable(() -> dslContext.insertInto(CLIENTMENUS)
                .set(CLIENTMENUS.CLIENT_ID, clientMenu.getClient().getId())
                .set(CLIENTMENUS.DESCRIPTION, clientMenu.getDescription())
                .returning()
                .fetchOne(record -> {
                            clientMenu.setId(record.getId());
                            clientMenu.getFoodMenus().forEach(foodMenuId -> dslContext.insertInto(CLIENT_MENU_FOOD_MENUS)
                                    .set(CLIENT_MENU_FOOD_MENUS.CLIENT_MENU_ID, record.getId())
                                    .set(CLIENT_MENU_FOOD_MENUS.FOOD_MENU_ID, foodMenuId.getId())
                                    .execute());

                            clientMenu.getDrinkMenus().forEach(drinkMenuId -> dslContext.insertInto(CLIENT_MENU_DRINK_MENUS)
                                    .set(CLIENT_MENU_DRINK_MENUS.CLIENT_MENU_ID, record.getId())
                                    .set(CLIENT_MENU_DRINK_MENUS.DRINK_MENU_ID, drinkMenuId.getId())
                                    .execute());
                            return clientMenu;
                        }
                ));
    }

}
