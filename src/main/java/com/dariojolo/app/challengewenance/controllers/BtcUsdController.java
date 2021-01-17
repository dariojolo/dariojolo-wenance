package com.dariojolo.app.challengewenance.controllers;

import com.dariojolo.app.challengewenance.entities.Fechas;
import com.dariojolo.app.challengewenance.entities.ResponseObject;
import com.dariojolo.app.challengewenance.services.BtcUsdService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/prices")
public class BtcUsdController {

    @Autowired
    private BtcUsdService service;

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<String> getCurrentPrice() throws JsonProcessingException {
        String price = service.findCurrentPrice();
        if (price == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(price);
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> getPriceAvg(@RequestBody String horariosIn) throws IOException {
        Fechas fechas = mapper.readValue(horariosIn, Fechas.class);

        return ResponseEntity.ok(service.getAveragePrice(fechas.getStart(), fechas.getEnd()));
    }
}

