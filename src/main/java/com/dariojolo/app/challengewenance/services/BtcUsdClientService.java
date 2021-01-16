package com.dariojolo.app.challengewenance.services;

import com.dariojolo.app.challengewenance.entities.BtcUsdPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class BtcUsdClientService {

    @Value("${btcusd.service.uri}")
    private String btcUsdUri;

    public Mono<String> getbtcUsdPriceWebClient() {
        return WebClient.create("https://cex.io/api/last_price/BTC")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/USD").build())
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.just(new Exception (response.statusCode().toString())))
                .bodyToMono(String.class)
                .log("error obteniendo precio")
                .onErrorResume(throwable -> {
                    return Mono.error(new Exception ("FALLO " + HttpStatus.NOT_FOUND));
                });

    }
}