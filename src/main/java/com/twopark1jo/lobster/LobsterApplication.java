package com.twopark1jo.lobster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class LobsterApplication {

	public static void main(String[] args) {

		SpringApplication.run(LobsterApplication.class, args);
	}

}
