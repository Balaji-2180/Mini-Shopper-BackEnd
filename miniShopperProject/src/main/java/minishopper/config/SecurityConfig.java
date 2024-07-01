package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;




import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Service.CustomUserDetailsService;
import com.example.demo.security.JwtAuthenticationFilter;



import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//System.out.println("in SecurityConfig securityFilterChain");
		http.cors((cors)-> cors.configurationSource(corsFilter())).csrf().disable().authorizeHttpRequests()
		
		.requestMatchers("/users/loginUser").permitAll()
		.requestMatchers("/users/newUser").permitAll()
		.requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated().and()

		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
	//	System.out.println("in SecurityConfig authenticationManager");
		
		return authenticationConfiguration.getAuthenticationManager();
	} 
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	CorsConfigurationSource corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Content-Type");
		configuration.addAllowedHeader("Accept");
		configuration.addAllowedMethod("GET");
		configuration.addAllowedMethod("POST");
		configuration.addAllowedMethod("PATCH");
		configuration.addAllowedMethod("PUT");
		configuration.addAllowedMethod("DELETE");
		configuration.addAllowedMethod("OPTIONS");
		configuration.setMaxAge(3600L);

		source.registerCorsConfiguration("/**", configuration);
		FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(source));
		filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return source;
	}
	
	
	
	
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		
		//System.out.println("in SecurityConfig authenticationProvider");
		
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	

}
