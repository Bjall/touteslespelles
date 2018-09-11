package com.udev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.udev.modele.DetailFacture;
import com.udev.modele.Produit;

@Stateless
public class DetailFactureDAO {

	@Resource(name = "touteslespelles")
	private DataSource dataSource;

	public DetailFacture enregistrerDetail(DetailFacture detail) throws SQLException {

		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "INSERT INTO detail_facture(quantite, prix_unitaire_ht, montant_ht, montant_ttc, code_facture, reference_produit) VALUES(?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				pstmt.setInt(1, detail.getQuantite());
				pstmt.setFloat(2, detail.getPrixUnitaireHT());
				pstmt.setFloat(3, detail.getMontantHT());
				pstmt.setFloat(4, detail.getMontantTTC());
				pstmt.setInt(5, detail.getFacture().getCode());
				pstmt.setString(6, detail.getProduit().getReference());
				pstmt.executeUpdate();
				return detail;
			}
		}
	}

//	public List<DetailFacture> recupererListeProduits(int numeroFacture) throws SQLException {
//		List<DetailFacture> liste = null;
//		try (Connection c = dataSource.getConnection()) {
//			String sqlStatement = "SELECT d.numero, d.quantite, d.prix_unitaire_ht, d.montant_ht, d.montant_ttc, d.reference_produit, p.designation, p.prix_unitaire_ht FROM detail_facture d INNER JOIN produit p ON d.reference_produit = p.reference_produit WHERE code_facture = "
//					+ numeroFacture;
//			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
//				ResultSet rs = pstmt.executeQuery();
//				while (rs.next()) {
//					int numeroLigne = rs.getInt("d.numero");
//					int quantite = rs.getInt("d.quantite");
//					float puht = rs.getFloat("d.prix_unitaire_ht");
//					float montantHT = rs.getFloat("d.montant_ht");
//					float montantTTC = rs.getFloat("d.montant_ttc");
//					String referenceProduit = rs.getString("d.reference_produit");
//					String designationProduit = rs.getString("p.designation");
//					float puhtProduit = rs.getFloat("p.prix_unitaire_ht");
//					Produit produit = new Produit(referenceProduit, designationProduit, puhtProduit);
//					DetailFacture detail = new DetailFacture(quantite, produit);
//					liste.add(detail);
//				}
//			}
//		}
//		return liste;
//	}
}