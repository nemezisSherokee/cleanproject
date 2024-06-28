package com.example.orderprocessing.services;

import com.example.infrastructures.entities.Order;
import com.example.orderprocessing.helpers.OrderHelper;
import com.example.orderprocessing.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void saveOrder() {
        // prepare

        // act
        orderService.saveOrder(any());

        // verify
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void getAllOrdersByCustomerNameAndReturnNull() {

        // prepare
        when(orderRepository.findOrdersByCustomerName(anyString())).thenReturn(null);

        // act
        List<Order> returnedList = orderService.getAllOrdersByCustomerName(anyString());

        // assert
        verify(orderRepository, times(1))
                .findOrdersByCustomerName(anyString());
        assertNull(returnedList);
    }

    @Test
    void getAllOrdersByCustomerNameReturnTwoItem() {
        // prepare
        List<Order> createdList = List.of(OrderHelper.createRandomOrder(), OrderHelper.createRandomOrder());

        when(orderRepository.findOrdersByCustomerName(anyString())).thenReturn(createdList);

        // act
        List<Order> returnedList = orderService.getAllOrdersByCustomerName(anyString());

        // assert
        verify(orderRepository, times(1))
                .findOrdersByCustomerName(anyString());
        assertNotNull(returnedList);
        assertEquals(returnedList.size(), createdList.size());
        assertEquals(returnedList, createdList);
    }

    @Test
    void findOrdersWithinDateRange() {

        // prepare
        List<Order> createdList = List.of(OrderHelper.createRandomOrder(), OrderHelper.createRandomOrder());
        when(orderRepository.findOrdersWithinDateRange(any(), any())).thenReturn(createdList);

        // act
        List<Order> returnedList = orderService.findOrdersWithinDateRange(any(), any());

        // assert
        verify(orderRepository, times(1))
                .findOrdersWithinDateRange(any(), any());
        assertNotNull(returnedList);
        assertEquals(returnedList.size(), createdList.size());
        assertEquals(returnedList, createdList);
    }

    @Test
    void updateOrder() {
        // prepare
        Order order = OrderHelper.createRandomOrder();
        Order newOrder = OrderHelper.createRandomOrder();
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));
        when(orderRepository.save(any())).thenReturn(newOrder);

        // act
        Order updatedOrder = orderService.updateOrder(any(), newOrder);

        // verify
        verify(orderRepository, times(1))
                .findById(any());
        verify(orderRepository, times(1))
                .save(any());
        assertNotNull(updatedOrder);
        assertEquals(updatedOrder, newOrder);
    }

    @Test
    void deleteOrder() {

        // prepare
        Long id = 12L;

        // act
        orderService.deleteOrder(id);

        // verify
        verify(orderRepository, times(1)).deleteById(id);
    }
}