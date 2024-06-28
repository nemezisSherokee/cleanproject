package com.example.orderprocessing.controllers;

import com.example.infrastructures.entities.Order;
import com.example.orderprocessing.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("orderprocessing/api/v1.0")
@RequiredArgsConstructor
public class OrderProcessingController {

    private final WebClient productcatalogWebClient;
    private final OrderService orderService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World Order Processing Service!";
    }

    @GetMapping("/version")
    public String getVersion() {
        return ("Processing Service v1.0");
    }

    @GetMapping("/orders/{customerName}")
    public List<Order> getOrders(@PathVariable String customerName) {
        return orderService.getAllOrdersByCustomerName(customerName);
    }

    @GetMapping("/orders/since/yersterday")
    public List<Order> findOrdersWithinDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        return orderService.findOrdersWithinDateRange(startDate, endDate);
    }

    @GetMapping("/partners")
    public Mono<String> getCommunicationPartner() {
        Mono<String> response = productcatalogWebClient
                .get()
                .uri("/version")
                .retrieve()
                .bodyToMono(String.class);

        return response.map(rsp -> "Partner : " + rsp);
    }
}
