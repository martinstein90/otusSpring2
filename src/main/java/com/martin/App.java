package com.martin;

import com.martin.service.OrderItemsService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.support.GenericMessage;

import java.util.Random;

@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@ComponentScan
@Configuration
@EnableIntegration
public class App {

    private static final String[] MENU = {"something satisfying", "something strong"};

    private static OrderItem generateOrderItem() {
        return new OrderItem( MENU[new Random().nextInt(MENU.length)], false);
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        DirectChannel itemsChannel = ctx.getBean("itemsChannel", DirectChannel.class);

        PublishSubscribeChannel foodChannel = ctx.getBean("foodChannel", PublishSubscribeChannel.class);
        foodChannel.subscribe(handler-> System.out.println("Ready orderItem: " + ((Food)handler.getPayload()).getFoodName()));

        DirectChannel checkChannel = ctx.getBean("checkChannel", DirectChannel.class);
        checkChannel.subscribe(handler-> System.out.println("Check: " + ((Check)handler.getPayload()).getCash()));


        OrderItem orderItem = generateOrderItem();
        System.out.println("New orderItem: " + orderItem.getItemName());

        itemsChannel.send(new GenericMessage<>(orderItem));


        ctx.close();
    }

    @Bean
    public IntegrationFlow cafeFlow(OrderItemsService orderItemsService) {
        return IntegrationFlows
                .from("itemsChannel")

                .<OrderItem, OrderItem>transform(orderItemsService::specify)

                .<OrderItem, Boolean>route(
                        OrderItem::isIced,
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf
                                        .handle("kitchenService", "cookHot")
                                )
                                .subFlowMapping(false, sf -> sf
                                        .handle("kitchenService", "cookCold")
                                )
                )
                .channel("foodChannel")
                .handle("cashboxService", "getPleaseByFood")
                .channel("checkChannel")
                .get();
    }


    @Bean
    public DirectChannel itemsChannel(){
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel foodChannel(){
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public DirectChannel checkChannel(){
        return MessageChannels.direct().get();
    }
}