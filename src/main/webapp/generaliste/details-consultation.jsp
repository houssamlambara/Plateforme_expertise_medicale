<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session.getAttribute("userEmail") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }
%>

<%@ page import="org.platform_expertise_medicle.model.Consultation,org.platform_expertise_medicle.model.Patient,org.platform_expertise_medicle.model.SigneVitaux,java.util.List" %>

<%
    Consultation consultation = (Consultation) request.getAttribute("consultation");
    List<SigneVitaux> signesVitaux = (List<SigneVitaux>) request.getAttribute("signesVitaux");
    Patient patient = consultation.getPatient();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails Consultation - <%= patient != null ? patient.getPrenom() + " " + patient.getNom() : "Patient supprimé" %></title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">

<header class="bg-gradient-to-r from-green-600 to-green-800 text-white shadow-lg">
    <div class="container mx-auto px-6 py-4 flex justify-between items-center">
        <h1 class="text-2xl font-bold">Détails de la Consultation</h1>
        <div class="flex items-center gap-4">
            <a href="${pageContext.request.contextPath}/generaliste/dashboard" class="text-sm hover:underline">← Retour au dashboard</a>
            <span class="text-sm">👨‍⚕️ Dr. ${sessionScope.userName}</span>
            <a href="${pageContext.request.contextPath}/logout" class="bg-white/20 hover:bg-white/30 px-4 py-2 rounded-lg transition">Déconnexion</a>
        </div>
    </div>
</header>

<main class="container mx-auto px-6 py-8 space-y-8">
    <!-- Consultation Info -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-2xl font-bold mb-4 text-gray-800">Informations de la Consultation</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div><p class="text-gray-600 font-semibold">Motif :</p><p class="text-gray-800"><%= consultation.getMotif() != null ? consultation.getMotif() : "-" %></p></div>
            <div><p class="text-gray-600 font-semibold">Symptômes :</p><p class="text-gray-800"><%= consultation.getSymptomes() != null ? consultation.getSymptomes() : "-" %></p></div>
            <div><p class="text-gray-600 font-semibold">Diagnostic :</p><p class="text-gray-800"><%= consultation.getDiagnostic() != null ? consultation.getDiagnostic() : "-" %></p></div>
            <div><p class="text-gray-600 font-semibold">Prescription :</p><p class="text-gray-800"><%= consultation.getPrescription() != null ? consultation.getPrescription() : "-" %></p></div>
            <div><p class="text-gray-600 font-semibold">Statut :</p><p class="text-gray-800"><%= consultation.getStatut() %></p></div>
            <div><p class="text-gray-600 font-semibold">Date :</p><p class="text-gray-800"><%= consultation.getDateConsultation() != null ? consultation.getDateConsultation() : "-" %></p></div>
        </div>
    </div>

    <!-- Patient Info -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-2xl font-bold mb-4 text-gray-800">Patient</h2>
        <c:choose>
            <c:when test="${patient != null}">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div><p class="text-gray-600 font-semibold">Nom :</p><p class="text-gray-800"><%= patient.getNom() %></p></div>
                    <div><p class="text-gray-600 font-semibold">Prénom :</p><p class="text-gray-800"><%= patient.getPrenom() %></p></div>
                    <div><p class="text-gray-600 font-semibold">Date de naissance :</p><p class="text-gray-800"><%= patient.getDateNaissance() %></p></div>
                    <div><p class="text-gray-600 font-semibold">Téléphone :</p><p class="text-gray-800"><%= patient.getTelephone() != null ? patient.getTelephone() : "-" %></p></div>
                    <div><p class="text-gray-600 font-semibold">Numéro sécurité sociale :</p><p class="text-gray-800"><%= patient.getNumSecuSociale() %></p></div>
                    <div><p class="text-gray-600 font-semibold">Adresse :</p><p class="text-gray-800"><%= patient.getAdresse() != null ? patient.getAdresse() : "-" %></p></div>
                </div>
                <div class="mt-6">
                    <a href="${pageContext.request.contextPath}/generaliste/cree-consultation?patientId=${patient.id}" class="bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded-lg font-medium transition-all duration-200">
                        ➕ Créer une nouvelle consultation pour ce patient
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-red-500 font-semibold">Patient supprimé ou non disponible.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Signes Vitaux -->
    <div class="bg-white rounded-xl shadow-md p-6">
        <h2 class="text-2xl font-bold mb-4 text-gray-800">Signes Vitaux</h2>
        <c:choose>
            <c:when test="${not empty signesVitaux}">
                <div class="space-y-6">
                    <c:forEach var="sv" items="${signesVitaux}">
                        <div class="border border-gray-200 rounded-lg p-4 bg-gray-50">
                            <p class="text-gray-600 font-semibold mb-2">Mesuré le : <span class="text-gray-800">${sv.formattedDate}</span></p>
                            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                                <div><p class="text-gray-600">Température (°C)</p><p class="text-gray-800 font-medium">${sv.temperature}</p></div>
                                <div><p class="text-gray-600">Poids (kg)</p><p class="text-gray-800 font-medium">${sv.poids}</p></div>
                                <div><p class="text-gray-600">Taille (cm)</p><p class="text-gray-800 font-medium">${sv.taille}</p></div>
                                <div><p class="text-gray-600">Tension artérielle</p><p class="text-gray-800 font-medium">${sv.tensionArterielle}</p></div>
                                <div><p class="text-gray-600">Fréquence cardiaque (bpm)</p><p class="text-gray-800 font-medium">${sv.frequenceCardiaque}</p></div>
                                <div><p class="text-gray-600">Fréquence respiratoire</p><p class="text-gray-800 font-medium">${sv.frequenceRespiratoire}</p></div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <p class="text-gray-500">Aucun signe vital enregistré pour ce patient.</p>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>
