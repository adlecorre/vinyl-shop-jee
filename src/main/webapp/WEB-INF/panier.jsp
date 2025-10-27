<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Mon Panier</title>
<%@ include file="partial/_links.jsp"%>
</head>
<body class="bg-light">
	<%@ include file="partial/_menu.jsp"%>

	<div class="container mt-5">
		<h1 class="mb-4 text-center">Votre Panier</h1>

		<c:if test="${empty sessionScope.panier}">
			<div class="alert alert-info text-center">Votre panier est
				vide.</div>
		</c:if>

		<c:if test="${not empty sessionScope.panier}">
			<table class="table table-bordered align-middle">
				<thead class="table-dark">
					<tr>
						<th>Titre</th>
						<th>Quantité</th>
						<th>Prix unitaire</th>
						<th>Sous-total</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="total" value="0" />
					<c:forEach var="ligne" items="${sessionScope.panier.values()}">
						<tr>
							<td>${ligne.vinyle.titre}</td>
							<td>
								<form action="panier" method="post" style="display: inline;">
									<input type="hidden" name="id" value="${ligne.vinyle.id}" /> <input
										type="hidden" name="action" value="moins" />
									<button type="submit" class="btn btn-outline-danger btn-sm">-</button>
								</form> ${ligne.quantite}
								<form action="panier" method="post" style="display: inline;">
									<input type="hidden" name="id" value="${ligne.vinyle.id}" /> <input
										type="hidden" name="action" value="plus" />
									<button type="submit" class="btn btn-outline-success btn-sm">+</button>
								</form>
							</td>
							<td><fmt:formatNumber value="${ligne.vinyle.prixVinyle}"
									type="currency" /></td>
							<td><fmt:formatNumber value="${ligne.sousTotal}"
									type="currency" /></td>
							<c:set var="total" value="${total + ligne.sousTotal}" />
						</tr>
					</c:forEach>
				</tbody>
				<tfoot class="table-secondary">
					<tr>
						<th colspan="3" class="text-end">Total :</th>
						<th><fmt:formatNumber value="${total}" type="currency" /></th>
					</tr>
				</tfoot>

			</table>
			<c:if test="${not empty sessionScope.panier}">
				<form action="panier" method="post">
					<input type="hidden" name="action" value="valider" />
					<button type="submit" class="btn btn-success">Valider la
						commande</button>
				</form>

			</c:if>
		</c:if>

		<div class="text-center mt-4">
			<a href="catalogue" class="btn btn-primary">Retour au catalogue</a>
		</div>
	</div>

</body>
</html>
