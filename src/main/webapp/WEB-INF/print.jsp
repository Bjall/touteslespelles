<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Imprimer une facture</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/form.css"/>" />
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
		<div id="bloc-sstitre">
			<h2>Bienvenue sur notre service d'impression de factures</h2>
			<p>Pour imprimer une facture, veuillez cliquer sur le bouton
				correspondant.</p>
		</div>
		<div id="bloc-form">
			<table>
				<thead>
					<tr>
						<td class="col-1">N° Facture</td>
						<td class="col-2">Date</td>
						<td class="col-3">Commentaire</td>
						<td class="col-4">N° Client</td>
						<td class="col-5">Lien</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${factures}" var="facture">
						<tr class="lignes-data">
							<td class="col-1"><c:out value="${facture.code}"></c:out></td>
							<td class="col-2"><c:out value="${facture.date}"></c:out></td>
							<td class="col-3"><c:out value="${facture.commentaire}"></c:out></td>
							<td class="col-4"><c:out value="${facture.idclient}"></c:out></td>
							<td class="col-5"><a
								href="<%=request.getContextPath()+"/pdf?id="%><c:out value="${facture.code}"/>"><img src="arrow.png" height="25px" width="25px"></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="bloc-pied">
			<p>2018 - Touteslespelles.com tous droits réservés.</p>
		</div>
	</div>
</body>
</html>