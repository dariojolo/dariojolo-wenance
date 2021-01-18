package com.dariojolo.app.challengewenance.services;

import com.dariojolo.app.challengewenance.entities.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.ParseException;

public interface BtcUsdService {
    String findCurrentPrice(String stringDate) throws JsonProcessingException, ParseException;

    ResponseObject getAveragePrice(String stringDateStart, String stringDateFinish);
}
