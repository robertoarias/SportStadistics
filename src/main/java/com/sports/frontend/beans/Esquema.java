package com.sports.frontend.beans;


public class Esquema {
	private Integer instIdEsquema;
	private String nombreEsquema;	
	private String descripcionEsquema;	
	
	
	public Esquema(Integer instIdEsquema, String nombreEsquema, String descripcionEsquema) {
		super();
		this.instIdEsquema = instIdEsquema;
		this.nombreEsquema = nombreEsquema;
		this.descripcionEsquema = descripcionEsquema;
	}
	
	public Integer getInstIdEsquema() {
		return instIdEsquema;
	}

	public void setInstIdEsquema(Integer instIdEsquema) {
		this.instIdEsquema = instIdEsquema;
	}

	public String getNombreEsquema() {
		return nombreEsquema;
	}

	public void setNombreEsquema(String nombreEsquema) {
		this.nombreEsquema = nombreEsquema;
	}

	public String getDescripcionEsquema() {
		return descripcionEsquema;
	}

	public void setDescripcionEsquema(String descripcionEsquema) {
		this.descripcionEsquema = descripcionEsquema;
	}
	
}
