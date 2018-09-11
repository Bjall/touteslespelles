package com.udev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.udev.modele.Client;

@Stateless
public class ClientDAO {

	@Resource(name = "touteslespelles")
	private DataSource dataSource;

	private int idClient;

	public int enregistrerClient(Client client) throws SQLException {
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "INSERT INTO client(nom, prenom, adresse1, adresse2, codepostal, ville) VALUES(?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, client.getNom());
				pstmt.setString(2, client.getPrenom());
				pstmt.setString(3, client.getAdresse1());
				pstmt.setString(4, client.getAdresse2());
				pstmt.setString(5, client.getCodePostal());
				pstmt.setString(6, client.getVille());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				rs.next();
				return idClient = rs.getInt(1);
			}
		}
	}

	public List<Client> extraireListeClients() throws SQLException {
		List<Client> clients = new ArrayList<Client>();
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "SELECT id_client, nom, prenom, adresse1, adresse2, codepostal, ville FROM client";
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("id_client");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String adresse1 = rs.getString("adresse1");
					String adresse2 = rs.getString("adresse2");
					String codePostal = rs.getString("codepostal");
					String ville = rs.getString("ville");
					Client client = new Client(nom, prenom, adresse1, adresse2, codePostal, ville);
					client.setIdClient(id);
					clients.add(client);
				}
			}
		}
		return clients;
	}

	public Client recupererClient(int numeroFacture) throws SQLException {
		Client client = null;
		try (Connection c = dataSource.getConnection()) {
			String sqlStatement = "SELECT c.id_client, c.nom, c.prenom, c.adresse1, c.adresse2, c.codepostal, c.ville FROM client c INNER JOIN facture f ON c.id_client = f.id_client WHERE f.id_client = " + numeroFacture;
			try (PreparedStatement pstmt = c.prepareStatement(sqlStatement)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("c.id_client");
					String nom = rs.getString("c.nom");
					String prenom = rs.getString("c.prenom");
					String adresse1 = rs.getString("c.adresse1");
					String adresse2 = rs.getString("c.adresse2");
					String codePostal = rs.getString("c.codepostal");
					String ville = rs.getString("c.ville");
					client = new Client(nom, prenom, adresse1, adresse2, codePostal, ville);
					client.setIdClient(id);
				}
			}
		}
		return client;
	}

	public int getIdClient() {
		return idClient;
	}
}