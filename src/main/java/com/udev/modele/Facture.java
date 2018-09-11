package com.udev.modele;

import java.time.LocalDate;

public class Facture {

	private int code;
	private LocalDate date;
	private String commentaire;
	private int idclient;

	public Facture(LocalDate date, String commentaire, int idclient) {
		super();
		this.date = date;
		this.commentaire = commentaire;
		this.setIdclient(idclient);
	}
	
	public Facture() {
		super();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

}
