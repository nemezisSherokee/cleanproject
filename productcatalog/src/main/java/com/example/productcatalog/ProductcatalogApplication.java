package com.example.productcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ProductcatalogApplication {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @LoadBalanced
    @Bean
    public WebClient orderprocessingWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.
                baseUrl("http://orderprocessing").
                build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductcatalogApplication.class, args);
    }

    @RestController
    @RequestMapping("/api/v1.0")
    public static class MyRestController {

        private final WebClient orderprocessingWebClient;

        @Autowired
        public MyRestController(WebClient orderprocessingWebClient) {
            this.orderprocessingWebClient = orderprocessingWebClient;
        }

        @GetMapping("/hello")
        public String hello() {
            return "Hello, World Product Catalog Service!";
        }

        @GetMapping("/version")
        public String getVersion() {
            return ("Product Catalog Service v1.0");
        }

        @GetMapping("/partners")
        public Mono<String> getCommunicationPartner() {
            Mono<String> response = orderprocessingWebClient
                    .get()
                    .uri("/api/v1.0/version")
                    .retrieve()
                    .bodyToMono(String.class);

            return response.map(rsp -> "Partner : " + rsp);
        }
    }

}
