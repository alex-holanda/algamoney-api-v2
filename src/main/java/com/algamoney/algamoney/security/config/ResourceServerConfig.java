package com.algamoney.algamoney.security.config;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.util.StringUtils;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

	private final AlgamoneyApiProperties algamoneyApiProperties;

	@Bean
	public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
		
		http.logout(logoutConfig -> {
			logoutConfig.logoutSuccessHandler((request, response, authentication) -> {
				var returnTo = request.getParameter("returnTo");
				
				if (!StringUtils.hasText(returnTo)) {
					returnTo = algamoneyApiProperties.getSecurity().getAuthenticationServerUrl();
				}
				
				response.setStatus(302);
				response.sendRedirect(returnTo);
			});
		});

		return http.formLogin(Customizer.withDefaults()).build();
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			var authorities = jwt.getClaimAsStringList("authorities");

			if (authorities == null) {
				authorities = Collections.emptyList();
			}

			var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
			var grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);

			grantedAuthorities
					.addAll(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

			return grantedAuthorities;
		});

		return jwtAuthenticationConverter;
	}
}
