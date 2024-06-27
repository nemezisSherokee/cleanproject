package com.example.orderprocessing.configurations;


import com.example.infrastructures.entities.config.EntitiesScanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        EntitiesScanConfiguration.class
})
@Configuration
public class GeneralConfig {
}
