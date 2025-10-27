<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
<%@ include file="partial/_links.jsp"%></head>
<body class="bg-light">
<%@ include file="partial/_menu.jsp"%>

<div class="container mt-5">
    <div class="card shadow p-4 mx-auto" style="max-width:400px;">
        <h3 class="text-center mb-3">Connexion</h3>

        <form method="post" action="${pageContext.request.contextPath}/connexion">
            <div class="mb-3">
                <label for="email" class="form-label">Email :</label>
                <input type="text" class="form-control" id="email" name="email" value="${param.email}" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Mot De Passe :</label>
                <input type="password" class="form-control" id="motDePasse" name="motDePasse" value="${param.nom}" required>
            </div>


            <button type="submit" class="btn btn-primary w-100">Se connecter</button>
        </form>
      	
      	<p class="text-center mt-3">
            Pas encore de compte ? 
            <a href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
        </p>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger text-center mt-3">${erreur}</div>
        </c:if>
    </div>
</div>

</body>
</html>
