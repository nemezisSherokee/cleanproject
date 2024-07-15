package house.learning.cleanproject.inventoryservice.Controllers;

import house.learning.cleanproject.inventoryservice.service.AmazonSQSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queues")
@RequiredArgsConstructor
public class SQSController {

    private final AmazonSQSService sqsService;

    @PostMapping("/{queueName}")
    public String createQueue(@PathVariable String queueName) {
        return sqsService.createQueue(queueName);
    }

    @GetMapping
    public List<String> listQueues() {
        return sqsService.listQueues();
    }

    @PostMapping("/message/{queueName}")
    public String sendMessqge(@PathVariable String queueName, @RequestBody String messaqge) {
        return sqsService.sendMessage(queueName, messaqge);
    }

    @GetMapping("/message/{queueName}")
    public String receiveMessqge(@PathVariable String queueName, @RequestParam(value = "deletemessqge", defaultValue = "false") Boolean deletemessqge) {
        return sqsService.receiveMessages(queueName, deletemessqge);
    }

}
