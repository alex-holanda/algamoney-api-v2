package com.algamoney.algamoney.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
				new OAuth2AuthorizationServerConfigurer();

//        authorizationServerConfigurer.authorizationEndpoint(
//                customizer -> customizer.consentPage("/oauth2/consent"));

		RequestMatcher endpointsMatcher = authorizationServerConfigurer
				.getEndpointsMatcher();

		http.securityMatcher(endpointsMatcher)
				.authorizeHttpRequests(authorize -> {
					authorize
							.requestMatchers("/assets/**", "/webjars/**", "/login").permitAll()
							.anyRequest().authenticated();
				})
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.formLogin(Customizer.withDefaults())
				.exceptionHandling(exceptions -> {
					exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
				})
				.apply(authorizationServerConfigurer);

		return http.formLogin(customizer -> customizer.loginPage("/login")).build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(Customizer.withDefaults())
				.csrf().disable()
				.cors();

		return http.build();
	}

	@Bean
	public AuthorizationServerSettings providerSettings() {
		return AuthorizationServerSettings.builder()
				.issuer("http://localhost:8080")
				.build();
	}

	@Bean
	public RegisteredClientRepository repository(PasswordEncoder passwordEncoder, JdbcOperations jdbcOperations) {
//        return new JdbcRegisteredClientRepository(jdbcOperations);
		var backend = RegisteredClient
				.withId("1")
				.clientId("perfilnet-web-client")
				.clientSecret(passwordEncoder.encode("perfilnet-password"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope("read")
				.tokenSettings(TokenSettings.builder()
						.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
						.accessTokenTimeToLive(Duration.ofMinutes(30))
						.build())
				.build();

		var backendWeb = RegisteredClient
				.withId("2")
				.clientId("perfilnet-web")
				.clientSecret(passwordEncoder.encode("perfilnet-password"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.scope("read")
				.scope("write")
				.tokenSettings(TokenSettings.builder()
						.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
						.accessTokenTimeToLive(Duration.ofMinutes(30))
						.build())
				.redirectUri("http://127.0.0.1:8080/authorized")
				.clientSettings(ClientSettings.builder()
						.requireAuthorizationConsent(true)
						.build())
				.build();

		return new InMemoryRegisteredClientRepository(Arrays.asList(backend, backendWeb));
	}

	@Bean
	public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
//        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
		return new InMemoryOAuth2AuthorizationService();
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
		char[] keyStorePass = properties.getPassword().toCharArray();
		String keypairAlias = properties.getKeypairAlias();
		Resource jksLocation = properties.getJksLocation();

		InputStream inputStream = jksLocation.getInputStream();
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(inputStream, keyStorePass);

		RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

		return new ImmutableJWKSet<>(new JWKSet(rsaKey));
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			Authentication authentication = context.getPrincipal();

			if (authentication.getPrincipal() instanceof User) {
				User user = (User) authentication.getPrincipal();
//				UserEntity userEntity = userRepository.findActivesByEmail(user.getUsername()).orElseThrow();

				Set<String> authorities = new HashSet<>();
				user.getAuthorities().forEach(grantedAuthority -> authorities.add(grantedAuthority.getAuthority()));

//				context.getClaims().claim("user_id", userEntity.getId());
				context.getClaims().claim("authorities", authorities);
			}
		};
	}

	@Bean
	public OAuth2AuthorizationConsentService consetService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
//        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
		return new InMemoryOAuth2AuthorizationConsentService();
	}
}