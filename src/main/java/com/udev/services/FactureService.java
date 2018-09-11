package com.udev.services;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.udev.dao.FactureDAO;
import com.udev.modele.Facture;

@Stateless
public class FactureService {
	
	@EJB
	private FactureDAO factureDao;
	
	public List<Facture> creerListeFactures() throws SQLException {
		List<Facture> liste = factureDao.extraireListeFactures();
		return liste;
	}
	
}