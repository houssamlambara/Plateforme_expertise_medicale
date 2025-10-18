package org.platform_expertise_medicle.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

/**
 * Test unitaire pour la classe Patient
 * Ce test vérifie les fonctionnalités de base de la classe Patient
 */
public class PatientTest {

    /**
     * Test 1 : Vérifie que le constructeur crée bien un patient avec toutes les informations
     */
    @Test
    public void testCreationPatient() {
        // ARRANGE (Préparer les données)
        String nom = "Dupont";
        String prenom = "Jean";
        Date dateNaissance = new Date();
        String numSecu = "1234567890123";
        String telephone = "0612345678";
        String adresse = "123 Rue de Paris, 75001 Paris";

        // ACT (Exécuter l'action à tester)
        Patient patient = new Patient(nom, prenom, dateNaissance, numSecu, telephone, adresse);

        // ASSERT (Vérifier les résultats)
        assertEquals(nom, patient.getNom(), "Le nom du patient devrait être 'Dupont'");
        assertEquals(prenom, patient.getPrenom(), "Le prénom du patient devrait être 'Jean'");
        assertEquals(dateNaissance, patient.getDateNaissance(), "La date de naissance devrait correspondre");
        assertEquals(numSecu, patient.getNumSecuSociale(), "Le numéro de sécurité sociale devrait correspondre");
        assertEquals(telephone, patient.getTelephone(), "Le téléphone devrait correspondre");
        assertEquals(adresse, patient.getAdresse(), "L'adresse devrait correspondre");
    }

    /**
     * Test 2 : Vérifie que les setters modifient bien les attributs du patient
     */
    @Test
    public void testModificationPatient() {
        // ARRANGE
        Patient patient = new Patient();
        String nouveauNom = "Martin";
        String nouveauPrenom = "Marie";

        // ACT
        patient.setNom(nouveauNom);
        patient.setPrenom(nouveauPrenom);

        // ASSERT
        assertEquals(nouveauNom, patient.getNom(), "Le nom devrait être modifié en 'Martin'");
        assertEquals(nouveauPrenom, patient.getPrenom(), "Le prénom devrait être modifié en 'Marie'");
    }
}

