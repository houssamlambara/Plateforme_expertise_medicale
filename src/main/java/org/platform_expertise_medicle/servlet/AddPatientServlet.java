package org.platform_expertise_medicle.servlet;

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
import java.time.LocalDateTime; // Importer LocalDateTime
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
            // --- PARTIE 1 : CRÉATION DU PATIENT ---
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String dateNaissanceStr = request.getParameter("dateNaissance");
            String numeroSecuriteSociale = request.getParameter("numeroSecuriteSociale");
            String telephone = request.getParameter("telephone");
            String adresse = request.getParameter("adresse");

            if (nom.isEmpty() || prenom.isEmpty() || dateNaissanceStr.isEmpty() || numeroSecuriteSociale.isEmpty()) {
                request.setAttribute("error", "Les champs du patient marqués d'une * sont obligatoires.");
                doGet(request, response);
                return;
            }

            Date dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissanceStr);
            Patient nouveauPatient = new Patient(nom, prenom, dateNaissance, numeroSecuriteSociale, telephone, adresse);
            patientDAO.save(nouveauPatient);

            // --- PARTIE 2 : CRÉATION DES SIGNES VITAUX (LA VISITE) ---
            SigneVitaux nouveauxSignes = new SigneVitaux();

            // Récupération des données du formulaire
            String temperatureStr = request.getParameter("temperature");
            String poidsStr = request.getParameter("poids");
            String tailleStr = request.getParameter("taille");
            String frequenceCardiaqueStr = request.getParameter("frequenceCardiaque");
            String frequenceRespiratoireStr = request.getParameter("frequenceRespiratoire");

            // Assignation des valeurs à l'objet
            if (temperatureStr != null && !temperatureStr.isEmpty()) nouveauxSignes.setTemperature(Double.parseDouble(temperatureStr));
            if (poidsStr != null && !poidsStr.isEmpty()) nouveauxSignes.setPoids(Double.parseDouble(poidsStr));
            if (tailleStr != null && !tailleStr.isEmpty()) nouveauxSignes.setTaille(Double.parseDouble(tailleStr));
            if (frequenceCardiaqueStr != null && !frequenceCardiaqueStr.isEmpty()) nouveauxSignes.setFrequenceCardiaque(Integer.parseInt(frequenceCardiaqueStr));
            if (frequenceRespiratoireStr != null && !frequenceRespiratoireStr.isEmpty()) nouveauxSignes.setFrequenceRespiratoire(Integer.parseInt(frequenceRespiratoireStr));
            nouveauxSignes.setTensionArterielle(request.getParameter("tensionArterielle"));

            // --- AJOUTS IMPORTANTS POUR LA FILE D'ATTENTE ---
            // 1. On lie la mesure au patient que l'on vient de créer
            nouveauxSignes.setPatient(nouveauPatient);
            // 2. On définit le statut pour la file d'attente
            nouveauxSignes.setStatut("EN_ATTENTE");
            // 3. On enregistre l'heure exacte de la mesure pour pouvoir trier la file
            nouveauxSignes.setDateMesure(LocalDateTime.now());
            // ----------------------------------------------------

            // On sauvegarde l'objet SigneVitaux complet
            signeVitauxDAO.save(nouveauxSignes);

            // Message de succès mis à jour
            request.setAttribute("success", "Le patient a été ajouté à la file d'attente avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors de l'enregistrement : " + e.getMessage());
        }

        doGet(request, response);
    }
}