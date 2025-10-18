<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
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
    <title>Gestion des Créneaux</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">

<header class="bg-gradient-to-r from-red-600 to-red-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🗓️ Gestion des Créneaux Horaires</h1>
        <div class="flex items-center gap-4">
            <a href="${pageContext.request.contextPath}/specialiste/dashboard.jsp"
               class="text-sm hover:underline">← Retour</a>
            <span class="text-sm">👨‍⚕️ Dr. ${sessionScope.userName}</span>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8">

    <c:if test="${not empty sessionScope.success}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-6">${sessionScope.success}</div>
        <c:remove var="success" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">${sessionScope.error}</div>
        <c:remove var="error" scope="session" />
    </c:if>

    <!-- Formulaire de création simple -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-4">📅 Créer un nouveau créneau</h2>
        <p class="text-gray-600 mb-6">Créez un créneau personnalisé avec les horaires de votre choix</p>

        <form method="POST" class="space-y-4">
            <input type="hidden" name="action" value="creer-manuel">
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Date</label>
                    <input type="date" name="date" required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500"
                           min="<%= java.time.LocalDate.now() %>">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Heure de début</label>
                    <input type="time" name="heureDebut" required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Heure de fin</label>
                    <input type="time" name="heureFin" required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-green-500">
                </div>
            </div>
            <button type="submit" class="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 font-semibold">
                Créer ce créneau
            </button>
        </form>
    </div>

    <!-- Liste des créneaux -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Vos créneaux horaires</h2>

        <c:choose>
            <c:when test="${not empty creneaux}">
                <div class="overflow-x-auto">
                    <table class="w-full">
                        <thead>
                            <tr class="bg-gray-50 border-b">
                                <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Date</th>
                                <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Créneau</th>
                                <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                                <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
                            </tr>
                        </thead>
                        <tbody class="divide-y">
                            <c:forEach var="creneau" items="${creneaux}">
                                <tr class="hover:bg-gray-50">
                                    <td class="px-6 py-4 text-sm">${creneau.date}</td>
                                    <td class="px-6 py-4 text-sm font-medium">${creneau.creneauFormate}</td>
                                    <td class="px-6 py-4">
                                        <c:choose>
                                            <c:when test="${creneau.estPasse()}">
                                                <span class="px-3 py-1 bg-gray-100 text-gray-600 rounded-full text-xs">⏱️ Passé</span>
                                            </c:when>
                                            <c:when test="${creneau.disponible}">
                                                <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs">Disponible</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs">Réservé</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="px-6 py-4 text-sm">
                                        <c:choose>
                                            <c:when test="${!creneau.disponible && creneau.reservationId != null}">
                                                <span class="text-gray-500">Réservé</span>
                                            </c:when>
                                            <c:when test="${creneau.disponible && !creneau.estPasse()}">
                                                <form method="POST" onsubmit="return confirm('Supprimer ce créneau ?')" class="inline">
                                                    <input type="hidden" name="action" value="supprimer">
                                                    <input type="hidden" name="creneauId" value="${creneau.id}">
                                                    <button type="submit" class="text-red-600 hover:text-red-800 font-medium">
                                                        Supprimer
                                                    </button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-gray-400">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="text-center py-12">
                    <div class="text-6xl mb-4"></div>
                    <p class="text-gray-500 text-lg">Aucun créneau créé</p>
                    <p class="text-gray-400 text-sm mt-2">Utilisez le formulaire ci-dessus pour créer votre premier créneau</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</main>
</body>
</html>

