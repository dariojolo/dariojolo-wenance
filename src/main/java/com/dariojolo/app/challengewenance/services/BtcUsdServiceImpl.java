package com.dariojolo.app.challengewenance.services;

import com.dariojolo.app.challengewenance.entities.BtcUsdPrice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BtcUsdServiceImpl implements BtcUsdService {

    @Autowired
    private BtcUsdClientService clientService;

    private Map<Long, String> mapPrices = new HashMap<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    @Override
    public Mono<String> findCurrentPrice() {
        Mono<String> price = clientService.getbtcUsdPriceWebClient();
        return price;
    }

    @Override
    public Double getAveragePrice(String stringDateStart, String stringDateFinish) {
        long dateStartMilli = 0;
        long dateFinishMilli = 0;
        try {
            dateStartMilli = sdf.parse(stringDateStart).getTime();
            dateFinishMilli = sdf.parse(stringDateFinish).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> resultados = getPricesRange(dateStartMilli,dateFinishMilli);

        for (String resultado: resultados){
            System.out.println("resultado: " + resultado);
        }
        System.out.println("Tamaño mapa: " + mapPrices.size());
        System.out.println("Tamaño lista: " + resultados.size());

        OptionalDouble average = resultados
                .stream()
                .mapToLong(a -> (long) Double.parseDouble(a))
                .average();
        return average.getAsDouble();
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${scheduler.rate.delay}")
    public void findUpdatePrice() throws JsonProcessingException {
        System.out.println("Fecha: " + new Date());
        mapPrices.put(System.currentTimeMillis(), parseJsonPrice(clientService.getbtcUsdPriceWebClient()));
    }



    private String parseJsonPrice(Mono<String> price) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        BtcUsdPrice jsonPrice = mapper.readValue(price.block(), BtcUsdPrice.class);
        return jsonPrice.getLastPrice();
    }

    public List<String> getPricesRange(long start, long end) {
        List<String> list = new ArrayList<>();

        for (long i = start; i < end; i++) {
            String value = mapPrices.get(i);

            if (value != null)
                list.add(value);
        }

        return list;
    }
}