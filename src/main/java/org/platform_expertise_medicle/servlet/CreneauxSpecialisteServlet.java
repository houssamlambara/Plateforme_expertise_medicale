package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.platform_expertise_medicle.DAO.DisponibiliteDAO;
import org.platform_expertise_medicle.DAO.MedecinSpecialisteDAO;
import org.platform_expertise_medicle.model.Disponibilite;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/specialiste/creneaux")
public class CreneauxSpecialisteServlet extends HttpServlet {

    private final DisponibiliteDAO disponibiliteDAO = new DisponibiliteDAO();
    private final MedecinSpecialisteDAO specialisteDAO = new MedecinSpecialisteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Récupérer le spécialiste
        MedecinSpecialiste specialiste = specialisteDAO.findById(user.getId());
        if (specialiste == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Spécialiste non trouvé");
            return;
        }

        // Récupérer tous les créneaux du spécialiste
        List<Disponibilite> creneaux = disponibiliteDAO.findBySpecialiste(specialiste.getId());

        request.setAttribute("creneaux", creneaux);
        request.setAttribute("specialiste", specialiste);
        request.getRequestDispatcher("/specialiste/creneaux.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String action = request.getParameter("action");

        if ("generer".equals(action)) {
            // Générer les créneaux pour une date
            String dateStr = request.getParameter("date");

            try {
                LocalDate date = LocalDate.parse(dateStr);
                MedecinSpecialiste specialiste = specialisteDAO.findById(user.getId());

                if (specialiste != null) {
                    disponibiliteDAO.genererCreneauxJournee(specialiste, date);
                    request.getSession().setAttribute("success", "Créneaux générés avec succès pour le " + date);
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Erreur lors de la génération des créneaux : " + e.getMessage());
            }
        } else if ("creer-manuel".equals(action)) {
            // Créer un créneau manuel
            String dateStr = request.getParameter("date");
            String heureDebutStr = request.getParameter("heureDebut");
            String heureFinStr = request.getParameter("heureFin");

            try {
                LocalDate date = LocalDate.parse(dateStr);
                LocalTime heureDebut = LocalTime.parse(heureDebutStr);
                LocalTime heureFin = LocalTime.parse(heureFinStr);

                // Vérifier que l'heure de fin est après l'heure de début
                if (heureFin.isBefore(heureDebut) || heureFin.equals(heureDebut)) {
                    request.getSession().setAttribute("error", "L'heure de fin doit être après l'heure de début");
                } else {
                    MedecinSpecialiste specialiste = specialisteDAO.findById(user.getId());
                    if (specialiste != null) {
                        disponibiliteDAO.creerCreneauManuel(specialiste, date, heureDebut, heureFin);
                        request.getSession().setAttribute("success", "Créneau créé avec succès");
                    }
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Erreur : " + e.getMessage());
            }
        } else if ("supprimer".equals(action)) {
            // Supprimer un créneau
            String creneauIdStr = request.getParameter("creneauId");

            try {
                Long creneauId = Long.parseLong(creneauIdStr);
                boolean deleted = disponibiliteDAO.delete(creneauId);

                if (deleted) {
                    request.getSession().setAttribute("success", "Créneau supprimé avec succès");
                } else {
                    request.getSession().setAttribute("error", "Impossible de supprimer ce créneau (il est peut-être déjà réservé)");
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
            }
        }

        // Rediriger vers la page GET
        response.sendRedirect(request.getContextPath() + "/specialiste/creneaux");
    }
}
