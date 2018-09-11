package com.udev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.udev.modele.Facture;

@Stateless
public class FactureDAO {

	@Resource(name = "touteslespelles")
	private DataSource dataSource;

	private int idFacture;

	public int enregistrerFacture(Facture facture) throws SQLException {

		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "INSERT INTO facture(date_facture, commentaire, id_client) VALUES(?, ?, ?)";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, facture.getDate().toString());
				pstmt.setString(2, facture.getCommentaire());
				pstmt.setInt(3, facture.getIdclient());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				rs.next();
				return idFacture = rs.getInt(1);
			}
		}
	}

	public List<Facture> extraireListeFactures() throws SQLException {
		List<Facture> factures = new ArrayList<Facture>();
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "SELECT * FROM facture";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					int codeFacture = rs.getInt("code_facture");
					LocalDate date = rs.getDate("date_facture").toLocalDate();
					String commentaire = rs.getString("commentaire");
					int idClient = rs.getInt("id_client");
					Facture facture = new Facture(date, commentaire, idClient);
					facture.setCode(codeFacture);
					factures.add(facture);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return factures;
	}

	public Facture recupererInfoFacture(int numeroFacture) throws SQLException {
		Facture facture = null;
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "SELECT * FROM facture WHERE code_facture = " + numeroFacture;
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					int codeFacture = rs.getInt("code_facture");
					LocalDate date = rs.getDate("date_facture").toLocalDate();
					String commentaire = rs.getString("commentaire");
					int idClient = rs.getInt("id_client");
					facture = new Facture(date, commentaire, idClient);
					facture.setCode(codeFacture);
				}
			}
		}
		return facture;
	}

	public int getIdFacture() {
		return idFacture;
	}
}