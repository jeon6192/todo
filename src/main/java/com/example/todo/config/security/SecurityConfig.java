package com.example.todo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] PERMIT_ALL = {
			"/", "/sign-up", "/login"
	};

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationEntryPoint authenticationEntryPoint,
						  CustomAccessDeniedHandler accessDeniedHandler) {
		this.customUserDetailsService = customUserDetailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authRequest ->
						authRequest
								.antMatchers(PERMIT_ALL).permitAll()
								.anyRequest().authenticated()
				)
				.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
					.authenticationEntryPoint(authenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler)

				.and()
				.csrf().disable()
				.formLogin()
				.usernameParameter("loginId")
				.passwordParameter("pwd")
				.loginProcessingUrl("/login")

				.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)

		;

		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomAuthenticationProvider customDaoAuthenticationProvider() {
		return new CustomAuthenticationProvider(customUserDetailsService, passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(customDaoAuthenticationProvider());
	}

	@Bean
	public CustomAuthenticationFilter customAuthenticationFilter() {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));
		customAuthenticationFilter.setAuthenticationManager(authenticationManager());
		customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationSuccessHandler());
		customAuthenticationFilter.afterPropertiesSet();
		return customAuthenticationFilter;
	}

	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationFailureHandler();
	}

}
