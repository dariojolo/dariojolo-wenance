package com.dariojolo.app.challengewenance;

import com.dariojolo.app.challengewenance.controllers.BtcUsdController;
import com.dariojolo.app.challengewenance.entities.DateIn;
import com.dariojolo.app.challengewenance.entities.Fechas;
import com.dariojolo.app.challengewenance.entities.ResponseObject;
import com.dariojolo.app.challengewenance.services.BtcUsdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BtcUsdController.class)
public class BtcUsdControllerTest {
    @MockBean
    private BtcUsdService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockmvc() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void testgetCurrentPrice() throws Exception {
        Mockito.when(service.findCurrentPrice("12:49:10 17/01/2021")).thenReturn("35678.9");

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/prices/")
                .content(asJsonString(new DateIn("12:49:10 17/01/2021")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testgetPriceAvg() throws Exception {
        ResponseObject response = new ResponseObject(35678.9d, 0.023d);
        Fechas fecha01 = Fechas.builder().start("12:49:10 17/01/2021").end("12:49:30 17/01/2021").build();

        Mockito.when(service.getAveragePrice(fecha01.getStart(), fecha01.getEnd())).thenReturn(response);


        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/prices/avg")
                .content(asJsonString(new Fechas("12:49:10 17/01/2021", "12:49:30 17/01/2021")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
