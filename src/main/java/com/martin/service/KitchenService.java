package com.martin.service;

import org.springframework.stereotype.Service;

@Service
public class KitchenService {

    public Food cookHot(OrderItem orderItem) throws InterruptedException {
        System.out.println("Cooking hot " + orderItem.getItemName());
        Food cook = cook(orderItem);
        System.out.println("Cooking hot " + orderItem.getItemName() + " done");
        return cook;
    }

    public Food cookCold(OrderItem orderItem) throws InterruptedException {
        System.out.println("Cooking cold " + orderItem.getItemName());
        Food cook = cook(orderItem);
        System.out.println("Cooking cold " + orderItem.getItemName() + " done");
        return cook;
    }

    public Food cook(OrderItem orderItem) throws InterruptedException {
        Thread.sleep(1000);
        return new Food(orderItem.getItemName());
    }
}
