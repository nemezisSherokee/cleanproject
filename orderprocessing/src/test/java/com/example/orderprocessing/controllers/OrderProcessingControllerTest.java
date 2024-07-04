package com.example.orderprocessing.controllers;

import com.example.infrastructures.entities.Order;
import com.example.infrastructures.entities.config.EntitiesScanConfiguration;
import com.example.orderprocessing.base.BaseTestClass;
import com.example.orderprocessing.helpers.OrderHelper;
import com.example.orderprocessing.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@Import({EntitiesScanConfiguration.class})
@ActiveProfiles("test")
class OrderProcessingControllerTest extends BaseTestClass {

    @LocalServerPort
    private Integer port;

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        order = OrderHelper.createRandomOrder();
        orderRepository.save(order);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void hello() {
        this.webTestClient.get().uri("/orderprocessing/api/v1.0/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }

    @Test
    void getVersion() {
        this.webTestClient.get().uri("/orderprocessing/api/v1.0/version")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);

    }

    @Test
    void getOrders() {
        this.webTestClient.get().uri("/orderprocessing/api/v1.0/orders/" + order.getCustomerName())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Order.class).hasSize(1);
    }

    @Test
    void findOrdersWithinDateRange() {

        this.webTestClient.get().uri("/orderprocessing/api/v1.0/orders/since/yersterday")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Order.class).hasSize(1);
    }

}