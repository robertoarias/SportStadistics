package com.sports.frontend.beans;


public class Pais {

	private Integer paisId;
	private String nombre;	
	
	public Pais(){
	}
	
	public Pais(Integer paisId, String nombre) {
		super();
		this.paisId = paisId;
		this.nombre = nombre;
	}

	public Integer getPaisId() {
		return paisId;
	}

	public void setPaisId(Integer paisId) {
		this.paisId = paisId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
