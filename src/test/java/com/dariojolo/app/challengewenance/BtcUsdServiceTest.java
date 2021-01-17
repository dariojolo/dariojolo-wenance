package com.dariojolo.app.challengewenance;

import com.dariojolo.app.challengewenance.entities.ResponseObject;
import com.dariojolo.app.challengewenance.services.BtcUsdClientService;
import com.dariojolo.app.challengewenance.services.BtcUsdService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
public class BtcUsdServiceTest {

    @Mock
    private BtcUsdClientService serviceWebClient;

    @Autowired
    private BtcUsdService service;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(serviceWebClient.getbtcUsdPriceWebClient())
                .thenReturn(Mono.just("35678.9"));
    }

    @Test
    public void whenFindCurrentPrice_ThenReturnPrice() throws JsonProcessingException {
        String found = service.findCurrentPrice();
        Assertions.assertThat(found.equals("35678.9"));
    }

    @Test
    public void WhenGetAverageProce_ThenReturnNotNull() {
        ResponseObject response = service.getAveragePrice("12:49:10 17/01/2021", "12:49:30 17/01/2021");
        Assertions.assertThat(response).isNotNull();
    }
}
