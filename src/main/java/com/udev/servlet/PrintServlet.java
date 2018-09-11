package com.udev.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.udev.dao.FactureDAO;
import com.udev.modele.Facture;

@SuppressWarnings("serial")
@WebServlet("/print")
public class PrintServlet extends HttpServlet {
	
	@EJB
	private FactureDAO factureDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Facture> liste = null;
		try {
			liste = factureDao.extraireListeFactures();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("factures", liste);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/print.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}