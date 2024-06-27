package com.example.orderprocessing.services;

import com.example.infrastructures.entities.Order;
import com.example.orderprocessing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @CacheEvict(value = { "ordersByCustomerName", "ordersWithinDateRange" }, allEntries = true)
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
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
