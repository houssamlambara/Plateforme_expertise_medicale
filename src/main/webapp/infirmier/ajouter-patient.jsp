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
    <title>Ajouter un Patient</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">
<!-- Header -->
<header class="bg-gradient-to-r from-purple-600 to-purple-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">🏥 Ajouter un Patient</h1>
        <div class="flex items-center gap-4">
            <a href="<%= request.getContextPath() %>/infermier/dashboard.jsp" 
               class="text-sm hover:underline">← Retour au dashboard</a>
            <span class="text-sm"><%= userName != null ? userName : userEmail %></span>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="container mx-auto px-6 py-8">
    <div class="max-w-3xl mx-auto">
        <!-- Messages -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-lg mb-6">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <% if (request.getAttribute("success") != null) { %>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded-lg mb-6">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>

        <!-- Formulaire -->
        <div class="bg-white rounded-xl shadow-md p-8">
            <h2 class="text-2xl font-bold text-gray-800 mb-6">Informations du Patient</h2>
            
            <form action="<%= request.getContextPath() %>/infirmier/ajouter-patient" method="post" class="space-y-6">
                <!-- Nom et Prénom -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label for="nom" class="block text-sm font-semibold text-gray-700 mb-2">
                            Nom <span class="text-red-500">*</span>
                        </label>
                        <input type="text" id="nom" name="nom" required
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                               placeholder="Nom du patient">
                    </div>
                    
                    <div>
                        <label for="prenom" class="block text-sm font-semibold text-gray-700 mb-2">
                            Prénom <span class="text-red-500">*</span>
                        </label>
                        <input type="text" id="prenom" name="prenom" required
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                               placeholder="Prénom du patient">
                    </div>
                </div>

                <!-- Date de naissance et Sexe -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label for="dateNaissance" class="block text-sm font-semibold text-gray-700 mb-2">
                            Date de naissance <span class="text-red-500">*</span>
                        </label>
                        <input type="date" id="dateNaissance" name="dateNaissance" required
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                    </div>
                    
                    <div>
                        <label for="sexe" class="block text-sm font-semibold text-gray-700 mb-2">
                            Sexe <span class="text-red-500">*</span>
                        </label>
                        <select id="sexe" name="sexe" required
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                            <option value="">Sélectionner...</option>
                            <option value="M">Masculin</option>
                            <option value="F">Féminin</option>
                        </select>
                    </div>
                </div>

                <!-- Numéro de sécurité sociale -->
                <div>
                    <label for="numeroSecuriteSociale" class="block text-sm font-semibold text-gray-700 mb-2">
                        Numéro de sécurité sociale <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="numeroSecuriteSociale" name="numeroSecuriteSociale" required
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                           placeholder="Ex: 1 23 45 67 890 123 45">
                </div>

                <!-- Téléphone et Email -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label for="telephone" class="block text-sm font-semibold text-gray-700 mb-2">
                            Téléphone
                        </label>
                        <input type="tel" id="telephone" name="telephone"
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                               placeholder="Ex: 06 12 34 56 78">
                    </div>
                    
                    <div>
                        <label for="email" class="block text-sm font-semibold text-gray-700 mb-2">
                            Email
                        </label>
                        <input type="email" id="email" name="email"
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                               placeholder="patient@exemple.com">
                    </div>
                </div>

                <!-- Adresse -->
                <div>
                    <label for="adresse" class="block text-sm font-semibold text-gray-700 mb-2">
                        Adresse complète
                    </label>
                    <textarea id="adresse" name="adresse" rows="3"
                              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                              placeholder="Adresse complète du patient"></textarea>
                </div>

                <!-- Groupe sanguin et Allergies -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label for="groupeSanguin" class="block text-sm font-semibold text-gray-700 mb-2">
                            Groupe sanguin
                        </label>
                        <select id="groupeSanguin" name="groupeSanguin"
                                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent">
                            <option value="">Sélectionner...</option>
                            <option value="A+">A+</option>
                            <option value="A-">A-</option>
                            <option value="B+">B+</option>
                            <option value="B-">B-</option>
                            <option value="AB+">AB+</option>
                            <option value="AB-">AB-</option>
                            <option value="O+">O+</option>
                            <option value="O-">O-</option>
                        </select>
                    </div>
                    
                    <div>
                        <label for="allergies" class="block text-sm font-semibold text-gray-700 mb-2">
                            Allergies connues
                        </label>
                        <input type="text" id="allergies" name="allergies"
                               class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                               placeholder="Ex: Pénicilline, Pollen...">
                    </div>
                </div>

                <!-- Antécédents médicaux -->
                <div>
                    <label for="antecedents" class="block text-sm font-semibold text-gray-700 mb-2">
                        Antécédents médicaux
                    </label>
                    <textarea id="antecedents" name="antecedents" rows="4"
                              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                              placeholder="Maladies chroniques, opérations antérieures, traitements en cours..."></textarea>
                </div>

                <div class="mt-8 pt-6 border-t border-gray-200">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Signes Vitaux de la Visite</h2>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label for="temperature" class="block text-sm font-semibold text-gray-700 mb-2">Température (°C)</label>
                            <input type="text" id="temperature" name="temperature" class="w-full px-4 py-3 border border-gray-300 rounded-lg" placeholder="Ex: 37.5">
                        </div>
                        <div>
                            <label for="poids" class="block text-sm font-semibold text-gray-700 mb-2">Poids (kg)</label>
                            <input type="text" id="poids" name="poids" class="w-full px-4 py-3 border border-gray-300 rounded-lg" placeholder="Ex: 70.5">
                        </div>
                        <div>
                            <label for="taille" class="block text-sm font-semibold text-gray-700 mb-2">Taille (cm)</label>
                            <input type="text" id="taille" name="taille" class="w-full px-4 py-3 border border-gray-300 rounded-lg" placeholder="Ex: 175">
                        </div>
                        <div>
                            <label for="tensionArterielle" class="block text-sm font-semibold text-gray-700 mb-2">Tension Artérielle</label>
                            <input type="text" id="tensionArterielle" name="tensionArterielle" class="w-full px-4 py-3 border border-gray-300 rounded-lg" placeholder="Ex: 120/80">
                        </div>
                        <div>
                            <label for="frequenceCardiaque" class="block text-sm font-semibold text-gray-700 mb-2">Fréquence Cardiaque (bpm)</label>
                            <input type="number" id="frequenceCardiaque" name="frequenceCardiaque" class="w-full px-4 py-3 border border-gray-300 rounded-lg" placeholder="Ex: 80">
                        </div>
                        <div>
                            <label for="frequenceRespiratoire" class="block text-sm font-semibold text-gray-700 mb-2">Fréquence Respiratoire</label>
                            <input type="number" id="frequenceRespiratoire" name="frequenceRespiratoire" class="w-full px-4 py-3 border border-gray-300 rounded-lg" placeholder="Ex: 16">
                        </div>
                    </div>
                </div>

                <!-- Boutons -->
                <div class="flex gap-4 pt-4">
                    <button type="submit"
                            class="flex-1 bg-gradient-to-r from-purple-600 to-purple-800 text-white px-6 py-3 rounded-lg font-semibold hover:shadow-lg transform hover:-translate-y-1 transition">
                        ✓ Enregistrer le patient
                    </button>
                    <a href="<%= request.getContextPath() %>/infirmier/dashboard.jsp"
                       class="flex-1 bg-gray-200 text-gray-700 px-6 py-3 rounded-lg font-semibold text-center hover:bg-gray-300 transition">
                        Annuler
                    </a>
                </div>
            </form>
        </div>
    </div>
</main>
</body>
</html>
