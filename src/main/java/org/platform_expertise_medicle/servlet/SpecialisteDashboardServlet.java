package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.DemandeExpertiseDAO;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.DemandeExpertise;
import org.platform_expertise_medicle.model.MedecinSpecialiste;

import java.io.IOException;
import java.util.List;

@WebServlet("/specialiste/expertises-en-attente")
public class SpecialisteDashboardServlet extends HttpServlet {

    private final DemandeExpertiseDAO demandeExpertiseDao = new DemandeExpertiseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer l'email du spécialiste depuis la session
        String email = (String) request.getSession().getAttribute("userEmail");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Récupérer le spécialiste via le DAO
        MedecinSpecialiste specialiste = demandeExpertiseDao.findSpecialisteByEmail(email);
        if (specialiste == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Spécialiste non trouvé");
            return;
        }

        // Récupérer les demandes d'expertise en attente
        List<Consultation> demandes = demandeExpertiseDao.findEnAttenteBySpecialisteId(specialiste.getId());

        // Debug : afficher dans la console le nombre de demandes récupérées
        System.out.println("=== DEBUG CONSULTATIONS EN ATTENTE ===");
        System.out.println("Spécialiste connecté : " + specialiste.getNom() + " (id=" + specialiste.getId() + ")");
        System.out.println("Demandes trouvées : " + (demandes != null ? demandes.size() : 0));
        System.out.println("=== FIN DEBUG ===");

        // Passer les demandes à la JSP pour affichage
        request.setAttribute("demandes", demandes);
        request.getRequestDispatcher("/specialiste/dashboard.jsp").forward(request, response);
    }
}
