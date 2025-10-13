package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.ConsultationDAO;
import org.platform_expertise_medicle.DAO.SigneVitauxDAO;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.SigneVitaux;

import java.io.IOException;

@WebServlet("/generaliste/cree-consultation")
public class AddConsultationServlet extends HttpServlet {

    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();
    private final ConsultationDAO consultationDAO = new ConsultationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID du patient si passé en param
        String patientIdStr = request.getParameter("patientId");
        if(patientIdStr != null) {
            long patientId = Long.parseLong(patientIdStr);
            SigneVitaux lastVisite = signeVitauxDAO.findLastByPatientId(patientId);
            request.setAttribute("selectedPatient", lastVisite != null ? lastVisite.getPatient() : null);
        }

        request.setAttribute("fileDAttente", signeVitauxDAO.findByStatut("EN_ATTENTE"));

        request.getRequestDispatcher("/generaliste/cree-consultation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long patientId = Long.parseLong(request.getParameter("patientId"));
            String symptomes = request.getParameter("symptomes");
            String diagnostic = request.getParameter("diagnostic");
            String prescription = request.getParameter("prescription");

            // Récupérer la dernière visite
            SigneVitaux visite = signeVitauxDAO.findLastByPatientId(patientId);

            if(visite != null) {
                // Créer consultation
                Consultation consultation = new Consultation();
                consultation.setVisite(visite);
                consultation.setSymptomes(symptomes);
                consultation.setDiagnostic(diagnostic);
                consultation.setPrescription(prescription);

                consultationDAO.save(consultation);

                // Mettre à jour le statut de la visite
                visite.setStatut("TRAITEE");
                signeVitauxDAO.update(visite);

                request.setAttribute("success", "Consultation créée avec succès !");
            } else {
                request.setAttribute("error", "Impossible de trouver la visite du patient.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la création de la consultation.");
        }

        // Recharge la page
        doGet(request, response);
    }
}
