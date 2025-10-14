package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.UserDAO;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.model.User;

import java.io.IOException;

@WebServlet("/auth/login")
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir tous les champs");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        if (userDAO.authenticate(email, password)) {
            var userOpt = userDAO.findByEmail(email);

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Stocker l'objet User complet dans la session
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userEmail", email);
                request.getSession().setAttribute("userName", user.getPrenom() + " " + user.getNom());
                request.getSession().setAttribute("userRole", user.getRole());
                request.getSession().setAttribute("isAuthenticated", true);

                // Déterminer le dashboard selon le rôle
                String dashboardUrl = "/infermier/dashboard.jsp"; // par défaut

                if (user.getRole() != null) {
                    switch (user.getRole()) {
                        case INFERMIER:
                            dashboardUrl = "/infirmier/dashboard";
                            break;
                        case GENERALISTE:
                            dashboardUrl = "/generaliste/dashboard";
                            break;
                        case SPECIALISTE:
                            dashboardUrl = "/specialiste/dashboard";
                            break;
                    }
                }

                response.sendRedirect(request.getContextPath() + dashboardUrl);
                return;
            }
        }

        request.setAttribute("error", "Email ou mot de passe incorrect");
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }
}
