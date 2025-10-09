package org.platform_expertise_medicle.servlet; // ou votre package de servlets

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.PatientDAO;
import org.platform_expertise_medicle.DAO.SigneVitauxDAO;
import org.platform_expertise_medicle.model.Patient;
import org.platform_expertise_medicle.model.SigneVitaux;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/infirmier/ajouter-patient")
public class AddPatientServlet extends HttpServlet {

    private final PatientDAO patientDAO = new PatientDAO();
    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/infirmier/ajouter-patient.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            // On récupère les données du formulaire pour le patient
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            String numeroSecuriteSociale = request.getParameter("numeroSecuriteSociale");
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");

            if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty() || dateNaissanceStr == null || dateNaissanceStr.isEmpty() || numeroSecuriteSociale == null || numeroSecuriteSociale.isEmpty()) {
                request.setAttribute("error", "Les champs du patient marqués d'une * sont obligatoires.");
                doGet(request, response);
                return;
            }

            // Conversion de la date
            Date dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissanceStr);

            Patient nouveauPatient = new Patient(nom, prenom, dateNaissance, numeroSecuriteSociale, telephone, adresse);

            // On sauvegarde le patient
            patientDAO.save(nouveauPatient);

            // --- PARTIE 2 : RÉCUPÉRATION ET CRÉATION DES SIGNES VITAUX ---

            String temperatureStr = request.getParameter("temperature");
            String poidsStr = request.getParameter("poids");
            String tailleStr = request.getParameter("taille");
            String tensionArterielle = request.getParameter("tensionArterielle");
            String frequenceCardiaqueStr = request.getParameter("frequenceCardiaque");
            String frequenceRespiratoireStr = request.getParameter("frequenceRespiratoire");

            SigneVitaux nouveauxSignes = new SigneVitaux();

            // On remplit l'objet SigneVitaux en vérifiant que les valeurs ne sont pas vides
            if (temperatureStr != null && !temperatureStr.isEmpty()) nouveauxSignes.setTemperature(Double.parseDouble(temperatureStr));
            if (poidsStr != null && !poidsStr.isEmpty()) nouveauxSignes.setPoids(Double.parseDouble(poidsStr));
            if (tailleStr != null && !tailleStr.isEmpty()) nouveauxSignes.setTaille(Double.parseDouble(tailleStr));
            if (frequenceCardiaqueStr != null && !frequenceCardiaqueStr.isEmpty()) nouveauxSignes.setFrequenceCardiaque(Integer.parseInt(frequenceCardiaqueStr));
            if (frequenceRespiratoireStr != null && !frequenceRespiratoireStr.isEmpty()) nouveauxSignes.setFrequenceRespiratoire(Integer.parseInt(frequenceRespiratoireStr));

            nouveauxSignes.setTensionArterielle(tensionArterielle);

            nouveauxSignes.setPatient(nouveauPatient);

            signeVitauxDAO.save(nouveauxSignes);

            // Si tout s'est bien passé, on affiche le message de succès
            request.setAttribute("success", "Le patient et sa visite ont été enregistrés avec succès !");

        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète dans la console Tomcat (très utile pour déboguer)
            request.setAttribute("error", "Une erreur est survenue lors de l'enregistrement : " + e.getMessage());
        }

        // Dans tous les cas (succès ou erreur), on ré-affiche le formulaire
        doGet(request, response);
    }
}