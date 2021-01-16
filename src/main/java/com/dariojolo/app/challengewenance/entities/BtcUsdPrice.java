package com.dariojolo.app.challengewenance.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BtcUsdPrice implements Serializable {
    @JsonProperty("lprice")
    private String lastPrice;
}
