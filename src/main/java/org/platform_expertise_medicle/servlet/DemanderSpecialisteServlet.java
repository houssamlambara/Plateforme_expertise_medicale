package org.platform_expertise_medicle.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.ConsultationDAO;
import org.platform_expertise_medicle.DAO.DemandeExpertiseDAO;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.DemandeExpertise;
import org.platform_expertise_medicle.model.MedecinSpecialiste;
import org.platform_expertise_medicle.util.JpaUtil;

import java.io.IOException;

@WebServlet("/generaliste/demander-specialiste")
public class DemanderSpecialisteServlet extends HttpServlet {

    private final ConsultationDAO consultationDao = new ConsultationDAO();
    private final DemandeExpertiseDAO demandeDao = new DemandeExpertiseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            long consultationId = Long.parseLong(request.getParameter("consultationId"));
            long specialisteId = Long.parseLong(request.getParameter("specialisteId"));

            Consultation consultation = consultationDao.findById(consultationId);
            MedecinSpecialiste specialiste = demandeDao.findById(specialisteId);

            if (consultation == null || specialiste == null) {
                System.out.println("❌ Consultation ou spécialiste introuvable !");
                response.sendRedirect(request.getContextPath() + "/generaliste/cree-consultation");
                return;
            }

            // Création d'une demande d'expertise
            DemandeExpertise demande = new DemandeExpertise();
            demande.setConsultation(consultation);
            demande.setSpecialiste(specialiste);
            demande.setStatut("En attente");
            demande.setQuestion(consultation.getMotif());

            // Persistance de la demande
            EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(demande);
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                e.printStackTrace();
            } finally {
                em.close();
            }

            // Mise à jour de la consultation
            consultation.setMedecinSpecialiste(specialiste);
            consultation.setStatut("EN_ATTENTE_AVIS_SPECIALISTE");
            consultationDao.update(consultation);

            System.out.println("✅ Demande créée pour le spécialiste " + specialiste.getNom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/generaliste/cree-consultation");
    }
}
