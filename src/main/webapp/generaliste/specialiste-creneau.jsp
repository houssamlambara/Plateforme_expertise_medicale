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
    <title>Sélection Spécialiste & Créneau</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">

<header class="bg-gradient-to-r from-green-600 to-green-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">📅 Réservation de Consultation</h1>
        <div class="flex items-center gap-4">
            <a href="${pageContext.request.contextPath}/generaliste/dashboard"
               class="text-sm hover:underline">← Retour au dashboard</a>
            <span class="text-sm">👨‍⚕️ Dr. ${sessionScope.userName}</span>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8">

    <!-- Info Patient -->
    <c:if test="${not empty patient}">
        <div class="bg-blue-50 border-l-4 border-blue-600 p-4 rounded mb-6">
            <p class="text-gray-800 font-semibold">👤 Patient : ${patient.prenom} ${patient.nom}</p>
            <p class="text-gray-600 text-sm">Consultation en cours de création...</p>
        </div>
    </c:if>

    <!-- Messages d'erreur -->
    <c:if test="${not empty sessionScope.error}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">❌ ${sessionScope.error}</div>
        <c:remove var="error" scope="session" />
    </c:if>

    <!-- Barre de progression -->
    <div class="mb-8">
        <div class="flex items-center justify-center">
            <div class="flex items-center">
                <div class="flex items-center ${empty specialiste ? 'text-green-600' : 'text-gray-400'}">
                    <div class="w-10 h-10 rounded-full ${empty specialiste ? 'bg-green-600' : 'bg-green-500'} text-white flex items-center justify-center font-bold">
                        ${empty specialiste ? '1' : '✓'}
                    </div>
                    <span class="ml-2 font-semibold">Spécialiste</span>
                </div>

                <div class="w-24 h-1 ${not empty specialiste ? 'bg-green-500' : 'bg-gray-300'} mx-4"></div>

                <div class="flex items-center ${not empty specialiste ? 'text-green-600' : 'text-gray-400'}">
                    <div class="w-10 h-10 rounded-full ${not empty specialiste ? 'bg-green-600' : 'bg-gray-300'} text-white flex items-center justify-center font-bold">
                        2
                    </div>
                    <span class="ml-2 font-semibold">Créneau</span>
                </div>
            </div>
        </div>
    </div>

    <!-- ÉTAPE 1 : Sélection du Spécialiste -->
    <c:if test="${empty specialiste}">
        <div class="bg-white rounded-xl shadow-md p-6 mb-8">
            <h2 class="text-xl font-bold text-gray-800 mb-4">📍 Étape 1/2 : Choisissez un spécialiste</h2>
            <p class="text-gray-600 mb-6">Sélectionnez un médecin spécialiste pour voir ses créneaux disponibles</p>

            <c:choose>
                <c:when test="${not empty specialistes}">
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <c:forEach var="spec" items="${specialistes}">
                            <a href="?specialisteId=${spec.id}"
                               class="block border-2 border-gray-200 rounded-lg p-6 hover:border-green-500 hover:shadow-lg transition">
                                <div class="flex items-center mb-4">
                                    <div class="bg-green-100 text-green-700 rounded-full w-16 h-16 flex items-center justify-center text-2xl font-bold">
                                        ${spec.nom.substring(0, 1)}${spec.prenom.substring(0, 1)}
                                    </div>
                                    <div class="ml-4">
                                        <h3 class="font-bold text-lg text-gray-800">Dr. ${spec.nom} ${spec.prenom}</h3>
                                        <p class="text-sm text-gray-600">${spec.specialite}</p>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <p class="text-sm text-gray-600">
                                        <span class="font-semibold">Email :</span> ${spec.email}
                                    </p>
                                    <c:if test="${not empty spec.tarif}">
                                        <p class="text-sm text-gray-600">
                                            <span class="font-semibold">Tarif :</span> ${spec.tarif} DH
                                        </p>
                                    </c:if>
                                </div>

                                <div class="w-full bg-green-600 hover:bg-green-700 text-white text-center font-semibold py-2 rounded-lg transition">
                                    Sélectionner →
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-12">
                        <div class="text-6xl mb-4">👨‍⚕️</div>
                        <p class="text-gray-500">Aucun spécialiste disponible pour le moment</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

    <!-- ÉTAPE 2 : Sélection du Créneau -->
    <c:if test="${not empty specialiste}">
        <!-- Info Spécialiste sélectionné -->
        <div class="bg-green-50 border-l-4 border-green-600 p-4 rounded mb-6 flex justify-between items-center">
            <div>
                <p class="text-gray-800 font-semibold">👨‍⚕️ Spécialiste sélectionné : Dr. ${specialiste.nom} ${specialiste.prenom}</p>
                <p class="text-gray-600 text-sm">${specialiste.specialite} - ${specialiste.tarif} DH</p>
            </div>
            <a href="?" class="text-sm text-green-700 hover:text-green-900 font-semibold">
                ← Changer de spécialiste
            </a>
        </div>

        <!-- Sélection de date et créneaux -->
        <div class="bg-white rounded-xl shadow-md p-6">
            <h2 class="text-xl font-bold text-gray-800 mb-4">📍 Étape 2/2 : Sélectionnez un créneau</h2>

            <form method="GET" class="flex gap-4 items-end mb-6">
                <input type="hidden" name="specialisteId" value="${param.specialisteId}">

                <div class="flex-1">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Choisir une date</label>
                    <input type="date" name="date" required
                           value="${selectedDate}"
                           min="<%= java.time.LocalDate.now() %>"
                           class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500">
                </div>

                <button type="submit" class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition">
                    Voir les créneaux
                </button>
            </form>

            <!-- Liste des créneaux -->
            <c:if test="${not empty selectedDate}">
                <div class="border-t pt-6">
                    <h3 class="text-lg font-bold text-gray-800 mb-4">Créneaux disponibles pour le ${selectedDate}</h3>

                    <c:choose>
                        <c:when test="${not empty creneauxDisponibles}">
                            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                                <c:forEach var="creneau" items="${creneauxDisponibles}">
                                    <form method="POST" action="${pageContext.request.contextPath}/generaliste/specialiste-creneau">
                                        <input type="hidden" name="creneauId" value="${creneau.id}">

                                        <button type="submit"
                                                class="w-full bg-green-50 hover:bg-green-100 border-2 border-green-200 hover:border-green-500 rounded-lg p-4 text-center transition">
                                            <div class="text-lg font-bold text-green-700">${creneau.creneauFormate}</div>
                                            <div class="text-xs text-green-600 mt-1">✅ Disponible</div>
                                        </button>
                                    </form>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-6 text-center">
                                <div class="text-5xl mb-3">📅</div>
                                <p class="text-yellow-800 font-semibold">Aucun créneau disponible pour cette date</p>
                                <p class="text-yellow-600 text-sm mt-2">Essayez une autre date ou contactez le spécialiste</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <c:if test="${empty selectedDate}">
                <div class="bg-gray-50 border border-gray-200 rounded-lg p-6 text-center">
                    <div class="text-5xl mb-3">📅</div>
                    <p class="text-gray-600">Sélectionnez une date pour voir les créneaux disponibles</p>
                </div>
            </c:if>
        </div>
    </c:if>

</main>
</body>
</html>
