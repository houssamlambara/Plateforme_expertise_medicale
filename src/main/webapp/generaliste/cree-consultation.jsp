<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Vérification de session
    if (session.getAttribute("userEmail") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }
    String userEmail = (String) session.getAttribute("userEmail");
    String userName = (String) session.getAttribute("userName");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer / Mettre à jour une Consultation</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

<!-- Header -->
<header class="bg-gradient-to-r from-green-600 to-green-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🩺 Dashboard Généraliste</h1>
        <div class="flex items-center gap-4">
            <a href="<%= request.getContextPath() %>/generaliste/dashboard"
               class="text-sm hover:underline">← Retour au dashboard</a>
            <span class="text-sm">👨‍⚕️ <%= userName != null ? userName : userEmail %></span>
            <a href="<%= request.getContextPath() %>/logout"
               class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">
                Déconnexion
            </a>
        </div>
    </div>
</header>

<!-- Contenu principal -->
<div class="container mx-auto px-6 py-10">
    <div class="bg-white shadow-lg rounded-xl p-8 max-w-2xl mx-auto">

        <h2 class="text-2xl font-bold text-gray-800 mb-6">
            <c:choose>
                <c:when test="${not empty consultation}">Modifier la consultation</c:when>
                <c:otherwise>Créer une nouvelle consultation</c:otherwise>
            </c:choose>
        </h2>

        <!-- Messages succès / erreur -->
        <c:if test="${not empty success}">
            <div class="bg-green-100 text-green-700 px-4 py-2 rounded mb-4">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="bg-red-100 text-red-700 px-4 py-2 rounded mb-4">${error}</div>
        </c:if>

        <!-- Infos patient -->
        <c:if test="${not empty patient}">
            <div class="bg-gray-50 border-l-4 border-green-700 p-4 rounded mb-6">
                <p class="text-gray-800 font-semibold">👤 Patient sélectionné :</p>
                <p class="text-gray-600">${patient.prenom} ${patient.nom}</p>
            </div>
        </c:if>

        <!-- Formulaire consultation -->
        <form action="<c:choose>
                        <c:when test='${not empty consultation}'>
                            ${pageContext.request.contextPath}/generaliste/maj-consultation
                        </c:when>
                        <c:otherwise>
                            ${pageContext.request.contextPath}/generaliste/cree-consultation
                        </c:otherwise>
                     </c:choose>"
              method="post"
              class="space-y-6">

            <!-- Champs cachés -->
            <input type="hidden" name="patientId" value="${patient.id != null ? patient.id : param.patientId}"/>
            <c:if test="${not empty consultation}">
                <input type="hidden" name="consultationId" value="${consultation.id}"/>
            </c:if>
            <c:if test="${not empty visite}">
                <input type="hidden" name="visiteId" value="${visite.id}"/>
            </c:if>

            <!-- Symptômes -->
            <div>
                <label class="block text-gray-700 font-semibold mb-2">Symptômes :</label>
                <textarea name="symptomes" rows="3" required
                          class="w-full border border-gray-300 rounded-lg px-4 py-2">${consultation.symptomes}</textarea>
            </div>

            <!-- Diagnostic -->
            <div>
                <label class="block text-gray-700 font-semibold mb-2">Diagnostic :</label>
                <textarea name="diagnostic" rows="3" required
                          class="w-full border border-gray-300 rounded-lg px-4 py-2">${consultation.diagnostic}</textarea>
            </div>

            <!-- Prescription -->
            <div>
                <label class="block text-gray-700 font-semibold mb-2">Prescription :</label>
                <textarea name="prescription" rows="3"
                          class="w-full border border-gray-300 rounded-lg px-4 py-2">${consultation.prescription}</textarea>
            </div>

            <!-- Motif -->
            <div>
                <label class="block text-gray-700 font-semibold mb-2">Motif :</label>
                <textarea name="motif" rows="2"
                          class="w-full border border-gray-300 rounded-lg px-4 py-2">${consultation.motif}</textarea>
            </div>

            <!-- Observations -->
            <div>
                <label class="block text-gray-700 font-semibold mb-2">Observations :</label>
                <textarea name="observations" rows="2"
                          class="w-full border border-gray-300 rounded-lg px-4 py-2">${consultation.observations}</textarea>
            </div>

            <!-- Boutons action -->
            <div class="flex justify-center gap-4 pt-4">
                <c:choose>
                    <c:when test="${not empty consultation}">
                        <button type="submit" name="action" value="cloturer"
                                class="bg-green-700 hover:bg-green-800 text-white font-semibold px-6 py-3 rounded-lg shadow-md">
                            ✅ Clôturer la consultation
                        </button>
                        <button type="submit" name="action" value="demanderSpecialiste"
                                class="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-3 rounded-lg shadow-md">
                            👨‍⚕️ Demander un avis spécialiste
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit"
                                class="bg-green-700 hover:bg-green-800 text-white font-semibold px-8 py-3 rounded-lg shadow-md">
                            Enregistrer la Consultation
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</div>

</body>
</html>
