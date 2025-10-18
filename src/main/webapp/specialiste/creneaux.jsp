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
        <h1 class="text-2xl font-bold">Gestion des Créneaux Horaires</h1>
        <div class="flex items-center gap-4">
            <a href="${pageContext.request.contextPath}/specialiste/dashboard.jsp"
               class="text-sm hover:underline">← Retour</a>
            <span class="text-sm">👨‍⚕️ Dr. ${sessionScope.userName}</span>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8">

    <c:if test="${not empty sessionScope.success}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-6">✅ ${sessionScope.success}</div>
        <c:remove var="success" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">❌ ${sessionScope.error}</div>
        <c:remove var="error" scope="session" />
    </c:if>

    <!-- Formulaires de création -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-4">Créer des créneaux</h2>

        <div class="flex gap-4 mb-6 border-b">
            <button onclick="showTab('manuel')" id="tab-manuel" class="px-4 py-2 font-semibold text-blue-600 border-b-2 border-blue-600">
                Création manuelle
            </button>
            <button onclick="showTab('auto')" id="tab-auto" class="px-4 py-2 font-semibold text-gray-500 hover:text-blue-600">
                Génération automatique
            </button>
        </div>

        <!-- Création manuelle -->
        <div id="form-manuel" class="tab-content">
            <p class="text-gray-600 mb-4">Créez un créneau personnalisé avec les horaires de votre choix</p>
            <form method="POST" class="space-y-4">
                <input type="hidden" name="action" value="creer-manuel">
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Date</label>
                        <input type="date" name="date" required class="w-full px-4 py-2 border rounded-lg"
                               min="<%= java.time.LocalDate.now() %>">
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Heure début</label>
                        <input type="time" name="heureDebut" required class="w-full px-4 py-2 border rounded-lg">
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Heure fin</label>
                        <input type="time" name="heureFin" required class="w-full px-4 py-2 border rounded-lg">
                    </div>
                </div>
                <button type="submit" class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700">
                    Créer ce créneau
                </button>
            </form>
        </div>

        <!-- Génération automatique -->
        <div id="form-auto" class="tab-content hidden">
            <p class="text-gray-600 mb-4">Créez automatiquement 6 créneaux de 30 minutes (09h00 - 12h00)</p>
            <form method="POST" class="flex gap-4 items-end">
                <input type="hidden" name="action" value="generer">
                <div class="flex-1">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Date</label>
                    <input type="date" name="date" required class="w-full px-4 py-2 border rounded-lg"
                           min="<%= java.time.LocalDate.now() %>">
                </div>
                <button type="submit" class="bg-red-600 text-white px-6 py-2 rounded-lg hover:bg-red-700">
                    Générer 6 créneaux
                </button>
            </form>
        </div>
    </div>

    <script>
        function showTab(tab) {
            document.querySelectorAll('.tab-content').forEach(el => el.classList.add('hidden'));
            document.querySelectorAll('[id^="tab-"]').forEach(el => {
                el.classList.remove('text-blue-600', 'border-b-2', 'border-blue-600');
                el.classList.add('text-gray-500');
            });
            document.getElementById('form-' + tab).classList.remove('hidden');
            const activeTab = document.getElementById('tab-' + tab);
            activeTab.classList.remove('text-gray-500');
            activeTab.classList.add('text-blue-600', 'border-b-2', 'border-blue-600');
        }
    </script>

    <!-- Liste des créneaux -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Vos créneaux horaires</h2>

        <c:choose>
            <c:when test="${not empty creneaux}">
                <table class="w-full">
                    <thead>
                        <tr class="bg-gray-50 border-b">
                            <th class="px-6 py-3 text-left text-sm font-semibold">Date</th>
                            <th class="px-6 py-3 text-left text-sm font-semibold">Créneau</th>
                            <th class="px-6 py-3 text-left text-sm font-semibold">Statut</th>
                            <th class="px-6 py-3 text-left text-sm font-semibold">Actions</th>
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
                                            <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs">✅ Disponible</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs">🔒 Réservé</span>
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
            </c:when>
            <c:otherwise>
                <div class="text-center py-12">
                    <div class="text-6xl mb-4"></div>
                    <p class="text-gray-500">Aucun créneau créé</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</main>
</body>
</html>

