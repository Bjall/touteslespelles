package com.udev.modele;

public class DetailFacture {

	private int numero;
	private int quantite;
	private float prixUnitaireHT;
	private float montantHT;
	private float montantTTC;
	private Facture facture;
	private Produit produit;

	public DetailFacture(int quantite, Produit produit) {
		super();
		this.quantite = quantite;
		this.produit = produit;
		this.prixUnitaireHT = produit.getPrixUnitaireHT();
		this.montantHT = this.prixUnitaireHT * this.quantite;
		this.montantTTC = (float) (this.montantHT * 1.20);
	}
	
	public DetailFacture(int numero, int quantite, float prixUnitaireHT, float montantHT, float montantTTC) {
		super();
		this.numero = numero;
		this.quantite = quantite;
		this.prixUnitaireHT = prixUnitaireHT;
		this.montantHT = montantHT;
		this.montantTTC = montantTTC;
	}
	

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public float getPrixUnitaireHT() {
		return prixUnitaireHT;
	}

	public void setPrixUnitaireHT(float prixUnitaireHT) {
		this.prixUnitaireHT = prixUnitaireHT;
	}

	public float getMontantHT() {
		return montantHT;
	}

	public void setMontantHT(float montantHT) {
		this.montantHT = montantHT;
	}

	public float getMontantTTC() {
		return montantTTC;
	}

	public void setMontantTTC(float montantTTC) {
		this.montantTTC = montantTTC;
	}
	
	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
}