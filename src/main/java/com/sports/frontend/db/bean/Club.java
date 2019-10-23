package com.sports.frontend.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Clubes")
public class Club {

	@Id
	@Column(name="club_id")
	int club_id;
	
	@Column(name="nombre_club")	
	String nombre_club;

	public Club(){}
	
	public int getClub_id() {
		return club_id;
	}

	public void setClub_id(int club_id) {
		this.club_id = club_id;
	}

	public String getNombre_club() {
		return nombre_club;
	}

	public void setNombre_club(String nombre_club) {
		this.nombre_club = nombre_club;
	}
	
}
