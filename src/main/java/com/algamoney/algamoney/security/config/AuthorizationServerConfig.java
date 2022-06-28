package com.algamoney.algamoney.security.config;

import java.security.KeyStore;
import java.time.Duration;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.algamoney.algamoney.security.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class AuthorizationServerConfig {

	private final PasswordEncoder passwordEncoder;
	private final AlgamoneyApiProperties algamoneyApiProperties;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		return http.formLogin(Customizer.withDefaults()).build();
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
//		var webClient = RegisteredClient
//				.withId(UUID.randomUUID().toString())
//				.clientId("web-client")
//				.clientSecret(passwordEncoder.encode("algamoney-web-client"))
//				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//				.redirectUris(uris -> uris.addAll(algamoneyApiProperties.getSecurity().getRedirectsUrls()))
//				.scope("read")
//				.scope("write")
//				.tokenSettings(TokenSettings.builder()
//						.accessTokenTimeToLive(Duration.ofMinutes(30))
//						.refreshTokenTimeToLive(Duration.ofHours(1)).build())
//				.clientSettings(ClientSettings.builder()
//						.requireAuthorizationConsent(true).build())
//				.build();
//		
//		return new InMemoryRegisteredClientRepository(Arrays.asList(webClient));

		var registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

		var client = RegisteredClient.withId("e4a295f7-0a5f-4cbc-bcd3-d870243d1b05").clientId("web-client")
				.clientSecret(passwordEncoder.encode("algamoney-web-client"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN).scope("read").scope("write")
				.redirectUris(uris -> uris.addAll(algamoneyApiProperties.getSecurity().getRedirectsUrls()))
				.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30))
						.refreshTokenTimeToLive(Duration.ofHours(1)).reuseRefreshTokens(false).build())
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()).build();

		registeredClientRepository.save(client);

		return registeredClientRepository;
	}

	@Bean
	public OAuth2AuthorizationService auth2AuthorizationService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	public OAuth2AuthorizationConsentService auth2AuthorizationConsentService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtBuildCustomizer(UsuarioRepository usuarioRepository) {
		return (context) -> {
			var authentication = context.getPrincipal();

			if (authentication.getPrincipal() instanceof User) {
				System.out.println("Veio aqui");
				var user = (User) authentication.getPrincipal();

				var usuario = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();

				var authorities = new HashSet<String>();
				user.getAuthorities().forEach(grantedAuthority -> authorities.add(grantedAuthority.getAuthority()));

				context.getClaims().claim("user:id", usuario.getId().toString());
				context.getClaims().claim("user:nome", usuario.getNome());
				context.getClaims().claim("authorities", authorities);
			}

		};
	}

	@Bean
	public JWKSet jwkSet() throws Exception {
		var jksProperties = algamoneyApiProperties.getJks();
		var inputStream = new ClassPathResource(jksProperties.getPath()).getInputStream();
		var keyStore = KeyStore.getInstance("JKS");
		keyStore.load(inputStream, jksProperties.getStorepass().toCharArray());

		var rsaKey = RSAKey.load(keyStore, jksProperties.getAlias(), jksProperties.getKeypass().toCharArray());

		return new JWKSet(rsaKey);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
	public ProviderSettings providerSettings() {
		return ProviderSettings.builder().issuer(algamoneyApiProperties.getSecurity().getIssuerServerUrl())
				.build();
	}
}
