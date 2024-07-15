package house.learning.cleanproject.productcatalog.services;

import house.learning.cleanproject.productcatalog.controllers.ProductCatalogController;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("myQueue", message);
    }

}
