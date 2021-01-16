package com.dariojolo.app.challengewenance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean("webClient")
    public WebClient.Builder getWebClientBuilder(){
        return WebClient.builder();
    }
}
