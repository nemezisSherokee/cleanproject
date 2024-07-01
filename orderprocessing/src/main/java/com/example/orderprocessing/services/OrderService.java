package com.example.orderprocessing.services;

import com.example.infrastructures.entities.Order;
import com.example.orderprocessing.exceptions.DataBaseException;
import com.example.orderprocessing.monitoring.interfaces.IActiveRequestsGauge;
import com.example.orderprocessing.monitoring.interfaces.ICreatedCounter;
import com.example.orderprocessing.monitoring.interfaces.ICreationTimer;
import com.example.orderprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.orderprocessing.constants.ExceptionMessages.CANNOT_SAVE_ENTITY_TO_THE_DATA_BASE;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ICreatedCounter orderCreatedCounter;
    private final ICreationTimer orderCreationTimer;
    private  final IActiveRequestsGauge activeOrderCreationRequests;

    public long getOrderCount() {
        return orderRepository.count();
    }
    @CacheEvict(value = { "ordersByCustomerName", "ordersWithinDateRange" }, allEntries = true)
    public void saveOrder(Order order) {

        activeOrderCreationRequests.increment();
        try {
             AtomicReference<Order> createdOrder = new AtomicReference<>();
             orderCreationTimer.recordTime(() -> createdOrder.set(Optional.ofNullable(orderRepository.save(order))
                     .orElseThrow(() -> new DataBaseException(CANNOT_SAVE_ENTITY_TO_THE_DATA_BASE))));
             orderCreatedCounter.incrementCounter();
        } finally {
            activeOrderCreationRequests.decrement();
        }
    }

    @Cacheable(value = "ordersByCustomerName", key = "#customerName") // The method Parameter are used as key if not explicit key is set
    public List<Order> getAllOrdersByCustomerName(String customerName) {
        doLongRunningTask();
        return orderRepository.findOrdersByCustomerName(customerName);
    }

    @Cacheable(value = "ordersWithinDateRange") // This method will still be called because the Parameter are still changing
    public List<Order> findOrdersWithinDateRange(LocalDateTime from, LocalDateTime to) {
        doLongRunningTask();
        return orderRepository.findOrdersWithinDateRange(from, to);
    }

    @CacheEvict(value = { "ordersByCustomerName", "ordersWithinDateRange" }, allEntries = true)
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setCustomerName(orderDetails.getCustomerName());
            order.setProduct(orderDetails.getProduct());
            order.setQuantity(orderDetails.getQuantity());
            order.setPrice(orderDetails.getPrice());
            order.setOrderDate(orderDetails.getOrderDate());
            return orderRepository.save(order);
        }
        return null;
    }

    @CacheEvict(value = { "ordersByCustomerName", "ordersWithinDateRange" }, allEntries = true)
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
