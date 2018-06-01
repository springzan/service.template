package com.example.service.template;

import com.example.service.template.boot.BaseApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:dubbo-consumer.xml"})
public class Application extends BaseApplication{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
