<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Mes commandes</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-5">
		<h1 class="mb-4 text-center">Mes commandes</h1>

		<!-- Tableau des commandes -->
		<table class="table table-striped table-bordered">
			<thead class="table-dark">
				<tr>
					<th>ID</th>
					<th>Statut</th>
					<th>Date de commande</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${commandes}">
					<tr>
						<td>${c.idCommande}</td>
						<td>${c.statutCommande}</td>
						<td>${c.dateCommande}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- Si la liste est vide -->
		<c:if test="${empty commandes}">
			<div class="alert alert-info text-center">Aucune commande n'a été effectuée pour le moment.</div>
		</c:if>
	</div>

</body>
</html>