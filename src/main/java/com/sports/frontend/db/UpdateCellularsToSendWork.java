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


public class UpdateCellularsToSendWork implements Work{
	private static Logger log = Logger.getLogger(UpdateCellularsToSendWork.class);
	
	

	private Integer old_campaign_id;
	private Integer new_campaign_id;
	private Integer instance_id;	
	private String 	partition_date;
	
	private Integer outStatus;
	private String outMessage;
	
	public UpdateCellularsToSendWork(Integer old_campaign_id, Integer new_campaign_id, Integer instance_id,
			String partition_date) {
		super();
		this.old_campaign_id = old_campaign_id;
		this.new_campaign_id = new_campaign_id;
		this.instance_id = instance_id;
		this.partition_date = partition_date;
		this.outStatus = 0;
		this.outMessage = "";
	}

	@Override
	public void execute(Connection cc) throws SQLException {
		
		java.sql.Date sqlDate =null;
		if (partition_date!=null)
		{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date parsed=new Date();
			try {
			
				parsed = format.parse(partition_date);
			} catch (ParseException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(parsed.getTime());
		}
		
		CallableStatement cstat = cc.prepareCall("{?= call PA_BROKER.UPDATE_CELLULARS_TO_SEND_WEB(?,?,?,?)}");
		cstat.registerOutParameter(1, OracleTypes.NUMBER);
		cstat.setInt(2, old_campaign_id);
		cstat.setInt(3, new_campaign_id);
		if (instance_id==null)
		{
			cstat.setNull(4, java.sql.Types.INTEGER);
		}else{
			cstat.setInt(4, instance_id);
		}
		if (partition_date==null)
		{
			cstat.setNull(5, java.sql.Types.DATE);
		}else{
			cstat.setDate(5, sqlDate);
		}
		


		
		cstat.execute();
		
			log.error("EXECUTES OK");
			outStatus = cstat.getInt(1);
			log.error("EXECUTES OK "+outStatus);
			if (outStatus<0)
			{
				outMessage="Error ejecutando el procedimiento. Revise la consulta. "+outStatus;
			}
		
	}

	public Integer getOld_campaign_id() {
		return old_campaign_id;
	}

	public void setOld_campaign_id(Integer old_campaign_id) {
		this.old_campaign_id = old_campaign_id;
	}

	public Integer getNew_campaign_id() {
		return new_campaign_id;
	}

	public void setNew_campaign_id(Integer new_campaign_id) {
		this.new_campaign_id = new_campaign_id;
	}

	public Integer getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(Integer instance_id) {
		this.instance_id = instance_id;
	}

	public String getPartition_date() {
		return partition_date;
	}

	public void setPartition_date(String partition_date) {
		this.partition_date = partition_date;
	}

	public Integer getOutStatus() {
		return outStatus;
	}

	public String getOutMessage() {
		return outMessage;
	}








}
