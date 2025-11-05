<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UTILISATEUR A MODIFER</title>
</head>
<body>

<h1> MODIFIER</h1>


<%@ include file="partial/_menu.jsp"%>
	<h1 class="container text-primary text-center my-5">Page de
		modification</h1>
	<p class="container text-primary text-center my-5">
		Bonjour
		<c:out
			value="${ sessionScope.utilisateur.prenom } ${ sessionScope.utilisateur.nom }" />,
		<c:url value="/deconnexion" var="lien">
		</c:url>
		<a href="${lien}">déconnexion</a>
	</p>
	<form action="${pageContext.request.contextPath}/utilisateur/edit"
		method="post">
		<input type=hidden name=id value="${ utilisateur.id }">
		<div>
			<label for="nom">Nom :</label> <input id=nom name=nom type=text
				value="${ utilisateur.nom }">
		</div>
		<div>
			<label for="prenom">Prénom :</label> <input id=prenom name=prenom
				value="${ utilisateur.prenom }" type=text>
		</div>
		
		<button>Enregistrer</button>
	</form>
</body>
</html>