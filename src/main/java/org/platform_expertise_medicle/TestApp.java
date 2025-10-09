package org.platform_expertise_medicle;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.model.User;


public class TestApp {
    public static void main(String[] args) {
        try (var sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            
            Transaction transaction = session.beginTransaction();

            User user = new User();
            user.setEmail("amine@gmail.com");
            user.setMotDePasse("123456");
            user.setRole(Role.INFERMIER);
            user.setNom("Amine");
            user.setPrenom("Amine");

            session.persist(user);
            transaction.commit();

            System.out.println("Utilisateur créé avec succès!");
            
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
