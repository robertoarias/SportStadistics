package com.sports.frontend.controller;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sports.frontend.beans.Confederacion;
import com.sports.frontend.db.bean.ClubBean;
import com.sports.frontend.security.UserInfoLDap;
import com.sports.frontend.ws.WebServiceMethods;



@Controller("/")
public class SportsGestorController {

	private static Logger logger = Logger.getLogger(SportsGestorController.class);
	private static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
	
	@Autowired
	private WebServiceMethods webServiceMethods;

	@Autowired
	private EntityManager em;
	
	private ClubBean club_bean_old = new ClubBean();
	
	private List<List<String>> paisesList=null;
	private List<List<String>> clubesList=null;
	
	@RequestMapping("/")
	public String index_(Model model, Authentication authentication)
	{			
		/*
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
		
		logger.trace("Request: index");
		//clubesList = webServiceMethods.getListaClubes();
		
		model.addAttribute("witherror", false);			
		model.addAttribute("statusOk", false);			
		model.addAttribute("club_bean", new ClubBean());
		//model.addAttribute("clubesList", clubesList);
		model.addAttribute("tituloPantalla", "Listado Principal");
		model.addAttribute("userPic", userPic);
		logger.trace("Mostrando la pantalla principal");
		*/
		
		return "index";		
	}	
	

	@RequestMapping("/selectAsoc")
	public String selectAsociacion(Model model, Authentication authentication)
	{			
		UserInfoLDap userDetails = (UserInfoLDap) authentication.getPrincipal();		
		String roleAuth="";
		String userAuth = "";
		String userDisplayName = userDetails.getUsername();
		String userPic = userDetails.getPathToPic();
		model.addAttribute("confederacion_bean", new Confederacion());
		model.addAttribute("isConfederaciones", true);
		model.addAttribute("tituloPantalla", "Confederaciones");
		model.addAttribute("userPic", userPic);
		return "selectasociacion";		
	}	
	
	
	@RequestMapping("/showPaisesXConfederaciones")
	public String clubes(@ModelAttribute Confederacion confederacion, Model model, Authentication authentication)
	{			
		logger.info("Request: showPaisesXConfederaciones-GET");
		UserInfoLDap userDetails = (UserInfoLDap) authentication.getPrincipal();		
		String roleAuth="";
		String userAuth = "";
		String userDisplayName = userDetails.getUsername();
		String userPic = userDetails.getPathToPic();
		
		Integer idConfederacionActual = confederacion.getConfederacionId()==null?0:confederacion.getConfederacionId();
		paisesList = webServiceMethods.getListaPaises(idConfederacionActual);
		
		model.addAttribute("confederacion_bean", new Confederacion());
		model.addAttribute("paisesList", paisesList);
		model.addAttribute("isConfederaciones", false);
		model.addAttribute("tituloPantalla", "Paises");
		model.addAttribute("userPic", userPic);
		return "selectasociacion";		
	}	
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	  public String login() {
		logger.trace("Request: Login-GET");
	    return "login";
	 }
	
	
	//ALTA O MODIFICACION DE UN CLUB
	@RequestMapping("/editclub")
	public String modificarClub(@ModelAttribute ClubBean clubBean, Model model, Authentication authentication)
	{
		logger.trace("Request: edit club-GET");

		UserInfoLDap userDetails = (UserInfoLDap) authentication.getPrincipal();
		String userPic = userDetails.getPathToPic();
		
		if (clubBean == null) {
			logger.debug("Se inserta un nuevo club");
			model.addAttribute("modifOk", false);
			//model.addAttribute("isSubCamp", false);					
			model.addAttribute("witherror", true);
			model.addAttribute("statusOk", false);
			model.addAttribute("statusMessage", "Error - el objeto del Club es nulo.");
			model.addAttribute("club_bean", new ClubBean());
			model.addAttribute("userPic", userPic);
			model.addAttribute("tituloPantalla", "Listado de Clubes");
			return "index";

		} else {
			
			if ((clubBean.getClub_id() == null) || (clubBean.getClub_id() == 0)) {
				model.addAttribute("modifOk", false);
				//model.addAttribute("isSubCamp", false);					
				model.addAttribute("witherror", false);
				model.addAttribute("statusOk", false);
				//model.addAttribute("numberCheck",true);
				model.addAttribute("userPic", userPic);
				model.addAttribute("tituloPantalla", "Creación de nuevo club");
			} else {
				model.addAttribute("modifOk", true);
				model.addAttribute("witherror", false);
				model.addAttribute("statusOk", false);
				model.addAttribute("userPic", userPic);
				model.addAttribute("tituloPantalla", "Modificación de club");
				
				//guardo los datos del bean original con el que se entra a la pantalla para modificar
				club_bean_old.setClub_id(clubBean.getClub_id());
				club_bean_old.setNombre_club(clubBean.getNombre_club());
			}
		}

		model.addAttribute("club_bean", clubBean);
		logger.trace("Mostrando la pantalla de edicion");
		return "editclub";
	}
	
	
	
	
	private static Long objectToLong(Object o)
	{
		return Long.valueOf(String.valueOf(o));
	}
	
	private static Integer objectToInteger(Object o)
	{
		return Integer.valueOf(String.valueOf(o));
	}
	
	
}
