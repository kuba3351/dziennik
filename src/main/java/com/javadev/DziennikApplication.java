package com.javadev;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.LogManager;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class DziennikApplication {
	public static void main(String[] args) {
		SpringApplication.run(DziennikApplication.class, args);
	}
}
