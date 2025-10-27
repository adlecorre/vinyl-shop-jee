<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand bg-dark navbar-dark">
  <div class="container-fluid d-flex justify-content-between align-items-center">

    <!-- Menu gauche -->
    <ul id="menu" class="navbar-nav d-flex flex-row mb-0">
      <li class="nav-item me-3">
        <a class="nav-link" href="${pageContext.request.contextPath}/accueil">Accueil</a>
      </li>
    </ul>

    <!-- Menu droite -->
    <ul class="navbar-nav d-flex flex-row mb-0">
      <c:choose>
        <c:when test="${not empty sessionScope.utilisateur}">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
               data-bs-toggle="dropdown" aria-expanded="false">
              ${sessionScope.utilisateur.prenom} ${sessionScope.utilisateur.nom}
            </a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/connexion">Connexion</a>
          </li>
        </c:otherwise>
      </c:choose>
    </ul>

  </div>
</nav>
