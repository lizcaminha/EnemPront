package com.info3c.enemPront.config;

import java.time.Clock;
import java.time.ZoneId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    private static final int FORCA_BCRYPT = 12;

    @Bean
    public Clock clock(EnemProntProperties propriedades) {
        return Clock.system(ZoneId.of(propriedades.timezone()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(FORCA_BCRYPT);
    }
}
