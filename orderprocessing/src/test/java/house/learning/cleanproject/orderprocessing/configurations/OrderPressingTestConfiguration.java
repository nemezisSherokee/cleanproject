package house.learning.cleanproject.orderprocessing.configurations;

import house.learning.cleanproject.infrastructures.entities.config.EntitiesScanConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({EntitiesScanConfiguration.class})
public class OrderPressingTestConfiguration {
}
