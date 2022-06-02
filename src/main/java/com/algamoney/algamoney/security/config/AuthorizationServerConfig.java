package com.algamoney.algamoney.security.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class AuthorizationServerConfig {

	private final PasswordEncoder passwordEncoder;
	private final AlgamoneyApiProperties algamoneyApiProperties;
	
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		var webClient = RegisteredClient
				.withId(UUID.randomUUID().toString())
				.clientId("web-client")
				.clientSecret(passwordEncoder.encode("algamoney-web-client"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUris(uris -> uris.addAll(algamoneyApiProperties.getSecurity().getRedirectsUrls()))
				.scope("read")
				.scope("write")
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofMinutes(30))
						.refreshTokenTimeToLive(Duration.ofHours(1)).build())
				.clientSettings(ClientSettings.builder()
						.requireAuthorizationConsent(true).build())
				.build();
		
		return new InMemoryRegisteredClientRepository(Arrays.asList(webClient));
	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		
		return http.formLogin(Customizer.withDefaults()).build();
	}
	
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtBuildCustomizer() {
		return (context) -> {
			var authenticationToken = context.getPrincipal();
			var user = (AuthorizationUser) authenticationToken.getPrincipal();
			
			var authorities = new HashSet<String>();
			
			user.getAuthorities().forEach(grantedAuthority -> authorities.add(grantedAuthority.getAuthority()));
			
			context.getClaims().claim("user:id", user.getUserId());
			context.getClaims().claim("user:nome", user.getFullName());
			context.getClaims().claim("authorities", authorities);
		};
	}
	
	@Bean
	public JWKSet jwkSet() throws JOSEException {
		var rsa = new RSAKeyGenerator(2048).keyUse(KeyUse.SIGNATURE).keyID(UUID.randomUUID().toString()).generate();
		
		return new JWKSet(rsa);
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
		return (jwkSelector, securityContext) ->  jwkSelector.select(jwkSet);
	}
	
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}
	
	@Bean
	public ProviderSettings providerSettings() {
		return ProviderSettings.builder().issuer(algamoneyApiProperties.getSecurity().getAuthenticationServerUrl()).build();
	}
}
