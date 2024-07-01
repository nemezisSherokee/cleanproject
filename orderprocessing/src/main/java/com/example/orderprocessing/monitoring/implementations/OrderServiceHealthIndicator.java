package com.example.orderprocessing.monitoring.implementations;

import com.example.orderprocessing.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceHealthIndicator implements HealthIndicator {

    private final OrderService orderService;

    @Override
    public Health health() {
        // Custom health check logic
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        // Custom logic to determine health
        return orderService.getOrderCount() > 5 ? 0 : 1;
        // return an error code if orderService.getOrderCount less than 5
    }

}
