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


public class GetNumberOfRowsWork implements Work{
	private static Logger log = Logger.getLogger(GetNumberOfRowsWork.class);
			
	private String external_query;
	
	private Integer outStatus;
	private String outMessage;
	
	public GetNumberOfRowsWork(String external_query) {
		super();
		this.external_query = external_query;		
		this.outStatus=0;
		this.outMessage="";
	}

	@Override
	public void execute(Connection cc) throws SQLException 
	{
		CallableStatement cstat = cc.prepareCall("{?= call PA_BROKER.GET_EXTERNAL_CELLULARS_NUMBER(?)}");
		cstat.registerOutParameter(1, OracleTypes.NUMBER);
		cstat.setString(2, external_query);
		
		cstat.execute();
		
		log.error("EXECUTES OK");
		outStatus = cstat.getInt(1);
		log.error("EXECUTES OK "+outStatus);
		if (outStatus!=0)
		{
			outMessage="Error ejecutando la consulta de usuarios. Revise la consulta. "+outStatus;
		}
	}


	

	public Integer getOutStatus() {
		return outStatus;
	}


	public String getOutMessage() {
		return outMessage;
	}

	
	public String getExternal_query() {
		return external_query;
	}


	public void setExternal_query(String external_query) {
		this.external_query = external_query;
	}



}
