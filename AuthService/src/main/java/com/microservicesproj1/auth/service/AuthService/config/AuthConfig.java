package com.microservicesproj1.auth.service.AuthService.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.microservicesproj1.auth.service.AuthService.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class AuthConfig {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/auth/test")
				.permitAll().anyRequest().authenticated());

		http.csrf(csrf -> csrf.disable());

		http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(withDefaults());

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(customUserDetailsService);
		return provider;
	}
}
