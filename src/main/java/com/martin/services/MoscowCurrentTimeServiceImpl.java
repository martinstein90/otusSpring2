package com.martin.services;

import com.martin.domain.CurrentTimeDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MoscowCurrentTimeServiceImpl implements CurrentTimeService {

    private final RestTemplate template;

    public MoscowCurrentTimeServiceImpl() {
        template = new RestTemplate();
    }

    @Override
    @HystrixCommand(groupKey = "MoscowCurrentTimeService", commandKey = "getCurrentTime")
    public CurrentTimeDto getCurrentTime() {
        return template.getForObject("https://yandex.com/time/sync.json?geo=213", CurrentTimeDto.class);
    }
}

