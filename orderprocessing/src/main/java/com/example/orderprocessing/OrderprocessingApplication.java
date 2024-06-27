package com.example.orderprocessing;

import com.example.infrastructures.entities.Order;
import com.example.orderprocessing.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class OrderprocessingApplication {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @LoadBalanced
    @Bean
    public WebClient productcatalogWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.
                baseUrl("http://productcatalog/productcatalog/api/v1.0").
                build();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderprocessingApplication.class, args);
    }

    @RestController
    @RequestMapping("orderprocessing/api/v1.0")
    @RequiredArgsConstructor
    public static class MyRestController {

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

        @GetMapping("/orders")
        public List<Order> getOrders() {
            return orderService.getAllOrdersByCustomerName("Avis Kuvalis");
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

}
