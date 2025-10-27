<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<%@ include file="partial/_links.jsp"%>
</head>

<body class="bg-light">
<%@ include file="partial/_menu.jsp"%>

<div class="card shadow p-4 mx-auto my-5" style="max-width:400px;">
	<h3 class="text-center mb-3">Créer un compte</h3>
	
	<form method="post" action="${pageContext.request.contextPath}/inscription">
            <div class="mb-3">
            	<label for="nom" class="form-label">Nom</label>
            	<input type="text" class="form-control" id="nom" name="nom" autocomplete="off" placeholder="Entrez votre nom" required>
		    </div>
		    
		    <div class="mb-3">
            	<label for="prenom" class="form-label">Prénom</label>
            	<input type="text" class="form-control" id="prenom" name="prenom" autocomplete="off" placeholder="Entrez votre prénom" required>
		    </div>
		    
		    <div class="mb-3">
            	<label for="email" class="form-label">Adresse mail</label>
            	<input type="email" class="form-control" id="email" name="email" autocomplete="off" placeholder="exemple@domaine.com" required>
		    </div>
		    
		    <div class="mb-3">
            	<label for="motDePasse" class="form-label">Mot de passe</label>
            	<input type="password" class="form-control" id="motDePasse" name="motDePasse" autocomplete="off" placeholder="Créez un mot de passe" required>
		    </div>
            
            <div class="mb-3">
                <label for="dateNaissance" class="form-label">Date de naissance <i>(optionnel)</i></label>
      			<input type="date" class="form-control" id="dateNaissance" name="dateNaissance" autocomplete="off">
            </div>
            
            <div class="mb-3">
                <label for="adresse" class="form-label">Adresse <i>(optionnel)</i></label>
      			<input type="text" class="form-control" id="adresse" name="adresse" autocomplete="off" placeholder="Rue, ville, code postal">
            </div>
            
            <div class="mb-3">
                <label for="telephone" class="form-label">Téléphone <i>(optionnel)</i></label>
      			<input type="tel" class="form-control" id="telephone" name="telephone" autocomplete="off" pattern="[0-9]{10}" maxlength="10" placeholder="Ex : 0733345678">
            </div>


            <button type="submit" class="btn btn-primary w-100">S'inscrire</button>
        </form>
      	
      	<p class="text-center mt-3">
            Vous avez déjà un compte ? 
            <a href="${pageContext.request.contextPath}/connexion">Se connecter</a>
        </p>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger text-center mt-3">${erreur}</div>
        </c:if>
    </div>
</div>

</body>
</html>