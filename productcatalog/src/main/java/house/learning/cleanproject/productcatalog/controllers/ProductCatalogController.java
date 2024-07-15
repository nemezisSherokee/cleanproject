package house.learning.cleanproject.productcatalog.controllers;

import house.learning.cleanproject.infrastructures.entities.Product;
import house.learning.cleanproject.productcatalog.repositories.ProductRepository;
import house.learning.cleanproject.productcatalog.services.RabbitProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/productcatalog/")
public class ProductCatalogController {

    private final RabbitProducer rabbitProducer;
    private final WebClient orderprocessingWebClient;
    private final ProductRepository productRepository;

    @PostMapping("/message")
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

    @GetMapping("/products")
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/products/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    return productRepository.save(existingProduct);
                })
                .switchIfEmpty(Mono.error(new Exception("Product not found")));
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productRepository.deleteById(id);
    }

    // http://localhost:8081/api/v1.0/productcatalog/products/page/flux?page=4&size=5&sortBy=price&direction=DESC
    @GetMapping("/products/page/flux")
    public Flux<Product> getProductsPagedFlux(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return productRepository.findAllBy(pageable);
    }

    // http://localhost:8081/api/v1.0/productcatalog/products/page?page=4&size=5&sortBy=price&direction=DESC
    @GetMapping("/products/page")
    public Mono<Page<Product>> getProductsPaged(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return productRepository.findAllProducts(pageable);
    }

}
