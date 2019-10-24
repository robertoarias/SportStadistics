package com.sports.frontend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.sports.frontend.SportsGestorConfig;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomLoginProvider loginAuthProvider;
	
	private static final Integer SESSION_TIMEOUT_MIN=15;
	
	//@Autowired
	//private SportsGestorConfig config;
	
	@Override	
	protected void configure(HttpSecurity http) throws Exception {
		
		 http
		 .authorizeRequests().antMatchers("/js/**").permitAll()		 
		 .antMatchers("/css/**").permitAll()
		 .antMatchers("/fonts/**").permitAll()
		 .antMatchers("/images/**").permitAll()		 
		 .mvcMatchers("/login").hasAnyRole("EDITOR,ADMIN,READONLY,UNAUTH,USERMGR,MASTER")
		 .mvcMatchers("/").hasAnyRole("EDITOR,ADMIN,READONLY,USERMGR,MASTER")
		 .anyRequest().authenticated().and()
         .formLogin().failureUrl("/login?error").successForwardUrl("/").defaultSuccessUrl("/")
         .loginPage("/login").successHandler(new AuthSessionHandler(SESSION_TIMEOUT_MIN)).permitAll().and()
         .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll();            
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(loginAuthProvider); 
    }
}
