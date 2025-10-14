package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.platform_expertise_medicle.DAO.ConsultationDAO;
import org.platform_expertise_medicle.DAO.PatientDAO;
import org.platform_expertise_medicle.DAO.SigneVitauxDAO;
import org.platform_expertise_medicle.DAO.UserDAO;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.model.SigneVitaux;
import org.platform_expertise_medicle.model.User;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/generaliste/cree-consultation")
public class AddConsultationServlet extends HttpServlet {

    private final ConsultationDAO consultationDAO = new ConsultationDAO();
    private final UserDAO userDAO = new UserDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/generaliste/cree-consultation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            request.setAttribute("error", "Session expirée. Veuillez vous reconnecter.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.GENERALISTE) {
            request.setAttribute("error", "Aucun médecin généraliste connecté.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        try {
            // Récupérer le patient sélectionné depuis le lien
            String patientIdStr = request.getParameter("patientId");
            if (patientIdStr == null || patientIdStr.isEmpty()) {
                request.setAttribute("error", "Aucun patient sélectionné !");
                doGet(request, response);
                return;
            }

            long patientId = Long.parseLong(patientIdStr);
            Patient patient = patientDAO.findById(patientId);
            if (patient == null) {
                request.setAttribute("error", "Patient introuvable !");
                doGet(request, response);
                return;
            }

            // Récupérer les champs du formulaire
            String symptomes = request.getParameter("symptomes");
            String diagnostic = request.getParameter("diagnostic");
            String prescription = request.getParameter("prescription");
            String motif = request.getParameter("motif");
            String observations = request.getParameter("observations");

            // Créer la consultation
            Consultation consultation = new Consultation();
            consultation.setSymptomes(symptomes);
            consultation.setDiagnostic(diagnostic);
            consultation.setPrescription(prescription);
            consultation.setMotif(motif);
            consultation.setObservations(observations);
            consultation.setMedecinSpecialiste(null); // pas de spécialiste pour l'instant
            consultation.setGeneraliste(userDAO.findMedecinGeneralisteById(user.getId()).orElseThrow());

            consultationDAO.save(consultation);

            // Mettre à jour le statut du patient dans SigneVitaux
            SigneVitaux dernierSigne = signeVitauxDAO.findLastByPatientId(patientId);
            if (dernierSigne != null) {
                dernierSigne.setStatut("TRAITE");
                signeVitauxDAO.update(dernierSigne);
            }

            request.setAttribute("success", "Consultation créée avec succès pour le patient : " + patient.getPrenom() + " " + patient.getNom());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la création de la consultation : " + e.getMessage());
        }

        doGet(request, response);
    }
}
