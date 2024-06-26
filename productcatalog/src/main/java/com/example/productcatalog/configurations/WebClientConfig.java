package com.example.productcatalog.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @LoadBalanced
    @Bean
    public WebClient orderprocessingWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.
                baseUrl("http://orderprocessing/orderprocessing/api/v1.0").
                build();
    }

}
