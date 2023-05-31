package com.album2me.repost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RepostApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepostApplication.class, args);
	}

}
