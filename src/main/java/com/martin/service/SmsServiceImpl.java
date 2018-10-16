package com.martin.service;

import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendSms(String phoneNumber, String sms) {
        System.out.println("Sms " + sms + " send to number " + phoneNumber + " !");
    }
}
