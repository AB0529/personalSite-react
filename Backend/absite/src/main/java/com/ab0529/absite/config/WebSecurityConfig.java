package com.ab0529.absite.config;

import com.ab0529.absite.component.JwtAuthenticationEntryPoint;
import com.ab0529.absite.component.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	/**
	 * Unauthorized routes: /api/auth/**
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				// Don't authenticate this particular request
				.authorizeRequests().antMatchers("/api/auth/**").permitAll().
				// Authenticate everything else
				anyRequest().authenticated().and().
				// Use stateless session
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().sameOrigin().disable();
		// Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
