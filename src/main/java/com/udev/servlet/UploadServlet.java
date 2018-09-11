package com.udev.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.udev.services.CsvFileReader;

@SuppressWarnings("serial")
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	
	@EJB
	private CsvFileReader csvFileReader;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/upload.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part part = request.getPart("fichier");
		InputStream inputStream = part.getInputStream();
		try {
			csvFileReader.readCsvFile(inputStream);
		} catch (SQLException e) {
			System.out.println("Erreur" + e);;
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/upload.jsp").forward(request, response);
	}
} 