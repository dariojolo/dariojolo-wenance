package com.dariojolo.app.challengewenance.services;

import com.dariojolo.app.challengewenance.entities.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BtcUsdService {
    String findCurrentPrice() throws JsonProcessingException;

    ResponseObject getAveragePrice(String stringDateStart, String stringDateFinish);
}
