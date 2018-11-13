package com.martin.rest;

import com.martin.domain.CurrentTimeDto;
import com.martin.services.CurrentTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoscowCurrentTimeRestController {

    @Autowired
    private CurrentTimeService moscowCurrentTimeService;

    @GetMapping("/time")
    public CurrentTimeDto getCurrentTime() {
        System.out.println("getCurrentTime");
        return moscowCurrentTimeService.getCurrentTime();
    }
}