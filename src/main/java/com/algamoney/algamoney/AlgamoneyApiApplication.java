package com.algamoney.algamoney;

import com.algamoney.algamoney.common.io.Base64ProtocolResolver;
import com.algamoney.algamoney.common.jpa.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgamoneyApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));

		var app = new SpringApplication(AlgamoneyApiApplication.class);

		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
	}

}
