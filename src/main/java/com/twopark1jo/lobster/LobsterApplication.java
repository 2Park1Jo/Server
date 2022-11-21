package com.twopark1jo.lobster;

import com.twopark1jo.lobster.department.chat.ChatContentRepository;
import com.twopark1jo.lobster.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RequiredArgsConstructor
public class LobsterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LobsterApplication.class, args);
	}
}
