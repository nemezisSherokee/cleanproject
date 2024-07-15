package house.learning.cleanproject.productcatalog.repositories;

import house.learning.cleanproject.infrastructures.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long>,
        ReactiveSortingRepository<Product, Long>,
        ReactiveCrudRepository<Product, Long> {

    Flux<Product> findAllBy(Pageable pageable);

    default Mono<Page<Product>> findAllProducts(Pageable pageable) {
        return this.findAllBy(pageable)
                .collectList()
                .zipWith(count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }
}