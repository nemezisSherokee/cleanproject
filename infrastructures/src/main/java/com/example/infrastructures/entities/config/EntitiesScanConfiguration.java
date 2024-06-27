package com.example.infrastructures.entities.config;

import com.example.infrastructures.entities.Order;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EntityScan(basePackages = {"com.example.infrastructures.entities"})
@EntityScan(basePackageClasses = {Order.class})
public class EntitiesScanConfiguration {
}
