package com.vandemarket.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class VandemarketApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(VandemarketApigatewayApplication.class, args);
    }

}
