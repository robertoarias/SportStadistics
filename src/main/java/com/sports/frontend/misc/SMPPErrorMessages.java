package com.sports.frontend.misc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SMPPErrorMessages {
	
	public static final Map<Integer,String> smppErrors;
	static
	{
		Map<Integer,String> smpp=new HashMap<Integer,String>();
		smpp.put(-20,"Campaña interrumpida (Quiet period o fin de campaña).");
		smpp.put(-10,"Sin respuesta del SMSC.");
		smpp.put(0,"OK.");
		smpp.put(8,"Problemas en el host SMPP.");
		smpp.put(98,"Periodo de validez/expiracion seleccionado es invalido.");
		smpp.put(100,"ESME Receiver Temporary App Error Code");
		smpp.put(102,"El proveedor rechazo el mensaje por la aplicacion de una politica o filtro.");
		smppErrors=Collections.unmodifiableMap(smpp);
	}
}
