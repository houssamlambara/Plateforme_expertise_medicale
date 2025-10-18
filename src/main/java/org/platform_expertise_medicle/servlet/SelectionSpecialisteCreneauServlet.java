package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.platform_expertise_medicle.DAO.*;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.enums.StatutConsultation;
import org.platform_expertise_medicle.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/generaliste/specialiste-creneau")
public class SelectionSpecialisteCreneauServlet extends HttpServlet {

    private final MedecinSpecialisteDAO specialisteDAO = new MedecinSpecialisteDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final DisponibiliteDAO disponibiliteDAO = new DisponibiliteDAO();
    private final ConsultationDAO consultationDAO = new ConsultationDAO();
    private final UserDAO userDAO = new UserDAO();
    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Vérifier si c'est une consultation simple (sans spécialiste)
        String typeConsultation = request.getParameter("type");
        if ("simple".equals(typeConsultation)) {
            // Créer directement la consultation sans spécialiste
            creerConsultation(request, response, session);
            return;
        }

        // Récupérer le patient
        String patientIdStr = request.getParameter("patientId");
        if (patientIdStr == null) {
            patientIdStr = (String) session.getAttribute("patientIdForConsultation");
        }

        if (patientIdStr != null) {
            session.setAttribute("patientIdForConsultation", patientIdStr);
            long patientId = Long.parseLong(patientIdStr);
            Patient patient = patientDAO.findById(patientId);
            request.setAttribute("patient", patient);
        }

        // Vérifier si un spécialiste est sélectionné
        String specialisteIdStr = request.getParameter("specialisteId");

