package com.algamoney.algamoney.security.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperties {

	@Valid
	@NotNull
	private Security security;
	
	@Valid
	@NotNull
	private JksProperties jks;
	
	@Getter
	@Setter
	public static class Security {
		
		@NotNull
		@Size(min = 1)
		private List<String> redirectsUrls = new ArrayList<>();
		
		private String issuerServerUrl;
	}
	
	@Getter
	@Setter
	public static class JksProperties {
		
		@NotBlank
		private String keypass;
		
		@NotBlank
		private String storepass;
		
		@NotBlank
		private String alias;
		
		@NotBlank
		private String path;
	}
}


