<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Catalogue des vinyles</title>
<%@ include file="partial/_links.jsp" %>
</head>

<body class="bg-light">
<%@ include file="partial/_menu.jsp" %>

<%@ page import="java.util.Map" %>
<%@ page import="GestionPanier.LignePanier" %>
<%
    Map<Integer, LignePanier> panier = (Map<Integer, LignePanier>) session.getAttribute("panier");
    int totalArticles = 0;
    double totalPrix = 0.0;

    if (panier != null) {
        for (LignePanier lp : panier.values()) {
            totalArticles += lp.getQuantite();
            totalPrix += lp.getSousTotal();
        }
    }
%>

<!-- Bloc Panier sous la navbar -->
<div class="container mt-3">
    <div class="alert alert-info d-flex justify-content-between align-items-center">
        <div>
            🛒 <strong>Panier</strong> : <%= totalArticles %> article(s)
        </div>
        <div>
            Total : <%= String.format("%.2f", totalPrix) %> €
        </div>
        <div>
            <a href="panier" class="btn btn-sm btn-primary">Voir le panier</a>
        </div>
    </div>
</div>

<div class="container mt-4">
    <h1 class="mb-4 text-center">Notre Catalogue</h1>

    <form action="catalogue" method="get" class="mb-4 text-center">
        <input type="text" name="search" value="${param.search}" placeholder="Rechercher un vinyle ou un artiste..." class="form-control d-inline w-50" />
        <button type="submit" class="btn btn-primary">Rechercher</button>
    </form>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty erreur}">
        <div class="alert alert-danger">${erreur}</div>
    </c:if>

    <!-- Tableau des vinyles -->
    <table class="table table-striped table-bordered align-middle">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Titre</th>
                <th>Artiste</th>
                <th>Prix</th>
                <th>Stock</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="v" items="${vinyles}">
                <tr>
                    <td>${v.id}</td>
                    <td>${v.titre}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty v.artiste}">
                                ${v.artiste.nom}
                            </c:when>
                            <c:otherwise>Inconnu</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <fmt:setLocale value="fr_FR" />
                        <fmt:formatNumber value="${v.prixVinyle}" type="currency" />
                    </td>
                    <td>${v.stock}</td>
                    <td>${v.description}</td>
                    <td class="text-center">
                        <c:set var="ligne" value="${sessionScope.panier[v.id]}" />
                        <c:choose>
                            <c:when test="${not empty ligne}">
                                <form action="panier" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${v.id}" />
                                    <input type="hidden" name="action" value="moins" />
                                    <button type="submit" class="btn btn-outline-danger btn-sm">-</button>
                                </form>

                                <span class="mx-2">${ligne.quantite}</span>

                                <form action="panier" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${v.id}" />
                                    <input type="hidden" name="action" value="plus" />
                                    <button type="submit" class="btn btn-outline-success btn-sm">+</button>
                                </form>
                            </c:when>

                            <c:otherwise>
                                <form action="panier" method="post">
                                    <input type="hidden" name="id" value="${v.id}" />
                                    <input type="hidden" name="action" value="ajouter" />
                                    <button type="submit" class="btn btn-success btn-sm">Ajouter</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty vinyles}">
        <div class="alert alert-info text-center">
            Aucun vinyle trouvé pour votre recherche.
        </div>
    </c:if>
</div>

</body>
</html>
