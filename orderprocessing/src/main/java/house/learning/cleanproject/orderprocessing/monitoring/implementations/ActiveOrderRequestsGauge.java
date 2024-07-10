package house.learning.cleanproject.orderprocessing.monitoring.implementations;

import house.learning.cleanproject.orderprocessing.monitoring.interfaces.IActiveRequestsGauge;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ActiveOrderRequestsGauge  implements IActiveRequestsGauge {

    private AtomicInteger activeOrderCreationRequests;

    public ActiveOrderRequestsGauge(MeterRegistry registry) {
        activeOrderCreationRequests = new AtomicInteger(0);
        Gauge.builder("orders.creation.active.requests", activeOrderCreationRequests, f -> f.get())
                .description("Registry Active order creation requests")
                .register(registry);

    }

    public int increment() {
        return activeOrderCreationRequests.incrementAndGet();
    }

    public int decrement() {
        return activeOrderCreationRequests.decrementAndGet();
    }

}
