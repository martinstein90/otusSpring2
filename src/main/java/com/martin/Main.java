package com.martin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args)  {
        SpringApplication.run(Main.class);//Todo Шелл не позволяет запустить тесты! если в гредле его отключить, то ок
    }
}
