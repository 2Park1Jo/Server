package com.twopark1jo.lobster;

import com.twopark1jo.lobster.translation.PapagoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@RequiredArgsConstructor
public class LobsterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LobsterApplication.class, args);
	}
}
