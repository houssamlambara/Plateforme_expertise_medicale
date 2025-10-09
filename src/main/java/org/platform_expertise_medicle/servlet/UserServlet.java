package org.platform_expertise_medicle.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.UserDAO;

import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/auth/login")
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir tous les champs");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        // Authentifier l'utilisateur
        if (userDAO.authenticate(email, password)) {
            // Récupérer l'utilisateur pour obtenir son rôle
            var userOpt = userDAO.findByEmail(email);

            if (userOpt.isPresent()) {
                var user = userOpt.get();

                // Créer une session
                request.getSession().setAttribute("userEmail", email);
                request.getSession().setAttribute("userName", user.getPrenom() + " " + user.getNom());
                request.getSession().setAttribute("userRole", user.getRole());
                request.getSession().setAttribute("isAuthenticated", true);

                // Rediriger vers le dashboard selon le rôle
                String dashboardUrl = "/infermier/dashboard.jsp"; // Par défaut

                if (user.getRole() != null) {
                    switch (user.getRole()) {
                        case INFERMIER:
                            dashboardUrl = "/infermier/dashboard.jsp";
                            break;
                        case GENERALISTE:
                            dashboardUrl = "/generaliste/dashboard.jsp";
                            break;
                        case SPECIALISTE:
                            dashboardUrl = "/specialiste/dashboard.jsp";
                            break;
                    }
                }

                response.sendRedirect(request.getContextPath() + dashboardUrl);
            }
        } else {
            request.setAttribute("error", "Email ou mot de passe incorrect");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }

}
