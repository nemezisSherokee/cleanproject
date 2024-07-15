package house.learning.cleanproject.infrastructures.entities;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "clientmenus")
@AllArgsConstructor
@NoArgsConstructor
public class ClientMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "client_menu_food_menus",
            joinColumns = @JoinColumn(name = "client_menu_id"),
            inverseJoinColumns = @JoinColumn(name = "food_menu_id")
    )
    private Set<FoodMenu> foodMenus;

    @ManyToMany
    @JoinTable(
            name = "client_menu_drink_menus",
            joinColumns = @JoinColumn(name = "client_menu_id"),
            inverseJoinColumns = @JoinColumn(name = "drink_menu_id")
    )
    private Set<DrinkMenu> drinkMenus;

    private String description;
}
