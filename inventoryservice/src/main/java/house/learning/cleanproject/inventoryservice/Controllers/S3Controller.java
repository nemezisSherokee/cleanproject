package house.learning.cleanproject.inventoryservice.Controllers;

import house.learning.cleanproject.inventoryservice.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class S3Controller {

    private final AmazonS3Service s3Service;

    @PostMapping("/{bucketName}")
    public void createBucket(@PathVariable String bucketName) {
        s3Service.createBucket(bucketName);
    }

    @GetMapping
    public List<String> listBuckets() {
        return s3Service.listBuckets()
                .stream()
                .map(Bucket::name)
                .collect(toList());
    }

}
