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
    <title>Dashboard Spécialiste</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">
<!-- Header -->
<header class="bg-gradient-to-r from-red-600 to-red-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🔬 Dashboard Spécialiste</h1>
        <div class="flex items-center gap-4">
            <span class="text-sm">👨‍⚕️ Dr. <%= userName != null ? userName : userEmail %></span>
            <a href="<%= request.getContextPath() %>/logout"
               class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">
                Déconnexion
            </a>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-6 py-8">

    <!-- Actions Section -->
    <div class="bg-white rounded-xl shadow-md p-6 mb-8">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Actions Rapides</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <a href="<%= request.getContextPath() %>/specialiste/expertises-en-attente"
               class="bg-gradient-to-r from-red-600 to-red-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Expertises en attente
            </a>
            <a href="<%= request.getContextPath() %>/specialiste/mes-expertises"
               class="bg-gradient-to-r from-red-600 to-red-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Mes Expertises
            </a>
            <a href="<%= request.getContextPath() %>/specialiste/historique"
               class="bg-gradient-to-r from-red-600 to-red-800 text-white px-6 py-4 rounded-lg text-center font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                Historique
            </a>
        </div>
    </div>

    <!-- Expertises Table -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Demandes d'Expertise en Attente</h2>
        <div class="overflow-x-auto">
            <table class="w-full">
                <thead>
                <tr class="bg-gray-50 border-b">
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Patient</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Généraliste</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Question</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Priorité</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Statut</th>
                    <th class="px-6 py-3 text-left text-sm font-semibold text-gray-700">Actions</th>
                </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Bernard Paul</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Dr. Lambara</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Hypertension résistante au traitement</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-red-100 text-red-700 rounded-full text-xs font-medium">Urgente</span>
                    </td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-full text-xs font-medium">En attente</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Répondre</a> |
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Dubois Marie</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Dr. Lambara</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Suspicion de diabète type 2</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-orange-100 text-orange-700 rounded-full text-xs font-medium">Normale</span>
                    </td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-xs font-medium">En cours</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Continuer</a> |
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Martin Sophie</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Dr. Lambara</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Contrôle post-opératoire</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-medium">Basse</span>
                    </td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-full text-xs font-medium">En attente</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Répondre</a> |
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                <tr class="hover:bg-gray-50 transition">
                    <td class="px-6 py-4 text-sm text-gray-800">Dupont Jean</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Dr. Lambara</td>
                    <td class="px-6 py-4 text-sm text-gray-600">Douleurs chroniques inexpliquées</td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-orange-100 text-orange-700 rounded-full text-xs font-medium">Normale</span>
                    </td>
                    <td class="px-6 py-4">
                        <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-xs font-medium">Terminée</span>
                    </td>
                    <td class="px-6 py-4">
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Voir réponse</a> |
                        <a href="#" class="text-red-600 hover:text-red-800 font-medium text-sm">Voir dossier</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>
