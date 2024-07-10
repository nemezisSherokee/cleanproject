package house.learning.cleanproject.orderprocessing.configurations;


import house.learning.cleanproject.infrastructures.entities.config.EntitiesScanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        EntitiesScanConfiguration.class
})
@Configuration
public class GeneralConfig {
}
