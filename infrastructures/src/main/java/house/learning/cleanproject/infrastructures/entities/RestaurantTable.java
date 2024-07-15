package house.learning.cleanproject.infrastructures.entities;


import com.github.javafaker.Faker;
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
@Table(name = "restaurant_table")
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "table_clients",
            joinColumns = @JoinColumn(name = "table_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> clients;

    private LocalDateTime date;
    private String name;
    private String description;

    // getters and setters
}
