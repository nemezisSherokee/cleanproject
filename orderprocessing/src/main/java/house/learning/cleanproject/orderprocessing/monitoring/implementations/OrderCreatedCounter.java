package house.learning.cleanproject.orderprocessing.monitoring.implementations;

import house.learning.cleanproject.orderprocessing.monitoring.interfaces.ICreatedCounter;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedCounter implements ICreatedCounter {

    private final Counter orderCreatedCounter;

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    public OrderCreatedCounter(MeterRegistry meterRegistry) {

        orderCreatedCounter = Counter.builder("orders.created")
                .description("Registry Number of orders created")
                .register(meterRegistry);

    }

    public void incrementCounter() {
        orderCreatedCounter.increment();
    }
}