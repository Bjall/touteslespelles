package com.udev.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.csv.*;
import com.udev.dao.ClientDAO;
import com.udev.dao.DetailFactureDAO;
import com.udev.dao.FactureDAO;
import com.udev.dao.ProduitDAO;
import com.udev.modele.Client;
import com.udev.modele.DetailFacture;
import com.udev.modele.Facture;
import com.udev.modele.Produit;

@Stateless
public class CsvFileReader {

	private static final String[] FILE_HEADER_MAPPING = { "id", "nom", "prenom", "adresse1", "adresse2", "codePostal",
			"ville" };

	private static final String CLIENT_NOM = "nom";
	private static final String CLIENT_PRENOM = "prenom";
	private static final String CLIENT_ADRESSE1 = "adresse1";
	private static final String CLIENT_ADRESSE2 = "adresse2";
	private static final String CLIENT_CP = "codePostal";
	private static final String CLIENT_VILLE = "ville";

	@EJB
	private ClientDAO clientDao;
	@EJB
	private ProduitDAO produitDao;
	@EJB
	private FactureDAO factureDao;
	@EJB
	private DetailFactureDAO detailDao;

	public void readCsvFile(InputStream inputStream) throws IOException, SQLException {

		Reader reader = new InputStreamReader(inputStream, "UTF-8");

		CSVParser csvFileParser = null;

		CSVFormat csvFileFormat = CSVFormat.EXCEL.withDelimiter(';').withHeader(FILE_HEADER_MAPPING);

		// Initialisation des objets
		Client client = null;
		Produit produit = null;
		Facture facture = null;
		DetailFacture detail = null;

		// Préparation de listes d'objets (qui seront alimentées par le contenu du CSV
		// lu)
		List<Client> clients = new ArrayList<Client>();
		List<Produit> produits = new ArrayList<Produit>();
		List<DetailFacture> details = new ArrayList<DetailFacture>();
		List<Facture> factures = new ArrayList<Facture>();

		// On initialise un objet CSVParser
		csvFileParser = new CSVParser(reader, csvFileFormat);

		try {
			// On lit les enregistrements du fichier CSV
			for (CSVRecord record : csvFileParser) {
				switch (record.get(0)) {
				// Si c'est une ligne Client
				case "CLI":
					client = new Client(record.get(CLIENT_NOM), record.get(CLIENT_PRENOM), record.get(CLIENT_ADRESSE1),
							record.get(CLIENT_ADRESSE2), record.get(CLIENT_CP), record.get(CLIENT_VILLE));
					clientDao.enregistrerClient(client);
					client.setIdClient(clientDao.getIdClient());
					clients.add(client);
					break;
				case "PDT":
					produit = new Produit(record.get(1), record.get(2), Float.parseFloat(record.get(4)));
					produitDao.enregistrerProduit(produit);
					produits.add(produit);
					// On créé une liste de détails
					detail = new DetailFacture(Integer.parseInt(record.get(3)), produit);
					details.add(detail);
					break;
				case "COM":
					// On instancie une nouvelle facture
					facture = new Facture(LocalDate.now(), record.get(1), client.getIdClient());
					factureDao.enregistrerFacture(facture);
					facture.setCode(factureDao.getIdFacture());
					factures.add(facture);
					// Pour chaque détail non attribué à une facture
					for (DetailFacture df : details) {
						if (df.getFacture() == null) {
							// Attribution de la facture courante
							df.setFacture(facture);
							detailDao.enregistrerDetail(df);
						}
					}
					break;
				default:
					System.out.println(
							"Le fichier en entrée est erroné. Au moins une ligne en en-tête contient autre chose que CLI, PDT ou COM");
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				csvFileParser.close();
			} catch (IOException e) {
				System.out.println("Erreur de fermeture de fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}
	}
}