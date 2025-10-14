<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer une Consultation</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>

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

<body class="bg-gray-100 min-h-screen">

<div class="container mx-auto px-6 py-10">
    <div class="bg-white shadow-lg rounded-xl p-8 max-w-2xl mx-auto">

        <h2 class="text-2xl font-bold text-gray-800 mb-6">Créer une nouvelle consultation</h2>

        <c:if test="${not empty success}">
            <div class="bg-green-100 text-green-700 px-4 py-2 rounded mb-4">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="bg-red-100 text-red-700 px-4 py-2 rounded mb-4">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/generaliste/cree-consultation" method="post" class="space-y-6">

            <div>
                <label class="block text-gray-700 font-semibold mb-2">Symptômes :</label>
                <textarea name="symptomes" rows="4" required class="w-full border border-gray-300 rounded-lg px-4 py-2"></textarea>
            </div>

            <div>
                <label class="block text-gray-700 font-semibold mb-2">Diagnostic :</label>
                <textarea name="diagnostic" rows="4" required class="w-full border border-gray-300 rounded-lg px-4 py-2"></textarea>
            </div>

            <div>
                <label class="block text-gray-700 font-semibold mb-2">Prescription :</label>
                <textarea name="prescription" rows="4" class="w-full border border-gray-300 rounded-lg px-4 py-2"></textarea>
            </div>

            <div>
                <label class="block text-gray-700 font-semibold mb-2">Motif :</label>
                <textarea name="motif" rows="2" class="w-full border border-gray-300 rounded-lg px-4 py-2"></textarea>
            </div>

            <div>
                <label class="block text-gray-700 font-semibold mb-2">Observations :</label>
                <textarea name="observations" rows="2" class="w-full border border-gray-300 rounded-lg px-4 py-2"></textarea>
            </div>

            <div class="text-center">
                <button type="submit" class="bg-green-700 hover:bg-green-800 text-white font-semibold px-8 py-3 rounded-lg shadow-md">
                    Enregistrer la Consultation
                </button>
            </div>
        </form>
    </div>
</div>

</body>
</html>
