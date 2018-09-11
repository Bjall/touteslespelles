<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Envoi de fichier</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/form.css"/>" />
</head>
<body>
	<div id="conteneur">
		<div id="bloc-titre">
			<img src="pelle-man.jpg" height="100px">
			<h1>Touteslespelles.com</h1>
			<img src="pelle-man.jpg" height="100px">
		</div>
		<ul id="bloc-nav">
			<li class="btn-menu"><a href="upload">Téléverser</a></li>
			<li class="btn-menu"><a href="print">Imprimer</a></li>
			<li class="btn-menu"><a href="contact">Contact</a></li>
		</ul>
		<div id="bloc-sstitre">
			<h2>Bienvenue sur notre service d'envoi de données</h2>
			<p>Pour sauvegarder les données de vos ventes en base, veuillez utiliser le formulaire ci-dessous afin de téléverser votre fichier au format CSV.</p>
		</div>
		<div id="bloc-form">
			<form action="<c:url value="/upload" />" method="post"
				enctype="multipart/form-data">
				<fieldset>
					<legend>Envoi de fichier</legend>
					<label for="fichier">Emplacement du fichier <span class="requis">*</span></label> 
					<input type="file" id="fichier" name="fichier" value="<c:out value="${fichier.nom}"/>" /> 
					<span class="erreur">${form.erreurs['fichier']}</span><br />
					<input type="submit" value="Envoyer" class="sansLabel" /> <br />
					<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
				</fieldset>
			</form>
		</div>
		<div id="bloc-pied">
			<p>2018 - Touteslespelles.com tous droits réservés.</p>
		</div>
	</div>
</body>
</html>