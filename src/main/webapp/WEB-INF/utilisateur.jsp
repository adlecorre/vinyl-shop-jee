<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IUtilisateur</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>
	<div class="container mt-5">
		<h1 class="mb-4 text-center">Liste des utilisateurs</h1>

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
					<th>Prenom</th>
					
				</tr>
				
			</thead>
			<tbody>
				<c:forEach var="u" items="${utilisateurs}">
					<tr>
						<td>${u.id}</td>
						<td>${u.nom}</td>
						<td>${u.prenom}</td>

<td><c:url value="/utilisateur/edit" var="editLink">
						<c:param name="id" value="${ u.id }" />
					</c:url>
					<a href="${ editLink }"><i class="fa-solid fa-pen-to-square"></i></a>
					</li> </td>
					</tr>



				</c:forEach>
			</tbody>
		</table>

		<!-- Si la liste est vide -->
		<c:if test="${empty utilisateurs}">
			<div class="alert alert-info text-center">Aucun utilisateur
				enregistré pour le moment.</div>
		</c:if>
	</div>


</body>
</html>