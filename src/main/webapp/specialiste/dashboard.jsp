<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
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
    <title>Dashboard Spécialiste</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">

<header class="bg-gradient-to-r from-red-600 to-red-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🔬 Dashboard Spécialiste</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm">👨‍⚕️ Dr. <%= userName != null ? userName : userEmail %></span>
            <a href="<%= request.getContextPath() %>/logout"
               class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">Déconnexion</a>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8">

    <!-- Actions Rapides -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Actions Rapides</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <a href="<%= request.getContextPath() %>/specialiste/expertises-en-attente"
               class="bg-gradient-to-r from-red-600 to-red-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Expertises en attente
            </a>
            <a href="<%= request.getContextPath() %>/specialiste/creneaux"
               class="bg-gradient-to-r from-red-600 to-red-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Gérer mes créneaux
            </a>
            <a href="<%= request.getContextPath() %>/specialiste/mes-expertises"
               class="bg-gradient-to-r from-red-600 to-red-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Mes Expertises
            </a>
        </div>
    </div>

    <!-- Tableau des demandes -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Demandes d'Expertise en Attente</h2>
        <div class="overflow-x-auto">
            <table class="w-full">
                <thead>
                <tr class="bg-gray-50 border-b">
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Patient</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Généraliste</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Question / Motif</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Priorité</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                <c:choose>
                    <c:when test="${not empty demandes}">
                        <c:forEach var="demande" items="${demandes}">
                            <tr class="hover:bg-gray-50 transition">
                                <!-- Nom et prénom du patient -->
                                <td class="px-6 py-4 text-sm text-gray-800">
                                        ${demande.patient.nom} ${demande.patient.prenom}
                                </td>

                                <!-- Nom du généraliste -->
                                <td class="px-6 py-4 text-sm text-gray-600">
                                    Dr. ${demande.generaliste.nom}
                                </td>

                                <!-- Motif de consultation -->
                                <td class="px-6 py-4 text-sm text-gray-600">
                                        ${demande.motif}
                                </td>

                                <!-- Priorité -->
                                <td class="px-6 py-4">
                                    <c:choose>
                                        <c:when test="${demande.priorite == 'Urgente'}">
                                            <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs font-medium">Urgente</span>
                                        </c:when>
                                        <c:when test="${demande.priorite == 'Normale'}">
                                            <span class="px-3 py-1 bg-orange-100 text-orange-700 rounded-full text-xs font-medium">Normale</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-medium">Basse</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- Statut -->
                                <td class="px-6 py-4">
                                    <c:choose>
                                        <c:when test="${demande.statut.name() == 'EN_ATTENTE_AVIS_SPECIALISTE'}">
                                            <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-full text-xs font-medium">En attente</span>
                                        </c:when>
                                        <c:when test="${demande.statut.name() == 'EN_COURS'}">
                                            <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-xs font-medium">En cours</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-medium">Terminée</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- Actions -->
                                <td class="px-6 py-4 text-sm">
                                    <a href="${pageContext.request.contextPath}/specialiste/repondre?demandeId=${demande.id}" class="text-red-600 hover:text-red-800 font-medium">Répondre</a> |
                                    <a href="${pageContext.request.contextPath}/specialiste/voir-dossier?demandeId=${demande.id}" class="text-red-600 hover:text-red-800 font-medium">Voir dossier</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="px-6 py-4 text-center text-gray-500">
                                Aucune demande d'expertise en attente.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>

            </table>
        </div>
    </div>
</main>

</body>
</html>
