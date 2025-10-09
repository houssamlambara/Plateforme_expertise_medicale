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
    <title>Dashboard Généraliste</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">
<!-- Header -->
<header class="bg-gradient-to-r from-green-600 to-green-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🩺 Dashboard Généraliste</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm">👨‍⚕️ Dr. <%= userName != null ? userName : userEmail %></span>
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
        <div class="bg-white rounded-xl shadow-md p-6">
            <h3 class="text-3xl font-bold text-gray-800">8</h3>
            <p class="text-gray-600 text-sm">Consultations aujourd'hui</p>
        </div>
        <div class="bg-white rounded-xl shadow-md p-6">
            <h3 class="text-3xl font-bold text-gray-800">3</h3>
            <p class="text-gray-600 text-sm">En attente</p>
        </div>
        <div class="bg-white rounded-xl shadow-md p-6">
            <h3 class="text-3xl font-bold text-gray-800">5</h3>
            <p class="text-gray-600 text-sm">Terminées</p>
        </div>
        <div class="bg-white rounded-xl shadow-md p-6">
            <h3 class="text-3xl font-bold text-gray-800">2</h3>
            <p class="text-gray-600 text-sm">Expertises demandées</p>
        </div>
    </div>

    <!-- Actions Section -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Actions Rapides</h2>
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
            <a href="<%= request.getContextPath() %>/generaliste/nouvelle-consultation"
               class="bg-gradient-to-r from-green-600 to-green-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Nouvelle Consultation
            </a>
            <a href="<%= request.getContextPath() %>/generaliste/demander-expertise"
               class="bg-gradient-to-r from-green-600 to-green-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Demander une Expertise
            </a>
            <a href="<%= request.getContextPath() %>/generaliste/patients"
               class="bg-gradient-to-r from-green-600 to-green-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Mes Patients
            </a>
            <a href="<%= request.getContextPath() %>/generaliste/historique"
               class="bg-gradient-to-r from-green-600 to-green-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Historique
            </a>
        </div>
    </div>

    <!-- Consultations Table -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Consultations du Jour</h2>
        <div class="overflow-x-auto">
            <table class="w-full">
                <thead>
                <tr class="bg-gray-50 border-b">
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Patient</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Motif</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Constantes</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Dupont Jean</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Douleurs abdominales</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 37.2°C, TA: 120/80</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-full text-xs font-medium">En attente</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Consulter</a> |
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Martin Sophie</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Contrôle diabète</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 36.8°C, Glycémie: 1.2g/L</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-xs font-medium">En cours</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Continuer</a> |
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Bernard Paul</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Hypertension</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 37.0°C, TA: 150/95</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs font-medium">Expertise demandée</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Voir expertise</a> |
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Dubois Marie</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Grippe saisonnière</td>
                    <td class="px-6 py-4 text-sm text-gray-600">T: 38.5°C, FC: 85 bpm</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-medium">Terminée</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Voir détails</a> |
                        <a href="#" class="text-green-600 hover:text-green-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>
