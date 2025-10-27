<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Accueil</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-5">
		<h1 class="mb-4 text-center">Liste des vinyles</h1>

		<!-- Message de succès ou d’erreur -->
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty erreur}">
			<div class="alert alert-danger">${erreur}</div>
		</c:if>

		<!-- Tableau des utilisateurs -->
		<table class="table table-striped table-bordered">
			<thead class="table-dark">
				<tr>
					<th>ID</th>
					<th>Nom</th>
					<th>Stock</th>
					<th>Prix Vinyle</th>
					<th>ID Artiste</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="u" items="${vinyles}">
					<tr>
						<td>${u.id}</td>
						<td>${u.titre}</td>
						<td>${u.stock}</td>
						<td>${u.prixVinyle}</td>
						<td>${u.artiste}</td>
						<td>${u.description}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- Si la liste est vide -->
		<c:if test="${empty vinyles}">
			<div class="alert alert-info text-center">Aucun vinyle
				enregistré pour le moment.</div>
		</c:if>
	</div>

</body>
</html>