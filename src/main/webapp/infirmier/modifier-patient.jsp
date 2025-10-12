<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier le Patient</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">
<header class="bg-gradient-to-r from-blue-600 to-blue-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4">
        <h1 class="text-2xl font-bold">Modifier : ${patient.prenom} ${patient.nom}</h1>
    </div>
</header>

<main class="container mx-auto px-6 py-8">
    <div class="max-w-3xl mx-auto bg-white rounded-xl shadow-md p-8">
        <form action="${pageContext.request.contextPath}/infirmier/modifier" method="post" class="space-y-6">

            <input type="hidden" name="id" value="${patient.id}">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label for="nom" class="block text-sm font-semibold text-gray-700 mb-2">Nom</label>
                    <input type="text" id="nom" name="nom" value="${patient.nom}" required class="w-full px-4 py-3 border rounded-lg">
                </div>
                <div>
                    <label for="prenom" class="block text-sm font-semibold text-gray-700 mb-2">Prenom</label>
                    <input type="text" id="prenom" name="prenom" value="${patient.prenom}" required class="w-full px-4 py-3 border rounded-lg">
                </div>
            </div>

            <div>
                <label for="dateNaissance" class="block text-sm font-semibold text-gray-700 mb-2">Date de naissance</label>
                <input type="date" id="dateNaissance" name="dateNaissance" value="<fmt:formatDate value='${patient.dateNaissance}' pattern='yyyy-MM-dd'/>" required class="w-full px-4 py-3 border rounded-lg">
            </div>

            <div>
                <label for="numeroSecuriteSociale" class="block text-sm font-semibold text-gray-700 mb-2">Numero Securite Sociale</label>
                <input type="text" id="numeroSecuriteSociale" name="numeroSecuriteSociale" value="${patient.numSecuSociale}" required class="w-full px-4 py-3 border rounded-lg">
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label for="telephone" class="block text-sm font-semibold text-gray-700 mb-2">Telephone</label>
                    <input type="tel" id="telephone" name="telephone" value="${patient.telephone}" class="w-full px-4 py-3 border rounded-lg">
                </div>
                <div>
                    <label for="adresse" class="block text-sm font-semibold text-gray-700 mb-2">Adresse</label>
                    <input type="text" id="adresse" name="adresse" value="${patient.adresse}" class="w-full px-4 py-3 border rounded-lg">
                </div>
            </div>

            <div class="flex gap-4 pt-4">
                <button type="submit" class="flex-1 bg-gradient-to-r from-blue-600 to-blue-800 text-white px-6 py-3 rounded-lg font-semibold">
                    Enregistrer les modifications
                </button>
                <a href="${pageContext.request.contextPath}/infirmier/liste-patient" class="flex-1 bg-gray-200 text-gray-700 px-6 py-3 rounded-lg font-semibold text-center">
                    Annuler
                </a>
            </div>
        </form>
    </div>
</main>
</body>
</html>