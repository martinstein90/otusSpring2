package com.martin.services;

import com.martin.domain.CurrentTimeDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class MoscowCurrentTimeServiceImpl implements CurrentTimeService {

    private final RestTemplate template;

    public MoscowCurrentTimeServiceImpl() {
        template = new RestTemplate();
    }

    @Override
    @HystrixCommand(groupKey = "MoscowCurrentTimeService",
                    commandKey = "getCurrentTimeFromYandex",
                    fallbackMethod = "getCurrentTimeFromServer")
    public CurrentTimeDto getCurrentTimeFromYandex() {
        log.info("From yandex");
        return template.getForObject("https://yandex1.com/time/sync.json?geo=213", CurrentTimeDto.class);
    }

    @Override
    public CurrentTimeDto getCurrentTimeFromServer() {
        log.info("From localhost");
        CurrentTimeDto currentTimeDto = new CurrentTimeDto();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        currentTimeDto.setCurrentTime(dateFormat.format(new Date()));
        return currentTimeDto;
    }
}

