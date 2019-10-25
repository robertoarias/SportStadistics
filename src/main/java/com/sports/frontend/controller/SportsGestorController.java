package com.sports.frontend.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sports.frontend.security.UserInfoLDap;



@Controller("/")
public class SportsGestorController {

	@RequestMapping("/")
	public String index_(Model model, Authentication authentication)
	{			
		UserInfoLDap userDetails = (UserInfoLDap) authentication.getPrincipal();
		
		String roleAuth="";
		String userAuth = "";
		String userDisplayName = userDetails.getUsername();
		String userPic = userDetails.getPathToPic();
		
		if (userDisplayName.indexOf("(") != -1)	{
			userAuth = userDisplayName.substring(0, userDisplayName.indexOf("(")-1);
		} else {
			userAuth = userDisplayName;
		}

		Collection<GrantedAuthority> authorities =(Collection<GrantedAuthority>)userDetails.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			roleAuth=authority.getAuthority();
		}
		
		model.addAttribute("tituloPantalla", "Torneos");
		model.addAttribute("userPic", userPic);
		
		return "index";	
	}	
	

	@RequestMapping(value="/login",method=RequestMethod.GET)
	  public String login() {
	    return "login";
	 }
	
}
