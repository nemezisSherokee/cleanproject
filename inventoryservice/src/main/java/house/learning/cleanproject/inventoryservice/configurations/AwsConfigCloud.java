package house.learning.cleanproject.inventoryservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

@Configuration
@Profile("cloud")
public class AwsConfigCloud {

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {

        return  DefaultCredentialsProvider.create();
    }

}
