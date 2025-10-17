package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.ConsultationDAO;
import org.platform_expertise_medicle.DAO.DemandeExpertiseDAO;
import org.platform_expertise_medicle.DAO.MedecinSpecialisteDAO;
import org.platform_expertise_medicle.enums.StatutConsultation;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.DemandeExpertise;
import org.platform_expertise_medicle.model.MedecinSpecialiste;

import java.io.IOException;

@WebServlet("/generaliste/demander-specialiste")
public class DemanderSpecialisteServlet extends HttpServlet {

    private final ConsultationDAO consultationDao = new ConsultationDAO();
    private final MedecinSpecialisteDAO specialisteDao = new MedecinSpecialisteDAO();
    private final DemandeExpertiseDAO demandeDao = new DemandeExpertiseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            long consultationId = Long.parseLong(request.getParameter("consultationId"));
            Consultation consultation = consultationDao.findById(consultationId);

            MedecinSpecialiste specialiste = specialisteDao.findById(1L); // Spécialiste unique

            DemandeExpertise demande = new DemandeExpertise();
            demande.setConsultation(consultation);
            demande.setSpecialiste(specialiste);
            demande.setStatut(StatutConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            demande.setQuestion(consultation.getMotif());
            demande.setPriorite(consultation.getPriorite());

            demandeDao.save(demande);

            consultation.setMedecinSpecialiste(specialiste);
            consultation.setStatut(StatutConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            consultationDao.update(consultation);

            request.setAttribute("success", "Demande envoyée au spécialiste !");
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/generaliste/cree-consultation");
    }
}
