package com.example.stock_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StockManagementApplication {

    public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(StockManagementApplication.class, args);
	}

}
