package house.learning.cleanproject.inventoryservice.repositories;

import house.learning.cleanproject.infrastructures.entities.Order;
import house.learning.cleanproject.infrastructures.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//@Repository
public interface OrderRepository
//        extends R2dbcRepository<Order, Long>,
//        ReactiveSortingRepository<Order, Long>,
//        ReactiveCrudRepository<Order, Long>
{

//    @Query("SELECT o FROM Order o WHERE o.customerName = :customerName")
//    List<Order> findOrdersByCustomerName(@Param("customerName") String customerName);
//
//    @Query(value = "SELECT * FROM orders WHERE order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
//    List<Order> findOrdersWithinDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
//
//    @Query(value = "SELECT count(*) FROM orders WHERE order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
//    int countOrdersWithinDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
