package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.platform_expertise_medicle.DAO.SigneVitauxDAO;
import org.platform_expertise_medicle.model.SigneVitaux;

import java.io.IOException;
import java.util.List;

@WebServlet("/infirmier/dashboard")
public class DashboardInfirmierServlet extends HttpServlet {

    private final SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        List<SigneVitaux> patientsEnAttente = signeVitauxDAO.findByStatut("EN_ATTENTE");

        for (SigneVitaux sv : patientsEnAttente) {
            sv.prepareFormattedDate();
        }

        request.setAttribute("patientsEnAttente", patientsEnAttente);

        request.getRequestDispatcher("/infirmier/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
