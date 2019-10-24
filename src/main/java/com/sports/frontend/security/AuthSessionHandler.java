package com.sports.frontend.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthSessionHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	Logger logger = Logger.getLogger(AuthSessionHandler.class);
	
	private Integer timeout=null;
	
	
	public AuthSessionHandler(Integer timeout)
	{
		this.timeout=timeout*60;
	}
	
	private final static Integer DEFAULT_TIMEOUT = 15*60;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
		request.getSession().setMaxInactiveInterval(timeout!=null?timeout:DEFAULT_TIMEOUT);
		
				
		super.onAuthenticationSuccess(request, response, authentication);		 
	}

}
