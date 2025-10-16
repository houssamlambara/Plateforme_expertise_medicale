package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.DemandeExpertiseDAO;
import org.platform_expertise_medicle.model.DemandeExpertise;
import org.platform_expertise_medicle.model.MedecinSpecialiste;

import java.io.IOException;
import java.util.List;

@WebServlet("/specialiste/expertises-en-attente")
public class SpecialisteDashboardServlet extends HttpServlet {

    private final DemandeExpertiseDAO demandeDao = new DemandeExpertiseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute("userEmail");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        MedecinSpecialiste specialiste = demandeDao.findByEmail(email);
        if (specialiste == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Spécialiste non trouvé");
            return;
        }

        System.out.println(specialiste.getId());
        // Récupération des demandes en attente pour ce spécialiste
        List<DemandeExpertise> demandes = demandeDao.findEnAttenteBySpecialisteId(specialiste.getId());

        System.out.println("=== DEBUG DEMANDES EN ATTENTE ===");
        System.out.println("Spécialiste connecté : " + specialiste.getNom() + " (id=" + specialiste.getId() + ")");
        System.out.println("Nombre de demandes trouvées : " + demandes.size());
        System.out.println("=== FIN DEBUG ===");

        request.setAttribute("demandes", demandes);
        request.getRequestDispatcher("/specialiste/dashboard.jsp").forward(request, response);
    }
}
