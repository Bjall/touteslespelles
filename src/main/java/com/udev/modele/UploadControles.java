package com.udev.modele;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

//import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
//import javax.sql.DataSource;

import com.udev.services.CsvFileReader;

@Stateless
public final class UploadControles {

//	@Resource(name = "touteslespelles")
//	private DataSource dataSource;

	@EJB
	private CsvFileReader csvFileReader;
	
	private static final String CHAMP_FICHIER = "fichier";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Fichier enregistrerFichier(HttpServletRequest request, String chemin) {
		/* Initialisation du bean représentant un fichier */
		Fichier fichier = new Fichier();

		/*
		 * Récupération du contenu du champ fichier du formulaire. Il faut ici utiliser
		 * la méthode getPart(), comme nous l'avions fait dans notre servlet auparavant.
		 */
		String nomFichier = null;
		InputStream contenuFichier = null;
		try {
			Part part = request.getPart(CHAMP_FICHIER);
			/*
			 * Il faut déterminer s'il s'agit bien d'un champ de type fichier : on délègue
			 * cette opération à la méthode utilitaire getNomFichier().
			 */
			nomFichier = getNomFichier(part);

			/*
			 * Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ de type
			 * fichier (input type="file").
			 */
			if (nomFichier != null && !nomFichier.isEmpty()) {
				/*
				 * Antibug pour Internet Explorer, qui transmet pour une raison mystique le
				 * chemin du fichier local à la machine du client...
				 * 
				 * Ex : C:/dossier/sous-dossier/fichier.ext
				 * 
				 * On doit donc faire en sorte de ne sélectionner que le nom et l'extension du
				 * fichier, et de se débarrasser du superflu.
				 */
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
						.substring(nomFichier.lastIndexOf('\\') + 1);

				/* Récupération du contenu du fichier */
				contenuFichier = part.getInputStream();

			}
		} catch (IllegalStateException e) {
			/*
			 * Exception retournée si la taille des données dépasse les limites définies
			 * dans la section <multipart-config> de la déclaration de notre servlet
			 * d'upload dans le fichier web.xml
			 */
			e.printStackTrace();
			setErreur(CHAMP_FICHIER, "Les données envoyées sont trop volumineuses.");
		} catch (IOException e) {
			/*
			 * Exception retournée si une erreur au niveau des répertoires de stockage
			 * survient (répertoire inexistant, droits d'accès insuffisants, etc.)
			 */
			e.printStackTrace();
			setErreur(CHAMP_FICHIER, "Erreur de configuration du serveur.");
		} catch (ServletException e) {
			/*
			 * Exception retournée si la requête n'est pas de type multipart/form-data. Cela
			 * ne peut arriver que si l'utilisateur essaie de contacter la servlet d'upload
			 * par un formulaire différent de celui qu'on lui propose... pirate ! :|
			 */
			e.printStackTrace();
			setErreur(CHAMP_FICHIER,
					"Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.");
		}

		/* Si aucune erreur n'est survenue jusqu'à présent */
		if (erreurs.isEmpty()) {
			/* Validation du champ fichier. */
			try {
				validationFichier(nomFichier, contenuFichier);
			} catch (Exception e) {
				setErreur(CHAMP_FICHIER, e.getMessage());
			}
			fichier.setNom(nomFichier);
		}

		/* Si aucune erreur n'est survenue jusqu'à présent */
		if (erreurs.isEmpty()) {
			try {
				Part part = request.getPart("fichier");
				InputStream inputStream = part.getInputStream();
				csvFileReader.readCsvFile(inputStream);
			} catch (Exception e) {
				setErreur(CHAMP_FICHIER, "Erreur lors de l'écriture du fichier sur le disque.");
			}
		}

		/* Initialisation du résultat global de la validation. */
		if (erreurs.isEmpty()) {
			resultat = "Succès de l'envoi du fichier.";
		} else {
			resultat = "Échec de l'envoi du fichier.";
		}

		return fichier;
	}

	// Valide le fichier envoyé.
	private void validationFichier(String nomFichier, InputStream contenuFichier) throws Exception {
		if (nomFichier == null || contenuFichier == null) {
			throw new Exception("Merci de sélectionner un fichier à envoyer.");
		}
	}

	// Ajoute un message correspondant au champ spécifié à la map des erreurs.
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/*
	 * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
	 * "content-disposition", et de vérifier si le paramètre "filename" y est
	 * présent. Si oui, alors le champ traité est de type File et la méthode
	 * retourne son nom, sinon il s'agit d'un champ de formulaire classique et la
	 * méthode retourne null.
	 */
	private static String getNomFichier(Part part) {
		/* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			/* Recherche de l'éventuelle présence du paramètre "filename". */
			if (contentDisposition.trim().startsWith("filename")) {
				/*
				 * Si "filename" est présent, alors renvoi de sa valeur, c'est-à-dire du nom de
				 * fichier sans guillemets.
				 */
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		/* Et pour terminer, si rien n'a été trouvé... */
		return null;
	}
	
}