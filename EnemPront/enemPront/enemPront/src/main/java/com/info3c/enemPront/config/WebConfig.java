package com.info3c.enemPront.config;

import com.info3c.enemPront.web.ContaAtivaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ContaAtivaInterceptor contaAtiva;

    public WebConfig(ContaAtivaInterceptor contaAtiva) {
        this.contaAtiva = contaAtiva;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contaAtiva)
                .excludePathPatterns("/styles.css", "/app.js", "/favicon.ico", "/error");
    }
}
