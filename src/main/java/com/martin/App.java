package com.martin;

import com.martin.domain.Country;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@ComponentScan
@Configuration
public class App {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders httpHeaders = restTemplate.headForHeaders("https://restcountries.eu/rest/v2/alpha/col");
        httpHeaders.add();

        Country[] countries = restTemplate.getForObject("https://restcountries.eu/rest/v2/alpha/col", Country[].class);
        for (Country country: countries) {
            System.out.println("country = " + country.getName());
        }

    }


}