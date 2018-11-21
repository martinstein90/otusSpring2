package com.martin.rest;

import com.martin.domain.CurrentTimeDto;
import com.martin.services.CurrentTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MoscowCurrentTimeRestController {

    @Autowired
    private CurrentTimeService moscowCurrentTimeService;

    @GetMapping("/time")
    public CurrentTimeDto getCurrentTime() {
        log.info("getCurrentTime");
        return moscowCurrentTimeService.getCurrentTimeFromYandex();
    }
}