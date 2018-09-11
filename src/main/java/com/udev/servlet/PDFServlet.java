package com.udev.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.udev.services.PDFGenerator;

@SuppressWarnings("serial")
@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {

	@EJB
	private PDFGenerator pdfGenerator;

	public static final String CHEMIN = "C:\\Users\\Benoit\\GeneratedPDF\\";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// On récupère l'id de la facture
		int idFacture = Integer.parseInt(request.getParameter("id"));

		// Définition du nom du fichier généré
		String nomFichier = "Facture" + idFacture + ".pdf";

		try {
			pdfGenerator.genererPdf(CHEMIN, nomFichier, idFacture);
		} catch (DocumentException | SQLException e) {
			e.printStackTrace();
		}

		// On instancie un nouveau fichier
		File fichier = new File(CHEMIN + nomFichier);

		// Construction du PDF et affichage dans le navigateur
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=" + nomFichier);
		response.setContentLength((int) fichier.length());
		request.setAttribute("fichierpdf", fichier);
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fichier));
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[2048];
			boolean end = false;
			while (!end) {
				int length = bis.read(buffer);
				if (length == -1) {
					end = true;
				} else {
					bos.write(buffer, 0, length);
				}
			}
			try {
				bis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			try {
				bos.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			bos.close();
		} catch (FileNotFoundException e) {
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
