package com.udev.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.udev.dao.ClientDAO;
import com.udev.dao.DetailFactureDAO;
import com.udev.dao.FactureDAO;
import com.udev.modele.Client;
import com.udev.modele.DetailFacture;
import com.udev.modele.Facture;
import com.udev.modele.Produit;

@Stateless
public class PDFGenerator {

	@EJB
	private ClientDAO clientDao;
	@EJB
	private FactureDAO factureDao;
	@EJB
	private DetailFactureDAO detailDao;

	public void genererPdf(String path, String nomFichier, int numeroFacture)
			throws DocumentException, IOException, SQLException {
		Document document = new Document();

		PdfWriter.getInstance(document, new FileOutputStream(path + nomFichier));

		// On initialise les objets à utiliser
		Client client = null;
		Facture facture = null;
//		Produit produit = null;
//		DetailFacture detail = null;

		document.open();

		// Recherche les information de la facture, du client et des détails
		facture = factureDao.recupererInfoFacture(numeroFacture);
		client = clientDao.recupererClient(numeroFacture);
//		List<DetailFacture> details = detailDao.recupererListeProduits(numeroFacture);

		// Appel de la méthode de création de l'en-tête de la facture
		creerEnteteFacture(document, client, facture);

		// Appel de la méthode de création du tableau
		creerTableauFacture(document, client, facture);

		document.close();
	}

	public Document creerEnteteFacture(Document document, Client client, Facture facture)
			throws SQLException, DocumentException {
		// Bandeau avec logo de l'enseigne
		Font fontLogo = new Font(FontFamily.HELVETICA, 32, Font.BOLD, BaseColor.BLACK);
		Chunk bandeau = new Chunk("            Touteslespelles.com", fontLogo);
		bandeau.setBackground(new BaseColor(255, 255, 0), 200, 20, 500, 40);
		document.add(bandeau);

		// Destinataire facture (client)
		Font fontClient = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
		Paragraph nomPrenomClient = new Paragraph(client.getNom() + " " + client.getPrenom(), fontClient);
		nomPrenomClient.setSpacingBefore(60);
		nomPrenomClient.setIndentationLeft(330);
		document.add(nomPrenomClient);
		Paragraph adresse1Client = new Paragraph(client.getAdresse1(), fontClient);
		adresse1Client.setIndentationLeft(330);
		document.add(adresse1Client);
		if (client.getAdresse2() != null) {
			Paragraph adresse2Client = new Paragraph(client.getAdresse2(), fontClient);
			adresse2Client.setIndentationLeft(330);
			document.add(adresse2Client);
		}
		Paragraph cpVilleClient = new Paragraph(String.valueOf(client.getCodePostal()) + " " + client.getVille(),
				fontClient);
		cpVilleClient.setIndentationLeft(330);
		document.add(cpVilleClient);

		// Date
		Paragraph dateParagraphe = new Paragraph("Bordeaux, le " + facture.getDate(), fontClient);
		dateParagraphe.setSpacingBefore(30);
		dateParagraphe.setIndentationLeft(330);
		document.add(dateParagraphe);

		// Numéro de facture
		Paragraph numFactureParagraphe = new Paragraph("Facture n° " + String.valueOf(facture.getCode()));
		numFactureParagraphe.setSpacingBefore(30);
		document.add(numFactureParagraphe);

		return document;
	}

	public Document creerTableauFacture(Document document, Client client, Facture facture) throws DocumentException {
		// Création du tableau
		PdfPTable tableau = new PdfPTable(4);
		tableau.setSpacingBefore(20);
		
		// Définition d'une cellule-type
		PdfPCell defaultcell = tableau.getDefaultCell();
		defaultcell.setFixedHeight(35f);
		defaultcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		defaultcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		// Ajout des cellules d'en-tête du tableau		
		Font fontTableau = new Font(FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
		tableau.addCell(new Paragraph("Référence", fontTableau));
		tableau.addCell(new Paragraph("Désignation", fontTableau));
		tableau.addCell(new Paragraph("Quantité", fontTableau));
		tableau.addCell(new Paragraph("P.U. HT", fontTableau));
		tableau.setWidthPercentage(100);
		float[] largeurColonnes = { 1.5f, 3f, 1f, 1.5f };
		tableau.setWidths(largeurColonnes);
		
		// Ajout des cellules d'en-tête du tableau		
		tableau.addCell(new Paragraph("PELLNG"));
		tableau.addCell(new Paragraph("Pelle à neige"));
		tableau.addCell(new Paragraph("2"));
		tableau.addCell(new Paragraph("39,80"));
		document.add(tableau);
		
		return document;
	}
}