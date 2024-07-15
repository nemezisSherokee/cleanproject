package house.learning.cleanproject.infrastructures.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "day_menuo_ffers")
@AllArgsConstructor
@NoArgsConstructor
public class DayMenuOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Adjust cascade type as needed
    @JoinTable(
            name = "day_menu_offer_food_menus",
            joinColumns = @JoinColumn(name = "day_menu_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "food_menu_id")
    )
    private Set<FoodMenu> foodMenus;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Adjust cascade type as needed
    @JoinTable(
            name = "day_menu_offer_drink_menus",
            joinColumns = @JoinColumn(name = "day_menu_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "drink_menu_id")
    )
    private Set<DrinkMenu> drinkMenus;

}
