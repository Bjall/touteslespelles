package com.udev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.udev.modele.Produit;

@Stateless
public class ProduitDAO {
	
	@Resource(name = "touteslespelles")
	private DataSource dataSource;

	public Produit enregistrerProduit(Produit produit) throws SQLException {

		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "INSERT IGNORE INTO produit(reference_produit, designation, prix_unitaire_ht) VALUES(?, ?, ?)";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				pstmt.setString(1, produit.getReference());
				pstmt.setString(2, produit.getDesignation());
				pstmt.setFloat(3, produit.getPrixUnitaireHT());
				pstmt.executeUpdate();
				return produit;
			}
		}
	}
	
	public Produit recupererInfosProduit(int numeroFacture, String referenceProduit) throws SQLException {
		Produit produit = null;
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "SELECT p.reference_produit, p.designation, p.prix_unitaire_ht FROM produit p INNER JOIN detail_facture d ON p.reference_produit = d.reference_produit INNER JOIN facture f ON d.code_facture = f.code_facture WHERE d.code_facture = " + numeroFacture + "AND d.reference_produit = " + referenceProduit;
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String reference_produit = rs.getString("p.reference_produit");
					String designation = rs.getString("p.designation");
					Float prix_unitaire_ht = rs.getFloat("p.prix_unitaire_ht");
					produit = new Produit(reference_produit, designation, prix_unitaire_ht);
				}
			}
		}
		return produit;
	}
}
