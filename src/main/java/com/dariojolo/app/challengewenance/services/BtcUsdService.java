package com.dariojolo.app.challengewenance.services;

import reactor.core.publisher.Mono;

import java.util.OptionalDouble;

public interface BtcUsdService {
    public Mono<String> findCurrentPrice();
    public Double getAveragePrice(String stringDateStart, String stringDateFinish);
}
