//package org.platform_expertise_medicle.servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.platform_expertise_medicle.model.User;
//
//import java.io.IOException;
//
//@WebServlet("/auth/register")
//public class RegisterServlet extends HttpServlet {
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Afficher la page d'inscription
//        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // Récupérer les données du formulaire
//        String nom = request.getParameter("nom");
//        String prenom = request.getParameter("prenom");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        String confirmPassword = request.getParameter("confirmPassword");
//
//        // Validation des champs
//        if (nom == null || nom.trim().isEmpty() ||
//            prenom == null || prenom.trim().isEmpty() ||
//            email == null || email.trim().isEmpty() ||
//            password == null || password.trim().isEmpty()) {
//
//            request.setAttribute("error", "Tous les champs sont obligatoires");
//            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
//            return;
//        }
//
//        // Vérifier que les mots de passe correspondent
//        if (!password.equals(confirmPassword)) {
//            request.setAttribute("error", "Les mots de passe ne correspondent pas");
//            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
//            return;
//        }
//
//        // Vérifier que l'email n'existe pas déjà
//        if (userDAO.findByEmail(email).isPresent()) {
//            request.setAttribute("error", "Cet email est déjà utilisé");
//            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            // Créer un nouvel utilisateur
//            User newUser = new User();
//            newUser.setNom(nom);
//            newUser.setPrenom(prenom);
//            newUser.setEmail(email);
//            newUser.setMotDePasse(password); // TODO: Hasher le mot de passe en production
//
//            // Sauvegarder dans la base de données
//            userDAO.save(newUser);
//
//            // Message de succès
//            request.setAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
//            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
//            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
//        }
//    }
//}
