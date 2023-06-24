package com.example.stellarmemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.stellarmemo.dao")
public class StellarMemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StellarMemoApplication.class, args);
	}

}
