package house.learning.cleanproject.infrastructures.entities.config;

import house.learning.cleanproject.infrastructures.entities.Order;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EntityScan(basePackages = {"house.learning.cleanproject.infrastructures.entities"})
@EntityScan(basePackageClasses = {Order.class})
public class EntitiesScanConfiguration {
}
