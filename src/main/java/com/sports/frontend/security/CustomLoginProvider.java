package com.sports.frontend.security;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//import javax.transaction.Transactional;



/*
 * Clase que implementa el manejo de seguridad por cada request
 * */


@Component
public class CustomLoginProvider implements AuthenticationProvider {
	
	private static Logger logger = LoggerFactory.getLogger(CustomLoginProvider.class);
    
	    
	@Override
	//@Transactional
    public Authentication authenticate(Authentication authentication)    throws AuthenticationException {
				
    	final List<GrantedAuthority> grantedAuths = new ArrayList<>();

		String username = authentication.getName();		
		String password = authentication.getCredentials().toString();

    	if (username.toUpperCase().equals("ADMIN"))	{
    		if (password.toUpperCase().equals("ADMIN"))	{
    	    	String roleStr = "ROLE_EDITOR";
    	    	grantedAuths.add(new SimpleGrantedAuthority(roleStr));
    	    	String usrName = "";
   	    		usrName = username;
    		    final UserDetails principal = new UserInfoLDap(username, password, "images/logo_user.png", grantedAuths);
    	    	final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
    	    	
    	    	return auth;    			
    		} else {
    			logger.error("Password incorrecto");    			
    		}
    	} else {
    		logger.error("Usuario incorrecto");
    	}
		return null;
	}
	
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    private class SingleAttributesMapper implements AttributesMapper<String> {

        @Override
        public String mapFromAttributes(Attributes attrs) throws NamingException {
         Attribute cn = attrs.get("cn");
         return cn.toString();
        }
       }
}