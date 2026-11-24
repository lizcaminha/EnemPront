package com.info3c.enemPront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EnemProntApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnemProntApplication.class, args);
    }
}
