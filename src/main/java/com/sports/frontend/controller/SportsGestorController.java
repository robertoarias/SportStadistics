package com.sports.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller("/")
public class SportsGestorController {

	@RequestMapping("/")
	public String index_()
	{			
		return "index";	
	}	
	

	@RequestMapping(value="/login",method=RequestMethod.GET)
	  public String login() {
	    return "login";
	 }
	
}
