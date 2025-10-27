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
<%@ include file="partial/_menu.jsp"%>
</head>
<body class="bg-light">
	<h1 class="container text-center my-3 text-success">Créer un compte</h1>

	<div class="d-flex justify-content-center">
  <form action="${pageContext.request.contextPath}/inscription"
        method="post"
        class="p-4 border rounded shadow-sm">

    <div class="mb-3 col-sm-12">
      <label for="nom" class="form-label">Nom</label>
      <input type="text" class="form-control" id="nom" name="nom"
             autocomplete="off" required
             placeholder="Entrez votre nom">
    </div>

    <div class="mb-3 col-sm-12">
      <label for="prenom" class="form-label">Prénom</label>
      <input type="text" class="form-control" id="prenom" name="prenom"
             autocomplete="off" required
             placeholder="Entrez votre prénom">
    </div>

    <div class="mb-3 col-sm-12">
      <label for="email" class="form-label">Adresse mail</label>
      <input type="email" class="form-control" id="email" name="email"
             autocomplete="off" required
             placeholder="exemple@domaine.com">
    </div>

    <div class="mb-3 col-sm-12">
      <label for="motDePasse" class="form-label">Mot de passe</label>
      <input type="password" class="form-control" id="motDePasse" name="motDePasse"
             autocomplete="off" required
             placeholder="Créez un mot de passe">
    </div>

    <div class="mb-3 col-sm-12">
      <label for="dateNaissance" class="form-label">
        Date de naissance <i>(optionnel)</i>
      </label>
      <input type="date" class="form-control" id="dateNaissance" name="dateNaissance"
             autocomplete="off">
    </div>

    <div class="mb-3 col-sm-12">
      <label for="adresse" class="form-label">
        Adresse <i>(optionnel)</i>
      </label>
      <input type="text" class="form-control" id="adresse" name="adresse"
             autocomplete="off" placeholder="Rue, ville, code postal">
    </div>

    <div class="mb-3 col-sm-12">
      <label for="telephone" class="form-label">
        Téléphone <i>(optionnel)</i>
      </label>
      <input type="tel" class="form-control" id="telephone" name="telephone"
             autocomplete="off"
             pattern="[0-9]{10}" maxlength="10"
             placeholder="Ex : 0733345678">
    </div>

    <div class="text-center">
      <button type="submit" class="btn btn-success">S'inscrire</button>
    </div>

  </form>
</div>

	
	<div class="container text-center">
		<c:url var="lienConnexion" value="/connexion">
		</c:url>
		<a class="text-success" href="${lienConnexion }">Se connecter</a>
	</div>


</body>
</html>