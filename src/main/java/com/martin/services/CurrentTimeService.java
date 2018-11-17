package com.martin.services;

import com.martin.domain.CurrentTimeDto;

public interface CurrentTimeService {
    CurrentTimeDto getCurrentTimeFromYandex();
    CurrentTimeDto getCurrentTimeFromServer();
}
