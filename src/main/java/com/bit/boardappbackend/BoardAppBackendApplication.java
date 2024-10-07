package com.bit.boardappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoardAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardAppBackendApplication.class, args);
	}

}
