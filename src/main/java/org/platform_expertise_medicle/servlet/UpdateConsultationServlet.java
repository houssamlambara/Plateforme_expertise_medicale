package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.ConsultationDAO;
import org.platform_expertise_medicle.DAO.SigneVitauxDAO;
import org.platform_expertise_medicle.DAO.PatientDAO;
import org.platform_expertise_medicle.enums.StatutConsultation;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.SigneVitaux;
import org.platform_expertise_medicle.model.Patient;

import java.io.IOException;

@WebServlet("/generaliste/maj-consultation")
public class UpdateConsultationServlet extends HttpServlet {

    private final ConsultationDAO consultationDAO = new ConsultationDAO();
    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long consultationId = Long.parseLong(request.getParameter("consultationId"));
        long visiteId = Long.parseLong(request.getParameter("visiteId"));

        Patient patient = patientDAO.findById(patientId);
        SigneVitaux visite = signeVitauxDAO.findById(visiteId);

        if(patient == null || visite == null) {
            request.setAttribute("error", "Patient ou visite introuvable !");
            request.getRequestDispatcher("/generaliste/cree-consultation").forward(request, response);
            return;
        }

        Consultation consultation = consultationDAO.findById(consultationId);
        if(consultation == null) {
            request.setAttribute("error", "Consultation introuvable !");
            request.getRequestDispatcher("/generaliste/cree-consultation").forward(request, response);
            return;
        }

        consultation.setSymptomes(request.getParameter("symptomes"));
        consultation.setDiagnostic(request.getParameter("diagnostic"));
        consultation.setPrescription(request.getParameter("prescription"));
        consultation.setMotif(request.getParameter("motif"));
        consultation.setObservations(request.getParameter("observations"));

        if ("cloturer".equals(action)) {
            consultation.setStatut(StatutConsultation.TERMINEE);
            visite.setStatut("TERMINEE");
        } else if ("demanderSpecialiste".equals(action)) {
            consultation.setStatut(StatutConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            visite.setStatut("EN_COURS");
        }

        consultationDAO.update(consultation);
        signeVitauxDAO.update(visite);

        request.setAttribute("success", "Action effectuée avec succès !");
        request.setAttribute("consultation", consultation);
        request.setAttribute("patient", patient);
        request.setAttribute("visite", visite);
        request.getRequestDispatcher("/generaliste/cree-consultation.jsp").forward(request, response);
    }
}
