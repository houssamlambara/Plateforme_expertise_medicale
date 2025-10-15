package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.SigneVitaux;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste/details-consultation")
public class DetailsConsultationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("consultationId");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
            return;
        }

        long consultationId;
        try {
            consultationId = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
            return;
        }

        EntityManager em = null;
        try {
            em = org.platform_expertise_medicle.util.JpaUtil.getEntityManagerFactory().createEntityManager();

            // Récupérer la consultation
            Consultation consultation = em.find(Consultation.class, consultationId);
            if (consultation == null) {
                response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
                return;
            }

            // Récupérer les signes vitaux
            List<SigneVitaux> signesVitaux = null;
            if (consultation.getPatient() != null) {
                TypedQuery<SigneVitaux> query = em.createQuery(
                        "SELECT s FROM SigneVitaux s WHERE s.patient.id = :patientId ORDER BY s.dateMesure DESC",
                        SigneVitaux.class
                );
                query.setParameter("patientId", consultation.getPatient().getId());
                signesVitaux = query.getResultList();
                for (SigneVitaux sv : signesVitaux) sv.prepareFormattedDate();
            }

            // Mettre les objets en attributs
            request.setAttribute("consultation", consultation);
            request.setAttribute("signesVitaux", signesVitaux);

            // Forward vers JSP détails
            request.getRequestDispatcher("/generaliste/details-consultation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur serveur.");
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}
