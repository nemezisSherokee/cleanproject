package com.example.orderprocessing.repository;

import com.example.infrastructures.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customerName = :customerName")
    List<Order> findOrdersByCustomerName(@Param("customerName") String customerName);

    @Query(value = "SELECT * FROM orders WHERE order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> findOrdersWithinDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
