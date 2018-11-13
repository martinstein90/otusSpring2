package com.martin.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CurrentTimeDto {

    private String currentTime;

    @JsonProperty("time")
    public void unpackCurrentTime(String currentTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.currentTime = dateFormat.format(new Date(Long.valueOf(currentTime)));
    }
}