        if (specialisteIdStr != null && !specialisteIdStr.isEmpty()) {
            // ÉTAPE 2 : Afficher les créneaux du spécialiste
            long specialisteId = Long.parseLong(specialisteIdStr);
            MedecinSpecialiste specialiste = specialisteDAO.findById(specialisteId);

            if (specialiste == null) {
                session.setAttribute("error", "Spécialiste introuvable");
                response.sendRedirect(request.getContextPath() + "/generaliste/specialiste-creneau");
                return;
            }

            // Stocker le spécialiste en session
            session.setAttribute("selectedSpecialisteId", specialisteId);
            request.setAttribute("specialiste", specialiste);

            // Si une date est sélectionnée, récupérer les créneaux disponibles
            String dateStr = request.getParameter("date");
            if (dateStr != null && !dateStr.isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    List<Disponibilite> creneauxDisponibles = disponibiliteDAO.findCreneauxDisponibles(specialisteId, date);
                    request.setAttribute("creneauxDisponibles", creneauxDisponibles);
                    request.setAttribute("selectedDate", dateStr);
                } catch (Exception e) {
                    request.setAttribute("error", "Date invalide");
                }
            }
        } else {
            // ÉTAPE 1 : Afficher la liste des spécialistes
            List<MedecinSpecialiste> specialistes = specialisteDAO.findAll();
            request.setAttribute("specialistes", specialistes);
        }

        request.getRequestDispatcher("/generaliste/specialiste-creneau.jsp").forward(request, response);
    }

    private void creerConsultation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        try {
            User user = (User) session.getAttribute("user");

            // Récupérer les données depuis la session
            String patientIdStr = (String) session.getAttribute("patientIdForConsultation");
            if (patientIdStr == null) {
                session.setAttribute("error", "Données de consultation manquantes");
                response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
                return;
            }

            long patientId = Long.parseLong(patientIdStr);
            Patient patient = patientDAO.findById(patientId);
            MedecinGeneraliste generaliste = userDAO.findMedecinGeneralisteById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Médecin généraliste introuvable."));

            // Récupérer les données de la consultation
            String symptomes = (String) session.getAttribute("consultationSymptomes");
            String diagnostic = (String) session.getAttribute("consultationDiagnostic");
            String prescription = (String) session.getAttribute("consultationPrescription");
            String motif = (String) session.getAttribute("consultationMotif");
            String observations = (String) session.getAttribute("consultationObservations");
            String priorite = (String) session.getAttribute("consultationPriorite");

            // Récupérer les actes techniques
            String[] actesNoms = (String[]) session.getAttribute("actesNoms");
            String[] actesPrix = (String[]) session.getAttribute("actesPrix");
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

            // Créer la consultation simple (SANS spécialiste)
            Consultation consultation = new Consultation();
            consultation.setPatient(patient);
            consultation.setGeneraliste(generaliste);
            consultation.setMedecinSpecialiste(null); // PAS DE SPÉCIALISTE
            consultation.setSymptomes(symptomes);
            consultation.setDiagnostic(diagnostic);
            consultation.setPrescription(prescription);
            consultation.setMotif(motif);
            consultation.setObservations(observations);
            consultation.setPriorite(priorite != null ? priorite : "Normale");
            consultation.setStatut(StatutConsultation.TERMINEE); // Directement terminée

            actesTechniquesList.forEach(consultation::addActeTechnique);

            // Sauvegarder la consultation
            consultationDAO.save(consultation);

            // Mettre à jour le dernier signe vital
            SigneVitaux dernierSigne = signeVitauxDAO.findLastByPatientId(patientId);
            if (dernierSigne != null) {
                dernierSigne.setStatut("TRAITE");
                signeVitauxDAO.update(dernierSigne);
            }

            // Nettoyer la session
            session.removeAttribute("patientIdForConsultation");
            session.removeAttribute("consultationSymptomes");
            session.removeAttribute("consultationDiagnostic");
            session.removeAttribute("consultationPrescription");
            session.removeAttribute("consultationMotif");
            session.removeAttribute("consultationObservations");
            session.removeAttribute("consultationPriorite");
            session.removeAttribute("actesNoms");
            session.removeAttribute("actesPrix");

            // Rediriger avec succès
            session.setAttribute("success",
                    "✅ Consultation simple créée et clôturée avec succès pour " + patient.getPrenom() + " " + patient.getNom());
            response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");

        } catch (Exception e) {
            session.setAttribute("error", "Erreur lors de la création de la consultation : " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.GENERALISTE) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        try {
            // Réserver le créneau et créer la consultation
            String creneauIdStr = request.getParameter("creneauId");
            if (creneauIdStr == null || creneauIdStr.isEmpty()) {
                session.setAttribute("error", "Aucun créneau sélectionné");
                response.sendRedirect(request.getContextPath() + "/generaliste/specialiste-creneau");
                return;
            }

            long creneauId = Long.parseLong(creneauIdStr);
            Disponibilite creneau = disponibiliteDAO.findById(creneauId);

            if (creneau == null || !creneau.isDisponible()) {
                session.setAttribute("error", "Créneau non disponible");
                response.sendRedirect(request.getContextPath() + "/generaliste/specialiste-creneau");
                return;
            }

            // Récupérer les données de la consultation depuis la session
            String patientIdStr = (String) session.getAttribute("patientIdForConsultation");
            Long specialisteId = (Long) session.getAttribute("selectedSpecialisteId");

            if (patientIdStr == null || specialisteId == null) {
                session.setAttribute("error", "Données de consultation manquantes");
                response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
                return;
            }

            long patientId = Long.parseLong(patientIdStr);
            Patient patient = patientDAO.findById(patientId);
            MedecinSpecialiste specialiste = specialisteDAO.findById(specialisteId);
            MedecinGeneraliste generaliste = userDAO.findMedecinGeneralisteById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Médecin généraliste introuvable."));

            // Récupérer les données de la consultation
            String symptomes = (String) session.getAttribute("consultationSymptomes");
            String diagnostic = (String) session.getAttribute("consultationDiagnostic");
            String prescription = (String) session.getAttribute("consultationPrescription");
            String motif = (String) session.getAttribute("consultationMotif");
            String observations = (String) session.getAttribute("consultationObservations");
            String priorite = (String) session.getAttribute("consultationPriorite");

            // Récupérer les actes techniques
            String[] actesNoms = (String[]) session.getAttribute("actesNoms");
            String[] actesPrix = (String[]) session.getAttribute("actesPrix");
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

            // Créer la consultation
            Consultation consultation = new Consultation();
            consultation.setPatient(patient);
            consultation.setGeneraliste(generaliste);
            consultation.setMedecinSpecialiste(specialiste);
            consultation.setSymptomes(symptomes);
            consultation.setDiagnostic(diagnostic);
            consultation.setPrescription(prescription);
            consultation.setMotif(motif);
            consultation.setObservations(observations);
            consultation.setPriorite(priorite != null ? priorite : "Normale");
            consultation.setStatut(StatutConsultation.EN_ATTENTE_AVIS_SPECIALISTE);

            actesTechniquesList.forEach(consultation::addActeTechnique);

            // Sauvegarder la consultation
            consultationDAO.save(consultation);

            // Réserver le créneau
            boolean reserved = disponibiliteDAO.reserverCreneau(creneauId, consultation.getId());

            if (!reserved) {
                session.setAttribute("error", "Impossible de réserver le créneau");
                response.sendRedirect(request.getContextPath() + "/generaliste/specialiste-creneau");
                return;
            }

            // Mettre à jour le dernier signe vital
            SigneVitaux dernierSigne = signeVitauxDAO.findLastByPatientId(patientId);
            if (dernierSigne != null) {
                dernierSigne.setStatut("TRAITE");
                signeVitauxDAO.update(dernierSigne);
            }

            // Nettoyer la session
            session.removeAttribute("patientIdForConsultation");
            session.removeAttribute("selectedSpecialisteId");
            session.removeAttribute("consultationSymptomes");
            session.removeAttribute("consultationDiagnostic");
            session.removeAttribute("consultationPrescription");
            session.removeAttribute("consultationMotif");
            session.removeAttribute("consultationObservations");
            session.removeAttribute("consultationPriorite");
            session.removeAttribute("actesNoms");
            session.removeAttribute("actesPrix");

            // Rediriger avec succès
            session.setAttribute("success",
                    "✅ Consultation créée avec succès et créneau réservé le " + creneau.getDate() +
                            " à " + creneau.getCreneauFormate() + " avec Dr. " + specialiste.getNom());
            response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");

        } catch (Exception e) {
            session.setAttribute("error", "Erreur lors de la création de la consultation : " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/generaliste/dashboard");
        }
    }
}
