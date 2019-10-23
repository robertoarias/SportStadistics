package com.sports.frontend.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.jdbc.Work;

import oracle.jdbc.OracleTypes;


public class InsertCellularsToSendWork implements Work{
	private static Logger log = Logger.getLogger(InsertCellularsToSendWork.class);
			
	private Integer instance_id;
	private Integer campaign_id;
	private String 	partition_date;
	private String external_query;
	
	private Integer outStatus;
	private String outMessage;
	
	public InsertCellularsToSendWork(Integer instance_id, Integer campaign_id,String partition_date, String external_query) {
		super();
		this.instance_id = instance_id;
		this.campaign_id = campaign_id;
		this.partition_date = partition_date;
		this.external_query = external_query;		
		this.outStatus=0;
		this.outMessage="";
	}

	@Override
	public void execute(Connection cc) throws SQLException {
		
	/*	if (!preprocessExternalQuery())
		{
			outStatus=-1;
			return;
		}
		*/
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    Date parsed=new Date();
		try {
			parsed = format.parse(partition_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
		
		
		CallableStatement cstat = cc.prepareCall("{?= call PA_BROKER.LOAD_CELLULARS_TO_SEND_WEB(?,?,?,?)}");
		cstat.registerOutParameter(1, OracleTypes.NUMBER);
		cstat.setInt(2, instance_id);
		cstat.setInt(3, campaign_id);
		cstat.setDate(4, sqlDate);
		cstat.setString(5, external_query);
		
		cstat.execute();
		
			log.error("EXECUTES OK");
			outStatus = cstat.getInt(1);
			log.error("EXECUTES OK "+outStatus);
			if (outStatus!=0)
			{
				outMessage="Error ejecutando la consulta de usuarios. Revise la consulta. "+outStatus;
			}
		
	}


	private boolean preprocessExternalQuery()
	{
		String message=this.external_query;
		
		if (message == null || message.equals(""))
		{
			this.outMessage = "La consulta no puede estar vacia.";		
			return false;
		}
		
	    if (message.charAt(message.length()-1)==';')
		{
	    	message = message.substring(0, message.length()-1);	    	
		}
	    
	    //Si la consulta no empieza con select, no es valida
		if(!message.matches("select.*"))
		{
			this.outMessage = "La consulta solo puede comenzar con la palabra clave 'SELECT'";		
			return false;
		}
		
		if(!message.matches("select.*"))
		{
			this.outMessage = "La consulta solo puede comenzar con la palabra clave 'SELECT'";		
			return false;
		}
	    
	    String pattern = "(SELECT|select\\s+)(.*)(\\s+FROM|from.*)";
	    Pattern r = Pattern.compile(pattern);
	    
	    Matcher m = r.matcher(message);
	    if (m.find()) {
	    	String selected_field = m.group(2).trim();
	    	if (selected_field.contains(","))
	    	{
	    		this.outMessage = "Solo se puede seleccionar un campo";		
	    		return false;
	      	}else{
	      		if (!selected_field.equalsIgnoreCase("clu_bill_number"))
	      		{
	      			this.outMessage = "Solo se puede seleccionar el campo clu_bill_number";
	      			return false;
	      		}
	      	}
	    } else {
	    	this.outMessage = "La consulta no es de la forma select clu_bill_number from...";		
	    	return false;
	    }
		
		return true;
	}

	public Integer getOutStatus() {
		return outStatus;
	}


	public String getOutMessage() {
		return outMessage;
	}



	public Integer getInstance_id() {
		return instance_id;
	}




	public Integer getCampaign_id() {
		return campaign_id;
	}




	public String getExternal_query() {
		return external_query;
	}




	public void setInstance_id(Integer instance_id) {
		this.instance_id = instance_id;
	}




	public void setCampaign_id(Integer campaign_id) {
		this.campaign_id = campaign_id;
	}




	public void setExternal_query(String external_query) {
		this.external_query = external_query;
	}



}
