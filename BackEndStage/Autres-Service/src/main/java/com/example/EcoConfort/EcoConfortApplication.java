package com.example.EcoConfort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class EcoConfortApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcoConfortApplication.class, args);
	}



}
