package house.learning.cleanproject.orderprocessing.monitoring.implementations;

import house.learning.cleanproject.orderprocessing.monitoring.interfaces.ICreationTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OrderCreationTimer implements ICreationTimer {

    private final Timer ordersCreationTimer;

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    public OrderCreationTimer(MeterRegistry registry) {

        ordersCreationTimer = Timer.builder("orders.creation.time")
                .description("Registry Time taken to create an order")
                .register(registry);
    }

    public void recordTime(Runnable runnable) {
        ordersCreationTimer.record(runnable);
    }

    public void recordTime(long duration, TimeUnit unit) {
        ordersCreationTimer.record(duration, unit);
    }
}