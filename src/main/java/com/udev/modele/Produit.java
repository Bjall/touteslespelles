package com.udev.modele;

public class Produit {

	private String reference;
	private String designation;
	private float prixUnitaireHT;

	public Produit(String reference, String designation, float prixUnitaireHT) {
		super();
		this.reference = reference;
		this.designation = designation;
		this.prixUnitaireHT = prixUnitaireHT;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public float getPrixUnitaireHT() {
		return prixUnitaireHT;
	}

	public void setPrixUnitaireHT(float prixUnitaireHT) {
		this.prixUnitaireHT = prixUnitaireHT;
	}

}
