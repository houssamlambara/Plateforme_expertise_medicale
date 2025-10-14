<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.platform_expertise_medicle.model.Patient" %>
<%@ page import="org.platform_expertise_medicle.model.SigneVitaux" %>
<%@ page import="java.util.List" %>
<%
    Patient patient = (Patient) request.getAttribute("patient");
    List<SigneVitaux> signesVitaux = (List<SigneVitaux>) request.getAttribute("signesVitaux");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails Patient - <%= patient.getPrenom() %> <%= patient.getNom() %></title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">

<!-- Header -->
<header class="bg-gradient-to-r from-purple-600 to-purple-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">Détails du Patient</h1>
        <a href="<%= request.getContextPath() %>/infirmier/dashboard"
           class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">
            ← Retour au dashboard
        </a>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-6 py-8 space-y-8">

    <!-- Informations du Patient -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-2xl font-bold mb-4 text-gray-800">Informations du Patient</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <p class="text-gray-600 font-semibold">Nom :</p>
                <p class="text-gray-800"><%= patient.getNom() %></p>
            </div>
            <div>
                <p class="text-gray-600 font-semibold">Prénom :</p>
                <p class="text-gray-800"><%= patient.getPrenom() %></p>
            </div>
            <div>
                <p class="text-gray-600 font-semibold">Date de naissance :</p>
                <p class="text-gray-800"><%= patient.getDateNaissance() %></p>
            </div>
            <div>
                <p class="text-gray-600 font-semibold">Téléphone :</p>
                <p class="text-gray-800"><%= patient.getTelephone() != null ? patient.getTelephone() : "-" %></p>
            </div>
            <div>
                <p class="text-gray-600 font-semibold">Adresse :</p>
                <p class="text-gray-800"><%= patient.getAdresse() != null ? patient.getAdresse() : "-" %></p>
            </div>
            <div>
                <p class="text-gray-600 font-semibold">Numéro sécurité sociale :</p>
                <p class="text-gray-800"><%= patient.getNumSecuSociale() %></p>
            </div>
        </div>
    </div>

    <!-- Signes Vitaux -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-2xl font-bold mb-4 text-gray-800">Signes Vitaux</h2>

        <% if (signesVitaux != null && !signesVitaux.isEmpty()) { %>
        <div class="space-y-6">
            <% for (SigneVitaux sv : signesVitaux) { %>
            <div class="border border-gray-200 rounded-lg p-4 bg-gray-50">
                <p class="text-gray-600 font-semibold mb-2">Mesuré le : <span class="text-gray-800"><%= sv.getFormattedDate() %></span></p>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <div>
                        <p class="text-gray-600">Température (°C)</p>
                        <p class="text-gray-800 font-medium"><%= sv.getTemperature() %></p>
                    </div>
                    <div>
                        <p class="text-gray-600">Poids (kg)</p>
                        <p class="text-gray-800 font-medium"><%= sv.getPoids() %></p>
                    </div>
                    <div>
                        <p class="text-gray-600">Taille (cm)</p>
                        <p class="text-gray-800 font-medium"><%= sv.getTaille() %></p>
                    </div>
                    <div>
                        <p class="text-gray-600">Tension artérielle</p>
                        <p class="text-gray-800 font-medium"><%= sv.getTensionArterielle() %></p>
                    </div>
                    <div>
                        <p class="text-gray-600">Fréquence cardiaque (bpm)</p>
                        <p class="text-gray-800 font-medium"><%= sv.getFrequenceCardiaque() %></p>
                    </div>
                    <div>
                        <p class="text-gray-600">Fréquence respiratoire</p>
                        <p class="text-gray-800 font-medium"><%= sv.getFrequenceRespiratoire() %></p>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <p class="text-gray-500">Aucun signe vital enregistré pour ce patient.</p>
        <% } %>
    </div>

</main>
</body>
</html>
