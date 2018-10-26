package com.martin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendSms(String phoneNumber, String sms) {
        log.info("Sms " + sms + " send to number " + phoneNumber + " !");
    }
}
