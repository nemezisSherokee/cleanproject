package house.learning.cleanproject.productcatalog.controllers;

import house.learning.cleanproject.productcatalog.services.RabbitProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productcatalog/api/v1.0/")
public class ProductCatalogController {

    private final RabbitProducer rabbitProducer;
    private final WebClient orderprocessingWebClient;

    @PostMapping("/")
    public String sendMessage(@RequestBody String message) {
        rabbitProducer.sendMessage(message);
        return "Message sent";
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
                .uri("/version")
                .retrieve()
                .bodyToMono(String.class);

        return response.map(rsp -> "Partner : " + rsp);
    }

}
