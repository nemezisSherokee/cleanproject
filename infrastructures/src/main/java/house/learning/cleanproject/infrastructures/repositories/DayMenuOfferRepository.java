package house.learning.cleanproject.infrastructures.repositories;

import house.learning.cleanproject.infrastructures.entities.DayMenuOffer;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static house.learning.cleanproject.inventoryservice.jooq.tables.DayMenuOfferDrinkMenus.DAY_MENU_OFFER_DRINK_MENUS;
import static house.learning.cleanproject.inventoryservice.jooq.tables.DayMenuOfferFoodMenus.DAY_MENU_OFFER_FOOD_MENUS;
import static house.learning.cleanproject.inventoryservice.jooq.tables.DayMenuoFfers.DAY_MENUO_FFERS;


@Repository
public class DayMenuOfferRepository {
    private final DSLContext dslContext;

    @Autowired
    public DayMenuOfferRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    public Mono<DayMenuOffer> createRestaurantTable(DayMenuOffer dayMenuOffer) {
        return Mono.fromCallable(() -> {

            DayMenuOffer createdDayMenuOffer = dslContext.insertInto(DAY_MENUO_FFERS)
                    .set(DAY_MENUO_FFERS.DATE, dayMenuOffer.getDate())
                    .returning()
                    .fetchOne(record -> {
                                dayMenuOffer.setId(record.get(DAY_MENUO_FFERS.ID));

                                dayMenuOffer.getDrinkMenus().forEach(drinkMenuId -> dslContext.insertInto(DAY_MENU_OFFER_DRINK_MENUS)
                                        .set(DAY_MENU_OFFER_DRINK_MENUS.DRINK_MENU_ID, drinkMenuId.getId())
                                        .set(DAY_MENU_OFFER_DRINK_MENUS.DAY_MENU_OFFER_ID, record.get(DAY_MENUO_FFERS.ID))
                                        .execute());

                                dayMenuOffer.getDrinkMenus().forEach(foodMenuId -> dslContext.insertInto(DAY_MENU_OFFER_FOOD_MENUS)
                                        .set(DAY_MENU_OFFER_FOOD_MENUS.FOOD_MENU_ID, foodMenuId.getId())
                                        .set(DAY_MENU_OFFER_FOOD_MENUS.DAY_MENU_OFFER_ID, record.get(DAY_MENUO_FFERS.ID))
                                        .execute());

                                return dayMenuOffer;
                            }
                    );

//            dayMenuOffer.getDrinkMenus().forEach(drinkMenuId -> dslContext.insertInto(DAY_MENU_OFFER_DRINK_MENUS)
//                    .set(DAY_MENU_OFFER_DRINK_MENUS.DRINK_MENU_ID, drinkMenuId.getId())
//                    .set(DAY_MENU_OFFER_DRINK_MENUS.DAY_MENU_OFFER_ID, createdDayMenuOffer.getId())
//                    .execute());
//
//            dayMenuOffer.getDrinkMenus().forEach(foodMenuId -> dslContext.insertInto(DAY_MENU_OFFER_FOOD_MENUS)
//                    .set(DAY_MENU_OFFER_FOOD_MENUS.FOOD_MENU_ID, foodMenuId.getId())
//                    .set(DAY_MENU_OFFER_FOOD_MENUS.DAY_MENU_OFFER_ID, createdDayMenuOffer.getId())
//                    .execute());

            return createdDayMenuOffer;
        });
    }

}
