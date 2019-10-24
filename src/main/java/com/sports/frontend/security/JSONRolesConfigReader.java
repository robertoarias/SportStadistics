package com.sports.frontend.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//@Configuration
//@PropertySource(value={"classpath:roles.json"}, factory=JSONRolesConfigReader.JsonLoader.class)
//@PropertySource(value={"file:///${jboss.home}/modules/system/layers/base/com/campfe/conf/main/roles.json"}, factory=JSONRolesConfigReader.JsonLoader.class)
//@PropertySource(value={"file:c:\\roles.json"}, factory=JSONRolesConfigReader.JsonLoader.class)
public class JSONRolesConfigReader {
	
	//Devuelve el mapa de usuarios
	public static  Map<String,Object> getRolesConfig()
	{
		return JsonLoader.jsonMap;
	}
	
	
	 public static void writeToJson(Map<String,Object> users)
	 {
		 ObjectMapper mapper = new ObjectMapper();
		 try {
			 
			 URL resourceUrl = JsonLoader.url;				
			 if(resourceUrl!=null)
			 {

			 }else{
			 }	
			
			 VirtualFile vFile = VFS.getChild(resourceUrl.toURI());//org.jboss.vfs.VFS.getChild(aFilePath);
			 URI fileNameDecodedTmp = org.jboss.vfs.VFSUtils.getPhysicalURI(vFile);
			 
			 if(vFile!=null && fileNameDecodedTmp!=null)
			 {
					
			 }else{
			 }
			
			 File file = new File(fileNameDecodedTmp);
			 OutputStream output = new FileOutputStream(file);
			
			 mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			 
			 //Guarda el mapa modificado
			 mapper.writeValue(output, users);
			 
			 //Recarga el json despues de escribirlo
			 JsonLoader propertySource = new JsonLoader();
			 propertySource.createPropertySource(JsonLoader.name_, JsonLoader.resource_);
				 

			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	public static class JsonLoader implements PropertySourceFactory {
		
		private static URL url = null;
		private static Map<String, Object> jsonMap;
		
		private static String name_;
		private static EncodedResource resource_;
		
		@Override
        public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
			name_=name;
			resource_=resource;
			url = resource.getResource().getURL();
            jsonMap = new ObjectMapper().readValue(resource.getInputStream(), Map.class);
            return new MapPropertySource("json-source", jsonMap);
        }

		
        
    }
}
