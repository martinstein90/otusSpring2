package com.martin.service;

import com.martin.domain.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsService {

    public OrderItem specify(OrderItem orderItem) {

        if(orderItem.getItemName().equals("something satisfying"))
            return new OrderItem("meat", orderItem.isIced());
        if(orderItem.getItemName().equals("something strong"))
            return new OrderItem("whiskey", false);
        else
            return new OrderItem("French fries", true);
    }
}
