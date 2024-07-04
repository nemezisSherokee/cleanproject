package com.example.orderprocessing.repository;

import com.example.infrastructures.entities.Order;
import com.example.orderprocessing.base.BaseTestClass;
import com.example.orderprocessing.helpers.OrderHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// both h2 profile and @Testcontainer can not be spinned up.
// @ActiveProfiles("h2")

// we configured our database in application-h2.yml, so we don't want any auto-configurations

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// this annotation is responsible to create application context

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest extends BaseTestClass {

    @Autowired
    OrderRepository orderRepository;

    private final Order order = OrderHelper.createRandomOrder();

    @Test
    public void save() {
        // prepare
        orderRepository.deleteAll();
        Order savedOrder = orderRepository.save(order);

        // act

        // verify
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertEquals(savedOrder.getCustomerName(), savedOrder.getCustomerName());
        assertEquals(savedOrder.getProduct(), savedOrder.getProduct());
    }


    @Test
    public void findOrdersByCustomerNameTest() {
        // prepare
        orderRepository.deleteAll();
        orderRepository.save(order);

        // act
        List<Order> savedOrders = orderRepository.findOrdersByCustomerName(order.getCustomerName());

        // verify
        assertNotNull(savedOrders);
        assertEquals(savedOrders.size(), 1);
        assertEquals(savedOrders.get(0).getCustomerName(), order.getCustomerName());
        assertEquals(savedOrders.get(0).getProduct(), order.getProduct());
    }


    @Test
    public void findOrdersWithinDateRangeTest() {
        // prepare
        orderRepository.deleteAll();
        orderRepository.save(order);

        // act
        List<Order> savedOrders = orderRepository
                .findOrdersWithinDateRange(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        // verify
        assertNotNull(savedOrders);
        assertEquals(savedOrders.size(), 1);
        assertEquals(savedOrders.get(0).getCustomerName(), order.getCustomerName());
        assertEquals(savedOrders.get(0).getProduct(), order.getProduct());
    }

    @Test
    public void findOrdersWithinDateRangeTomorrowTest() {
        // prepare
        orderRepository.deleteAll();
        orderRepository.save(order);

        // act
        List<Order> savedOrders = orderRepository
                .findOrdersWithinDateRange(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        // verify
        assertNotNull(savedOrders);
        assertEquals(savedOrders.size(), 0);
    }
}
