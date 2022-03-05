package com.springSecurity.jwt.config;

import com.springSecurity.jwt.filter.JwtFilter;
import com.springSecurity.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter 
{

	@Autowired
	private UserService userService;

	@Autowired
	private JwtFilter jwtFilter;

	// check user is valid or not (basic Auth not a part of JWT)
	// check userService class data with input
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
    	System.out.println("Reached Here...002");
		auth.userDetailsService(userService);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
    	System.out.println("Reached Here...001");
		return super.authenticationManagerBean();
	}
	
	// used for password encryption BCrypt Encoder
	@Bean
	public PasswordEncoder passwordEncoder()
	{
    	System.out.println("Reached Here...004");
		return new BCryptPasswordEncoder();
	}
	
	// list of methods who don't need token based security and set http STATELESS protocol
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
    	System.out.println("Reached Here...003");

		http.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated()
				   .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}
}
