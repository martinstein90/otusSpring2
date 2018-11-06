package com.martin.service;

import org.springframework.stereotype.Service;

@Service
public class CashboxService {

    public Check getPleaseByFood(Food food) {
        System.out.println("Please check by " + food.getFoodName());

        if(food.getFoodName().equals("whiskey"))
            return new Check(100);
        else
            return new Check(10);
    }
}
