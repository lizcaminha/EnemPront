package com.info3c.enemPront.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "enempront")
public record EnemProntProperties(
        @DefaultValue("America/Cuiaba") String timezone,
        @DefaultValue Admin admin) {

    public record Admin(
            @DefaultValue("admin@enempront.local") String email,
            @DefaultValue("Administrador") String nome,
            String password) {
    }
}
