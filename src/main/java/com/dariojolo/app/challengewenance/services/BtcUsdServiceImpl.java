package com.dariojolo.app.challengewenance.services;

import com.dariojolo.app.challengewenance.entities.BtcUsdPrice;
import com.dariojolo.app.challengewenance.entities.ResponseObject;
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
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String findCurrentPrice() throws JsonProcessingException {
        Mono<String> price = clientService.getbtcUsdPriceWebClient();

        return parseJsonPrice(price);
    }

    @Override
    public ResponseObject getAveragePrice(String stringDateStart, String stringDateFinish) {
        long dateStartMilli = 0;
        long dateFinishMilli = 0;
        try {
            dateStartMilli = sdf.parse(stringDateStart).getTime();
            dateFinishMilli = sdf.parse(stringDateFinish).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> results = getPricesRange(dateStartMilli, dateFinishMilli);

        Double average = calcAverage(results);
        ResponseObject response = new ResponseObject(average, calDifPorcentual(average));

        return response;
    }

    private double calDifPorcentual(double average) {
        Double max = mapPrices
                .entrySet()
                .stream()
                .mapToDouble(v -> Double.parseDouble(v.getValue()))
                .max().orElseThrow(NoSuchElementException::new);
        return (((max / average) * 100) - 100);
    }

    private double calcAverage(List<String> resultados) {
        try {
            return resultados
                    .stream()
                    .mapToLong(a -> (long) Double.parseDouble(a))
                    .average().getAsDouble();
        } catch (NoSuchElementException e) {
            return 0d;
        }
    }

    @PostConstruct
    @Scheduled(fixedRateString = "${scheduler.rate.delay}")
    public void findUpdatePrice() throws JsonProcessingException {
        mapPrices.put(System.currentTimeMillis(), parseJsonPrice(clientService.getbtcUsdPriceWebClient()));
    }

    private String parseJsonPrice(Mono<String> price) throws JsonProcessingException {
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