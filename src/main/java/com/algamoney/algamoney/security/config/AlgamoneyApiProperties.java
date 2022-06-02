package com.algamoney.algamoney.security.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperties {

	@Valid
	@NotNull
	private Security security;
	
	@Getter
	@Setter
	public static class Security {
		
		@NotNull
		@Size(min = 1)
		private List<String> redirectsUrls = new ArrayList<>();
		
		private String authenticationServerUrl;
	}
}


