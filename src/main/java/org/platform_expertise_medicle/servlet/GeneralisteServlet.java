package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.model.SigneVitaux;
import org.platform_expertise_medicle.DAO.SigneVitauxDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste/dashboard")
public class GeneralisteServlet extends HttpServlet {

    private SigneVitauxDAO signeVitauxDAO = new SigneVitauxDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("userEmail") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        List<SigneVitaux> fileDAttente = signeVitauxDAO.findPatientsEnAttente();
        for (SigneVitaux sv : fileDAttente) {
            sv.prepareFormattedDate();
        }

        request.setAttribute("fileDAttente", fileDAttente);
        request.getRequestDispatcher("/generaliste/dashboard.jsp").forward(request, response);
    }
}
