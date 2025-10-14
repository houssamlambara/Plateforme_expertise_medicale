<%@ page import="org.platform_expertise_medicle.model.Patient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%
    if (session.getAttribute("userEmail") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }
    String userName = (String) session.getAttribute("userName");
    List<Patient> patients = (List<Patient>) request.getAttribute("patients");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Patients - Plateforme Expertise Médicale</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            50: '#f5f3ff',
                            100: '#ede9fe',
                            200: '#ddd6fe',
                            300: '#c4b5fd',
                            400: '#a78bfa',
                            500: '#8b5cf6',
                            600: '#7c3aed',
                            700: '#6d28d9',
                            800: '#5b21b6',
                            900: '#4c1d95',
                        }
                    }
                }
            }
        }
    </script>
</head>
<body class="h-full">
<!-- Header -->
<header class="bg-gradient-to-r from-primary-600 to-primary-800 text-white shadow-lg">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8 py-4">
        <div class="flex justify-between items-center">
            <div class="flex items-center space-x-4">
                <a href="<%= request.getContextPath() %>/infirmier/dashboard" class="text-white hover:text-gray-200">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <h1 class="text-2xl font-bold">Liste des Patients</h1>
            </div>
            <div class="flex items-center space-x-4">
                <a href="<%= request.getContextPath() %>/infirmier/dashboard"
                   class="text-sm hover:underline">Retour au dashboard</a>
                <span class="text-sm font-medium"><%= userName %></span>
                <a href="<%= request.getContextPath() %>/logout"
                   class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200">
                    <i class="fas fa-sign-out-alt mr-1"></i> Deconnexion
                </a>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-4 sm:px-6 lg:px-8 py-8">
    <!-- Search and Add Patient -->
    <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-8 gap-4">
        <div class="relative w-full sm:w-96">
            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i class="fas fa-search text-gray-400"></i>
            </div>
            <input type="text"
                   id="searchInput"
                   placeholder="Rechercher un patient..."
                   class="pl-10 pr-4 py-2 w-full border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500">
        </div>
        <a href="<%= request.getContextPath() %>/infirmier/ajouter-patient"
           class="w-full sm:w-auto bg-primary-600 hover:bg-primary-700 text-white px-6 py-2.5 rounded-lg font-medium transition-all duration-200 flex items-center justify-center space-x-2">
            <i class="fas fa-plus"></i>
            <span>Nouveau Patient</span>
        </a>
    </div>

    <!-- Patients List -->
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                <tr>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Patient
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Date de naissance
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Contact
                    </th>
                    <th scope="col" class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Actions
                    </th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200" id="patientsTableBody">
                <% if (patients != null && !patients.isEmpty()) {
                    for (Patient patient : patients) {
                %>
                <tr class="hover:bg-gray-50 transition-colors duration-150">
                    <td class="px-6 py-4 whitespace-nowrap">
                        <div class="flex items-center">
                            <div class="flex-shrink-0 h-10 w-10 rounded-full bg-primary-100 flex items-center justify-center">
                                        <span class="text-primary-700 font-medium">
                                            <%= patient.getNom().substring(0, 1) %><%= patient.getPrenom().substring(0, 1) %>
                                        </span>
                            </div>
                            <div class="ml-4">
                                <div class="text-sm font-medium text-gray-900">
                                    <%= patient.getPrenom() %> <%= patient.getNom() %>
                                </div>
                                <div class="text-sm text-gray-500">#<%= patient.getId() %></div>
                            </div>
                        </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <div class="text-sm text-gray-900">
                            <%= dateFormat.format(patient.getDateNaissance()) %>
                        </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <div class="text-sm text-gray-900"><%= patient.getTelephone() %></div>
                        <div class="text-sm text-gray-500"><%= patient.getNumSecuSociale() %></div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <div class="flex justify-end space-x-3">
                            <a href="<%= request.getContextPath() %>/infirmier/details?id=<%= patient.getId() %>"
                               class="text-primary-600 hover:text-primary-900">
                                <i class="far fa-eye"></i>
                                <span class="sr-only">Voir</span>
                            </a>
                            <a href="<%= request.getContextPath() %>/infirmier/modifier?id=<%= patient.getId() %>"
                               class="text-blue-600 hover:text-blue-900">
                                <i class="far fa-edit"></i>
                                <span class="sr-only">Modifier</span>
                            </a>
                        </div>
                    </td>
                </tr>
                <% }
                } else { %>
                <tr>
                    <td colspan="4" class="px-6 py-4 text-center text-sm text-gray-500">
                        Aucun patient trouvé
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

<script>
    // Fonction de recherche
    document.getElementById('searchInput').addEventListener('input', function(e) {
        const searchTerm = e.target.value.toLowerCase();
        const rows = document.querySelectorAll('#patientsTableBody tr');

        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            if (text.includes(searchTerm)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });
</script>
</body>
</html>