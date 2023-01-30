package com.ticket.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ticket.util.PasswordUtilization;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.httpBasic();
		http.formLogin();
		http.authorizeHttpRequests()
				.antMatchers(HttpMethod.POST, "/users").permitAll()
				.antMatchers(HttpMethod.POST, "/users/login").permitAll()
				.antMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin/tickets/**").hasRole("ADMIN")
				.antMatchers("/orders/**", "/tickets/**").hasAnyRole("ADMIN", "USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/index").permitAll();

	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService).passwordEncoder(PasswordUtilization.encoder());
	}




}
