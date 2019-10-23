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


public class GetValueConfigWork implements Work{
	private static Logger log = Logger.getLogger(GetValueConfigWork.class);
			
	/*OUT PARAMS*/
	private String outValue;
	
	/*IN PARAMS*/	
	private String invalue;	
	
	public GetValueConfigWork(String invalue) {
		super();
		this.invalue = invalue;		
		this.outValue="";
	}

	@Override
	public void execute(Connection cc) throws SQLException 
	{
		CallableStatement cstat = cc.prepareCall("{?= call PA_BROKER.ENCRYPT_DATA(?)}");
		cstat.registerOutParameter(1, OracleTypes.VARCHAR);
		cstat.setString(2, invalue);
		
		cstat.execute();
		
		log.error("EXECUTES OK");
		outValue = cstat.getString(1);
	}


	public String getOutValue() {
		return outValue;
	}

	public void setOutValue(String outValue) {
		this.outValue = outValue;
	}

	public String getInvalue() {
		return invalue;
	}

	public void setInvalue(String invalue) {
		this.invalue = invalue;
	}	


}
