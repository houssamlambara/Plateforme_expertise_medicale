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
    <title>Dashboard Infirmier</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">

<header class="bg-gradient-to-r from-purple-600 to-purple-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🏥 Dashboard Infirmier</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm"><%= userName != null ? userName : userEmail %></span>
            <a href="<%= request.getContextPath() %>/logout"
               class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">
                Déconnexion
            </a>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8">

    <!-- Actions rapides -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Actions Rapides</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <a href="<%= request.getContextPath() %>/infirmier/ajouter-patient"
               class="bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Nouvelle Visite
            </a>
            <a href="<%= request.getContextPath() %>/infirmier/liste-patient"
               class="bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Liste des Patients
            </a>
            <a href="<%= request.getContextPath() %>/infirmier/historique"
               class="bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Historique
            </a>
        </div>
    </div>

    <!-- Tableau des patients en attente -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Patients en Attente</h2>
        <div class="overflow-x-auto">
            <table class="w-full">
                <thead>
                <tr class="bg-gray-50 border-b">
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Patient</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Heure d'arrivée</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Constantes</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                <c:forEach var="visite" items="${patientsEnAttente}">
                    <tr class="hover:bg-gray-50 transition">
                        <td class="px-6 py-4 text-sm text-gray-800">
                                ${visite.patient.prenom} ${visite.patient.nom}
                        </td>
                        <td class="px-6 py-4 text-sm text-gray-600">
                                ${visite.formattedDate}
                        </td>
                        <td class="px-6 py-4 text-sm text-gray-600">
                            T: ${visite.temperature}°C, FC: ${visite.frequenceCardiaque} bpm
                        </td>
                        <td class="px-6 py-4">
                                <span class="px-3 py-1 bg-orange-100 text-orange-700 rounded-full text-xs font-medium">
                                        ${visite.statut}
                                </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty patientsEnAttente}">
                    <tr>
                        <td colspan="5" class="px-6 py-4 text-center text-gray-500">
                            Aucun patient en attente
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</main>
</body>
</html>
