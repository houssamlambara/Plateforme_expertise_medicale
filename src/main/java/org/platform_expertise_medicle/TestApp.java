package org.platform_expertise_medicle;

import org.platform_expertise_medicle.DAO.UserDAO;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.model.Infirmier;

public class TestApp {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        try {
            Infirmier user = new Infirmier();
            user.setEmail("hatim@gmail.com");
            user.setMotDePasse("123");
            user.setRole(Role.SPECIALISTE);                // INFERMIER, GENERALISTE, SPECIALISTE
            user.setNom("Hatim");
            user.setPrenom("Hatim");

            userDAO.save(user);

            System.out.println("Utilisateur créé avec succès!");
            System.out.println("Email: " + user.getEmail());
            System.out.println("Mot de passe: password123");
            System.out.println("Rôle: " + user.getRole());

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

