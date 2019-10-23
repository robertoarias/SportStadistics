package com.sports.frontend.beans;


public class Confederacion {

	private Integer confederacionId;
	private String nombre;	
	
	public Confederacion(){
	}
	
	public Confederacion(Integer confederacionId, String nombre) {
		super();
		this.confederacionId = confederacionId;
		this.nombre = nombre;
	}

	public Integer getConfederacionId() {
		return confederacionId;
	}

	public void setConfederacionId(Integer confederacionId) {
		this.confederacionId = confederacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
