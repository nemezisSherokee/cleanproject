package house.learning.cleanproject.inventoryservice.configurations;

//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

@Configuration
@Profile("local-stack")
@AllArgsConstructor
public class AwsConfigLocalStack {

    private final AwsProperties awsProperties;

    @Bean
    public StaticCredentialsProvider awsCredentialsProvider() {
        return  StaticCredentialsProvider
                .create(AwsBasicCredentials.create(awsProperties.getAccess(),
                awsProperties.getSecret()));
    }

}
