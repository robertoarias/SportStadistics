package com.sports.frontend.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sports.frontend.db.bean.Club;
import com.sports.frontend.db.repository.ClubesRepository;

@Component
public class WebServiceMethods {
	
	private static Logger logger= Logger.getLogger(WebServiceMethods.class);
		
	//Repositorios de BD
    private ClubesRepository clubesRepository;

	//Injeccion de repositorios de BD
	@Autowired
	public void setClubesRepository(ClubesRepository clubesRepository) 
	{
		this.clubesRepository = clubesRepository;
	}

    /* Cuando se ponga el archivo properties en JBOSS productivo descomentar estas lineas. Por ahora es una clase comun que no hace nada */
	//@Autowired
	//private RASportFrontEndConfig config;
	
	@Autowired
	private EntityManager em;
	
	
	public List<List<String>> getListaClubes()
	{
		logger.info("getListaClubes WS method BEGIN");
		List<List<String>> listaClubes = new ArrayList<List<String>>();
		List<String> innerItem = null;

		String queryStr = "select cl.club_id, cl.escudo, cl.nombre_club, cd.nombre_ciudad, pr.nombre_provincia, pa.nombre_pais ";
		queryStr = queryStr + "from Clubes cl, Provincias pr, Ciudades cd, Paises pa ";
		queryStr = queryStr + "where cl.ciudad_id = cd.ciudad_id and cl.provincia_id = pr.provincia_id and cl.pais_id = pa.pais_id and cl.pais_id = 1";
		Query query = em.createNativeQuery(queryStr);
    	List<Object[]> listClubesResult = query.getResultList();
    	
    	for (Object [] objs:listClubesResult)
    	{
    		int index=0;
    		innerItem = new ArrayList<>();
    		for (Object item:objs)
    		{    		
    			switch (index)
    			{
    				case 0: //ClubId
    					String idstr = String.valueOf(item);
    					innerItem.add(String.valueOf(item));
    				break;
    				case 1://Club Escudo
    					innerItem.add(encoder((String)item));
    				break;    				
    				case 2://Club Nombre
    					innerItem.add((String)item);
    				break;    				
    				case 3://Ciudad Nombre
    					innerItem.add((String)item);
    				break;    				
    				case 4://Provincia Nombre
    					innerItem.add((String)item);
    				break;    				
    				case 5://Pais Nombre
    					innerItem.add((String)item);
    				break;    				
    			}
    			index++;
    		}

    		listaClubes.add(innerItem);
    	}
    	
		logger.info("getListaClubes WS method END");
		return  listaClubes;
	}
	
	public List<List<String>> getListaPaises(Integer idConfederacion)
    {
		logger.info("getListaPaises WS method");
		List<List<String>> listaPaises = new ArrayList<List<String>>();
		List<String> innerItem = null;
		
		String queryStr = "select ps.* from PAISES ps ";
		if (idConfederacion > 0) {
			queryStr += "where ps.confederacion_id = " + idConfederacion;
		}
		Query query = em.createNativeQuery(queryStr);
		
    	List<Object[]> listPaisesResult = query.getResultList();
    	
    	for (Object [] objs:listPaisesResult)
    	{
    		int index=0;
    		innerItem = new ArrayList<>();
    		for (Object item:objs)
    		{    		
    			switch (index)
    			{
    				case 0: //PaisId
    					String idstr = String.valueOf(item);
    					innerItem.add(String.valueOf(item));
    				break;
    				case 1://PaisNombre
    					String nombrePais = (String)item;
    					innerItem.add((String)item);
    				break;    				
    			}
    			index++;
    		}

    		listaPaises.add(innerItem);
    	}
    	
		return  listaPaises;
    }


	public static String encoder(String imagePath) {
		System.out.println("RUTA: " + imagePath);
		String base64Image = "";
		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
	
	private static Integer objectToInteger(Object o)
	{
		return Integer.valueOf(String.valueOf(o));
	}
	
}
