package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.PatientDAO;
import org.platform_expertise_medicle.model.Patient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/infirmier/modifier")
public class UpdatePatientServlet extends HttpServlet {

    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long patientId = Long.parseLong(request.getParameter("id"));
        Patient patient = patientDAO.findById(patientId);

        request.setAttribute("patient", patient);
        request.getRequestDispatcher("/infirmier/modifier-patient.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Récupérer l'ID du patient à modifier
            long id = Long.parseLong(request.getParameter("id"));

            // 2. CHERCHER le patient existant dans la base de données
            Patient patientAModifier = patientDAO.findById(id);

            if (patientAModifier != null) {
                // 3. Récupérer les nouvelles données du formulaire
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                Date dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateNaissance"));
                String numSecuSociale = request.getParameter("numeroSecuriteSociale");
                String telephone = request.getParameter("telephone");
                String adresse = request.getParameter("adresse");

                // 4. METTRE À JOUR les champs de l'objet existant
                patientAModifier.setNom(nom);
                patientAModifier.setPrenom(prenom);
                patientAModifier.setDateNaissance(dateNaissance);
                patientAModifier.setNumSecuSociale(numSecuSociale);
                patientAModifier.setTelephone(telephone);
                patientAModifier.setAdresse(adresse);

                patientDAO.update(patientAModifier);
            }

            // On redirige vers la liste des patients
            response.sendRedirect(request.getContextPath() + "/infirmier/liste-patient");

        } catch (Exception e) {
            e.printStackTrace(); // REGARDEZ LA CONSOLE TOMCAT POUR VOIR L'ERREUR EXACTE !
            // Gérer l'erreur, par exemple en renvoyant vers une page d'erreur
            response.sendRedirect(request.getContextPath() + "/infirmier/liste-patient?error=updateFailed");
        }
    }
}