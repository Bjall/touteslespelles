<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Imprimer une facture</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/form.css"/>" />
</head>
<body>
	<div id="conteneur">
		<div id="bloc-titre">
			<h1>Touteslespelles.com</h1>
		</div>
		<ul id="bloc-nav">
			<li class="btn-menu"><a href="upload">Téléverser</a></li>
			<li class="btn-menu"><a href="print">Imprimer</a></li>
			<li class="btn-menu"><a href="contact">Contact</a></li>
		</ul>
		<div id="contact">
			<p><a href="mailto:bjallet@gmail.com">bjallet@gmail.com</a></p>
		</div>
		<div id="bloc-form">
		</div>
		<div id="bloc-pied">
			<p>2018 - Touteslespelles.com tous droits réservés.</p>
		</div>
	</div>
</body>
</html>