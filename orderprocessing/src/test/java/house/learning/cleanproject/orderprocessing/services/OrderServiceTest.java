package house.learning.cleanproject.orderprocessing.services;

import house.learning.cleanproject.infrastructures.entities.Order;
import house.learning.cleanproject.orderprocessing.exceptions.DataBaseException;
import house.learning.cleanproject.orderprocessing.helpers.OrderHelper;
import house.learning.cleanproject.orderprocessing.monitoring.interfaces.IActiveRequestsGauge;
import house.learning.cleanproject.orderprocessing.monitoring.interfaces.ICreatedCounter;
import house.learning.cleanproject.orderprocessing.monitoring.interfaces.ICreationTimer;
import house.learning.cleanproject.orderprocessing.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static house.learning.cleanproject.orderprocessing.constants.ExceptionMessages.CANNOT_SAVE_ENTITY_TO_THE_DATA_BASE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ICreationTimer orderCreationTimer;

    @Mock
    IActiveRequestsGauge activeOrderRequestsGauge;

    @Mock
    ICreatedCounter orderCreatedCounter;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void before() {
    }

    @Test
    void saveOrderWithException() {
        // prepare
        Order order = OrderHelper.createRandomOrder();

        when(activeOrderRequestsGauge.increment()).thenReturn(0);
        when(activeOrderRequestsGauge.decrement()).thenReturn(0);

        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
            task.run();
            return null;
        }).when(orderCreationTimer).recordTime(any(Runnable.class));
        when(orderRepository.save(any())).thenReturn(null);

        // act
        RuntimeException exception = assertThrows(DataBaseException.class, () -> orderService.saveOrder(order));

        // verify
        assertTrue(exception.getMessage().contentEquals(CANNOT_SAVE_ENTITY_TO_THE_DATA_BASE));
        verify(orderRepository, times(1)).save(order);
        verify(activeOrderRequestsGauge).increment();
        verify(activeOrderRequestsGauge).decrement();
        verify(orderCreatedCounter, never()).incrementCounter(); // Ensure counter is not incremented on failure
        verify(orderCreationTimer, times(1)).recordTime(any(Runnable.class));
    }

    @Test
    void saveOrder() {
        // prepare
        Order order = OrderHelper.createRandomOrder();
        when(orderRepository.save(any())).thenReturn(order);

        when(activeOrderRequestsGauge.increment()).thenReturn(0);
        when(activeOrderRequestsGauge.decrement()).thenReturn(0);

        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
            task.run();
            return null;
        }).when(orderCreationTimer).recordTime(any(Runnable.class));

        // act
        orderService.saveOrder(order);

        // verify
        verify(orderRepository, times(1)).save(order);
        verify(activeOrderRequestsGauge).increment();
        verify(activeOrderRequestsGauge).decrement();
        verify(orderCreationTimer, times(1)).recordTime(any(Runnable.class));
        verify(orderCreatedCounter, times(1)).incrementCounter();
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