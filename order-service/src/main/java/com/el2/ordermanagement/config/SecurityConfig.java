package com.el2.ordermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests
				.anyRequest()
				.authenticated()) // All requests must
				.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
		).cors(cors -> cors.and()) // Enable CORS
				.csrf(csrf -> csrf.disable()) // Disable CSRF
				.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt()); // JWT is configured via
																							// application.yml
		return http.build();
	}
}