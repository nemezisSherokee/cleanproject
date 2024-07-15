package house.learning.cleanproject.inventoryservice.configurations;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
@AllArgsConstructor
public class AwsConfig {

    private final AwsProperties awsProperties;
    private final AwsCredentialsProvider awsCredentialsProvider;
//    private final Environment env;

    @Bean
    public SqsClient sqsClient() {
        return SqsClient
                .builder()
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .region(Region.of(awsProperties.getS3().getRegion()))
                .credentialsProvider(awsCredentialsProvider::resolveCredentials)
                .endpointOverride(URI.create(awsProperties.getServiceEndpoint()))
                .build();
    }

    @Bean
    public S3Client amazonS3() {
        return S3Client
                .builder()
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .region(Region.of(awsProperties.getS3().getRegion()))
                .credentialsProvider(awsCredentialsProvider::resolveCredentials)
                .endpointOverride(URI.create(awsProperties.getServiceEndpoint()))
                .forcePathStyle(true)
                .build();
    }

}
