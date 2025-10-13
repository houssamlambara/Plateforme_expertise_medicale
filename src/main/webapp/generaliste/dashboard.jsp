<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Vérifier que l'utilisateur est connecté
    if (session.getAttribute("userEmail") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Généraliste</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">

<header class="bg-gradient-to-r from-green-600 to-green-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🩺 Dashboard Généraliste</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm">👨‍⚕️ Dr. ${sessionScope.userName}</span>
            <a href="${pageContext.request.contextPath}/logout"
               class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">
                Déconnexion
            </a>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8">

    <!-- File d'attente des patients -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">File d'attente des patients</h2>
        <div class="overflow-x-auto">
            <table class="w-full">
                <thead class="bg-gray-50 border-b">
                <tr>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Patient</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Arrivée</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">

                <c:forEach items="${fileDAttente}" var="visite">
                    <tr class="hover:bg-gray-50 transition">
                        <td class="px-6 py-4 text-sm text-gray-800 font-medium">
                                ${visite.patient.prenom} ${visite.patient.nom}
                        </td>
                        <td class="px-6 py-4 text-sm text-gray-600">
                                ${visite.formattedDate}
                        </td>
                        <td class="px-6 py-4">
                            <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-full text-xs font-medium">
                                En attente
                            </span>
                        </td>
                        <td class="px-6 py-4">
                            <a href="${pageContext.request.contextPath}/generaliste/cree-consultation?patientId=${visite.patient.id}"
                               class="text-green-600 hover:text-green-800 font-medium text-sm">
                                Créer Consultation
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty fileDAttente}">
                    <tr>
                        <td colspan="4" class="px-6 py-4 text-center text-gray-500">
                            Aucun patient en attente pour le moment.
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