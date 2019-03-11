package com.stackroute.pie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableEurekaClient
@SpringBootApplication
public class UserregApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserregApplication.class, args);
	}

}

