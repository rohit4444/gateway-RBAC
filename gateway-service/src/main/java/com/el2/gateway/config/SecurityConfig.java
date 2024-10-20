package com.el2.gateway.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.core.publisher.Flux;

@Configuration
@EnableWebFlux
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
		http.authorizeExchange(exchanges -> exchanges.pathMatchers("/api/authservice/auth/**").permitAll()
				.pathMatchers("/api/product-service/kc/**").hasAnyRole("client_adminn")
				.pathMatchers("/api/product-service/con/**").hasRole("client_userr").anyExchange().authenticated())
				.csrf().disable().oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

		return http.build();
	}

	@Bean
	public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
		ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwt -> extractAuthorities(jwt));
		return converter;
	}

	private Flux<GrantedAuthority> extractAuthorities(Jwt jwt) {
		Set<GrantedAuthority> authorities = new HashSet<>();

		Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
		if (realmAccess != null && realmAccess.containsKey("roles")) {
			Object rolesObject = realmAccess.get("roles");
			if (rolesObject instanceof Collection) {
				@SuppressWarnings("unchecked")
				Collection<String> roles = (Collection<String>) rolesObject;
				roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
			}
		}

		Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
		if (resourceAccess != null) {
			resourceAccess.forEach((key, value) -> {
				if (value instanceof Map) {
					@SuppressWarnings("unchecked")
					Map<String, Object> resource = (Map<String, Object>) value;
					if (resource.containsKey("roles")) {
						Object rolesObject = resource.get("roles");
						if (rolesObject instanceof Collection) {
							@SuppressWarnings("unchecked")
							Collection<String> roles = (Collection<String>) rolesObject;
							roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
						}
					}
				}
			});
		}

		return Flux.fromIterable(authorities);
	}
}
