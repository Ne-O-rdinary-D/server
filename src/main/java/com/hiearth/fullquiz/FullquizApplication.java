package com.hiearth.fullquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
public class FullquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullquizApplication.class, args);
	}

}
