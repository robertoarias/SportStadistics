package com.sports.frontend;


/* Cuando se ponga el archivo properties en JBOSS productivo descomentar estas lineas. Por ahora es una clase comun que no hace nada */

//@Configuration
//@PropertySource("file:///${jboss.home}/modules/system/layers/base/com/campfe/conf/main/settings.properties")
//@PropertySource("file:c:/tmp/app.properties")
//@ConfigurationProperties(prefix = "config")
public class SportsGestorConfig {
	
	private static final String SESSION_TIMEOUT_MIN="15";
	
	//@Value("${session_timeout_minutes}")
	private String session_timeout_minutes;
	
	public String getSession_timeout_minutes() {
		//return session_timeout_minutes;
		return SESSION_TIMEOUT_MIN; //por ahora se devuelve la constante....
	}

}
