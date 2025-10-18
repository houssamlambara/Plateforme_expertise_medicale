<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Section Coût Total (à ajouter dans vos pages de consultation) -->
<div class="bg-gradient-to-r from-green-50 to-blue-50 rounded-xl shadow-lg p-6 border-2 border-green-200">
    <h3 class="text-xl font-bold text-gray-800 mb-4">💰 Détails des Coûts</h3>

    <div class="space-y-3">
        <!-- Coût de base -->
        <div class="flex justify-between items-center">
            <span class="text-gray-700 font-medium">Consultation de base :</span>
            <span class="text-green-600 font-bold text-lg">150.00 DH</span>
        </div>

        <!-- Coût actes techniques (calculé avec Stream API) -->
        <div class="flex justify-between items-center">
            <span class="text-gray-700 font-medium">Actes techniques :</span>
            <span class="text-blue-600 font-bold text-lg">
                <fmt:formatNumber value="${consultation.calculerCoutActesTechniques()}" pattern="#,##0.00"/> DH
            </span>
        </div>

        <hr class="border-gray-300">

        <!-- Coût total sans expertise (calculé avec Lambda) -->
        <div class="flex justify-between items-center bg-white p-3 rounded-lg">
            <span class="text-gray-800 font-semibold text-lg">Total (Consultation + Actes) :</span>
            <span class="text-green-700 font-bold text-2xl">
                <fmt:formatNumber value="${consultation.calculerCoutTotal()}" pattern="#,##0.00"/> DH
            </span>
        </div>

        <!-- Si expertise demandée -->
        <c:if test="${consultation.medecinSpecialiste != null && consultation.medecinSpecialiste.tarif != null}">
            <div class="flex justify-between items-center mt-2">
                <span class="text-gray-700 font-medium">+ Tarif expertise (${consultation.medecinSpecialiste.nom}) :</span>
                <span class="text-orange-600 font-bold text-lg">
                    <fmt:formatNumber value="${consultation.medecinSpecialiste.tarif}" pattern="#,##0.00"/> DH
                </span>
            </div>

            <hr class="border-gray-300">

            <!-- Coût total avec expertise -->
            <div class="flex justify-between items-center bg-blue-100 p-4 rounded-lg border-2 border-blue-300">
                <span class="text-blue-900 font-bold text-lg">💼 Total avec Expertise :</span>
                <span class="text-blue-700 font-bold text-3xl">
                    <fmt:formatNumber value="${consultation.calculerCoutTotalAvecExpertise()}" pattern="#,##0.00"/> DH
                </span>
            </div>

            <p class="text-xs text-gray-500 text-center mt-2">
                ✅ Calculs effectués avec Stream API et Lambda
            </p>
        </c:if>
    </div>
</div>

