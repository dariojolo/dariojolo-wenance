package com.dariojolo.app.challengewenance.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class BtcUsdClientService {

    public Mono<String> getbtcUsdPriceWebClient() {
        return WebClient.create("https://cex.io/api/last_price/BTC")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/USD").build())
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.just(new Exception(response.statusCode().toString())))
                .bodyToMono(String.class)
                .log("Obteniendo precio")
                .onErrorResume(throwable -> {
                    return Mono.error(new Exception("FALLO " + HttpStatus.NOT_FOUND));
                });

    }
}