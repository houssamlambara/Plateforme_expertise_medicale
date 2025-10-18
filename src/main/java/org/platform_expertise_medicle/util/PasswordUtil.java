package org.platform_expertise_medicle.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitaire simple pour gérer les mots de passe avec BCrypt
 */
public class PasswordUtil {

    /**
     * Hache un mot de passe en clair
     * @param plainPassword Le mot de passe en clair
     * @return Le mot de passe haché
     */
    public static String hashPassword(String plainPassword) {
        // BCrypt génère automatiquement un "salt" et hache le mot de passe
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Vérifie si un mot de passe correspond au hash
     * @param plainPassword Le mot de passe en clair à vérifier
     * @param hashedPassword Le hash stocké en base de données
     * @return true si le mot de passe correspond, false sinon
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // BCrypt compare le mot de passe avec le hash
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

