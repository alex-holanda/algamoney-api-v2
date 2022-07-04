package com.algamoney.algamoney.common.jpa.config;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.algamoney.algamoney.security.AlgaSecurity;
import com.algamoney.algamoney.security.domain.model.Usuario;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfig {

	@Bean
	public DateTimeProvider auditingDateTimeProvider() {
		return () -> Optional.of(OffsetDateTime.now());
	}

	@Bean
	public AuditorAware<Usuario> auditorAware(AlgaSecurity algaSecurity) {
		return algaSecurity::getAuthenticatedUser;
	}
}
