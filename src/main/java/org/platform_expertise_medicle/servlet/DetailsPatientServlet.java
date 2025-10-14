package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.model.SigneVitaux;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

@WebServlet("/infirmier/details")
public class DetailsPatientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/infirmier/dashboard");
            return;
        }

        long patientId = Long.parseLong(idParam);

        EntityManager em = null;
        try {
            em = org.platform_expertise_medicle.util.JpaUtil.getEntityManagerFactory().createEntityManager();

            // Récupérer le patient
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                response.sendRedirect(request.getContextPath() + "/infirmier/dashboard");
                return;
            }

            TypedQuery<SigneVitaux> query = em.createQuery(
                    "SELECT s FROM SigneVitaux s WHERE s.patient.id = :patientId ORDER BY s.dateMesure DESC",
                    SigneVitaux.class
            );
            query.setParameter("patientId", patientId);
            List<SigneVitaux> signesVitaux = query.getResultList();

            for (SigneVitaux sv : signesVitaux) {
                sv.prepareFormattedDate();
            }

            request.setAttribute("patient", patient);
            request.setAttribute("signesVitaux", signesVitaux);

            request.getRequestDispatcher("/infirmier/details.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur serveur.");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
