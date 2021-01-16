package com.dariojolo.app.challengewenance.controllers;

import com.dariojolo.app.challengewenance.entities.BtcUsdPrice;
import com.dariojolo.app.challengewenance.entities.Horarios;
import com.dariojolo.app.challengewenance.services.BtcUsdService;
import com.dariojolo.app.challengewenance.services.BtcUsdServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.DataInput;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/prices")
public class BtcUsdController {

    @Autowired
    private BtcUsdService service;

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<String> getPrice() {
        System.out.println("LLAMADA AL REST");
        Mono<String> price = service.findCurrentPrice();
        if (price == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(price.block());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Double> getPrice(@PathVariable ("id") int id) {
        double d = service.getAveragePrice("21:26:00 15/01/2021","21:28:30 15/01/2021");

        return ResponseEntity.ok(d);
    }
    @PostMapping()
    public ResponseEntity<Double> getPrice(@RequestBody String horariosIn) throws IOException {
       // double d = service.getAveragePrice("21:26:00 15/01/2021","21:28:30 15/01/2021");
        Horarios horarios = mapper.readValue(horariosIn, Horarios.class);
        String initDate = horarios.getInit();
        String endDate = horarios.getEnd();
        double d = service.getAveragePrice(initDate,endDate);
        return ResponseEntity.ok(d);
    }

    /*ObjectMapper mapper = new ObjectMapper();
        Horario horarios = mapper.readValue(horariosParam, Horario.class);
        String initDate = horarios.getInit();
        String endDate = horarios.getEnd();
        double d = service.getAveragePrice(initDate,endDate);

        return ResponseEntity.ok(d);
       */
}
