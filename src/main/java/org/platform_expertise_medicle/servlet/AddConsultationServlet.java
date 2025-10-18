package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.platform_expertise_medicle.DAO.PatientDAO;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste/cree-consultation")
public class AddConsultationServlet extends HttpServlet {

    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer le patient si un ID est fourni
        String patientIdStr = request.getParameter("patientId");
        if (patientIdStr != null && !patientIdStr.isEmpty()) {
            try {
                long patientId = Long.parseLong(patientIdStr);
                Patient patient = patientDAO.findById(patientId);
                if (patient != null) {
                    request.setAttribute("patient", patient);
                }
            } catch (NumberFormatException e) {
                // Ignorer si l'ID n'est pas valide
            }
        }

        List<String> actesTechniques = List.of(
                "Radiographie",
                "Échographie",
                "IRM",
                "Électrocardiogramme",
                "DERMATOLOGIQUES (Laser)",
                "Fond d'œil",
                "Analyse de sang",
                "Analyse d'urine"
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

            // Récupération des champs du formulaire
            String symptomes = request.getParameter("symptomes");
            String diagnostic = request.getParameter("diagnostic");
            String prescription = request.getParameter("prescription");
            String motif = request.getParameter("motif");
            String observations = request.getParameter("observations");
            String priorite = request.getParameter("priorite");
            String typeConsultation = request.getParameter("typeConsultation"); // "simple" ou "specialiste"

            // Récupération des actes techniques
            String[] actesNoms = request.getParameterValues("acteNom[]");
            String[] actesPrix = request.getParameterValues("actePrix[]");

            // Stocker toutes les données en session
            session.setAttribute("patientIdForConsultation", patientIdStr);
            session.setAttribute("consultationSymptomes", symptomes);
            session.setAttribute("consultationDiagnostic", diagnostic);
            session.setAttribute("consultationPrescription", prescription);
            session.setAttribute("consultationMotif", motif);
            session.setAttribute("consultationObservations", observations);
            session.setAttribute("consultationPriorite", priorite != null ? priorite : "Normale");
            session.setAttribute("actesNoms", actesNoms);
            session.setAttribute("actesPrix", actesPrix);

            // Rediriger selon le type de consultation
            if ("specialiste".equals(typeConsultation)) {
                // Rediriger vers la sélection de spécialiste et créneau
                response.sendRedirect(request.getContextPath() + "/generaliste/specialiste-creneau?patientId=" + patientIdStr);
            } else {
                // Créer une consultation simple directement (sans spécialiste)
                // On utilise le même servlet mais sans spécialiste
                response.sendRedirect(request.getContextPath() + "/generaliste/specialiste-creneau?patientId=" + patientIdStr + "&type=simple");
            }

        } catch (Exception e) {
            request.setAttribute("error", "Erreur : " + e.getMessage());
            doGet(request, response);
        }
    }
}
