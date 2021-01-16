package com.dariojolo.app.challengewenance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class ChallengeWenanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeWenanceApplication.class, args);


	}
}
