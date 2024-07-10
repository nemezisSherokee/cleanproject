package house.learning.cleanproject.orderprocessing.monitoring.implementations;

import house.learning.cleanproject.orderprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderServiceInfoContributor implements InfoContributor {

    private final OrderRepository orderRepository;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> customInfo = new HashMap<>();

        customInfo.put("Number of orders", orderRepository.count());

        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        customInfo.put("Number of orders since yestersay", orderRepository.countOrdersWithinDateRange(startDate, endDate));

        builder.withDetail("OrderService", customInfo);
    }
}
