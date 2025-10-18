package org.platform_expertise_medicle.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

/**
 * Test unitaire pour la classe SigneVitaux
 * Ce test vérifie la méthode de formatage de date
 */
public class SigneVitauxTest {


    @Test
    public void testFormatageDate() {
        // ARRANGE
        SigneVitaux signes = new SigneVitaux();
        // On crée une date précise : 18 octobre 2025 à 14h30
        LocalDateTime dateTest = LocalDateTime.of(2025, 10, 18, 14, 30);
        signes.setDateMesure(dateTest);

        // ACT
        String dateFormatee = signes.getFormattedDate();

        // ASSERT
        assertEquals("18/10/2025 14:30", dateFormatee,
                    "La date devrait être formatée en 'dd/MM/yyyy HH:mm'");
    }


    @Test
    public void testFormatageDateNulle() {
        // ARRANGE
        SigneVitaux signes = new SigneVitaux();
        signes.setDateMesure(null);

        // ACT
        String dateFormatee = signes.getFormattedDate();

        // ASSERT
        assertEquals("", dateFormatee,
                    "La méthode devrait retourner une chaîne vide si la date est nulle");
    }
}
