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
import org.platform_expertise_medicle.model.ActeTechnique;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.MedecinGeneraliste;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.model.SigneVitaux;
import org.platform_expertise_medicle.model.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/generaliste/cree-consultation")
public class AddConsultationServlet extends HttpServlet {

    private final ConsultationDAO consultationDAO = new ConsultationDAO();
    private final UserDAO userDAO = new UserDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> actesTechniques = List.of(
                "Radiographie",
                "Échographie",
                "IRM",
                "Électrocardiogramme",
                "DERMATOLOGIQUES (Laser)",
                "Fond d'œil",
                "Analyse de sang",
                "Analyse d’urine"
        );
        request.setAttribute("actesTechniques", actesTechniques);

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

            // Champs du formulaire
            String symptomes = request.getParameter("symptomes");
            String diagnostic = request.getParameter("diagnostic");
            String prescription = request.getParameter("prescription");
            String motif = request.getParameter("motif");
            String observations = request.getParameter("observations");
            String priorite = request.getParameter("priorite"); // par ex. "Urgente", "Normale", "Basse"

            // Récupération des actes techniques
            String[] actesNoms = request.getParameterValues("acteNom[]");
            String[] actesPrix = request.getParameterValues("actePrix[]");
            List<ActeTechnique> actesTechniquesList = new ArrayList<>();

            if (actesNoms != null && actesPrix != null) {
                for (int i = 0; i < actesNoms.length; i++) {
                    String nom = actesNoms[i];
                    if (nom != null && !nom.isEmpty()) {
                        BigDecimal prix = BigDecimal.ZERO;
                        try {
                            prix = new BigDecimal(actesPrix[i]);
                        } catch (NumberFormatException ignored) { }
                        actesTechniquesList.add(new ActeTechnique(nom, prix));
                    }
                }
            }

            // Création de la consultation
            Consultation consultation = new Consultation();
            consultation.setPatient(patient);
            consultation.setSymptomes(symptomes);
            consultation.setDiagnostic(diagnostic);
            consultation.setPrescription(prescription);
            consultation.setMotif(motif);
            consultation.setObservations(observations);
            consultation.setPriorite(priorite != null ? priorite : "Normale");

            // Ajouter les actes techniques
            for (ActeTechnique acte : actesTechniquesList) {
                consultation.addActeTechnique(acte);
            }

            MedecinGeneraliste generaliste = userDAO.findMedecinGeneralisteById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Médecin généraliste introuvable."));
            consultation.setGeneraliste(generaliste);
            consultation.setMedecinSpecialiste(null);

            consultationDAO.save(consultation);

            // Mettre à jour le dernier signe vital
            SigneVitaux dernierSigne = signeVitauxDAO.findLastByPatientId(patientId);
            if (dernierSigne != null) {
                dernierSigne.setStatut("TRAITE");
                signeVitauxDAO.update(dernierSigne);
                request.setAttribute("visite", dernierSigne);
            }

            request.setAttribute("patient", patient);
            request.setAttribute("consultation", consultation);
            request.setAttribute("success",
                    "Consultation créée avec succès pour le patient : " +
                            patient.getPrenom() + " " + patient.getNom());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la création de la consultation : " + e.getMessage());
        }

        doGet(request, response);
    }
}
