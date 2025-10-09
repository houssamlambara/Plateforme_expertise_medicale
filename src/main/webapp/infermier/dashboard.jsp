<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<!-- Header -->
<header class="bg-gradient-to-r from-purple-600 to-purple-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🏥 Dashboard Infirmier</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm"><%= userName != null ? userName : userEmail %></span>
            <a href="<%= request.getContextPath() %>/auth/logout"
               class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">
                Déconnexion
            </a>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-6 py-8">
    <!-- Stats Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <!-- Stat Card 1 -->
        <div class="bg-white rounded-xl shadow-md p-6 flex items-center gap-4">
            <div>
                <h3 class="text-3xl font-bold text-gray-800">12</h3>
                <p class="text-gray-600 text-sm">Visites aujourd'hui</p>
            </div>
        </div>

        <!-- Stat Card 2 -->
        <div class="bg-white rounded-xl shadow-md p-6 flex items-center gap-4">
            <div>
                <h3 class="text-3xl font-bold text-gray-800">5</h3>
                <p class="text-gray-600 text-sm">En attente</p>
            </div>
        </div>

        <!-- Stat Card 3 -->
        <div class="bg-white rounded-xl shadow-md p-6 flex items-center gap-4">
            <div>
                <h3 class="text-3xl font-bold text-gray-800">7</h3>
                <p class="text-gray-600 text-sm">Terminées</p>
            </div>
        </div>

        <!-- Stat Card 4 -->
        <div class="bg-white rounded-xl shadow-md p-6 flex items-center gap-4">
            <div>
                <h3 class="text-3xl font-bold text-gray-800">45</h3>
                <p class="text-gray-600 text-sm">Patients total</p>
            </div>
        </div>
    </div>

    <!-- Actions Section -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Actions Rapides</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <a href="<%= request.getContextPath() %>/infermier/nouvelle-visite"
               class="bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Nouvelle Visite
            </a>
            <a href="<%= request.getContextPath() %>/infermier/patients"
               class="bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Liste des Patients
            </a>
            <a href="<%= request.getContextPath() %>/infermier/historique"
               class="bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Historique
            </a>
        </div>
    </div>

    <!-- Visites Table -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Visites du Jour</h2>
        <div class="overflow-x-auto">
            <table class="w-full">
                <thead>
                <tr class="bg-gray-50 border-b">
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Patient</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Heure d'arrivée</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Constantes</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Dupont Jean</td>
                    <td class="px-6 py-4 text-sm text-gray-600">08:30</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 37.2°C, FC: 75 bpm</td>
                    <td class="px-6 py-4">
                                <span class="px-3 py-1 bg-orange-100 text-orange-700 rounded-full text-xs font-medium">
                                    En attente
                                </span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-purple-600 hover:text-purple-800 font-medium text-sm">Voir détails</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Martin Sophie</td>
                    <td class="px-6 py-4 text-sm text-gray-600">09:15</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 36.8°C, FC: 80 bpm</td>
                    <td class="px-6 py-4">
                                <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-xs font-medium">
                                    En cours
                                </span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-purple-600 hover:text-purple-800 font-medium text-sm">Voir détails</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Bernard Paul</td>
                    <td class="px-6 py-4 text-sm text-gray-600">10:00</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 37.0°C, FC: 72 bpm</td>
                    <td class="px-6 py-4">
                                <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-medium">
                                    Terminée
                                </span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-purple-600 hover:text-purple-800 font-medium text-sm">Voir détails</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>
