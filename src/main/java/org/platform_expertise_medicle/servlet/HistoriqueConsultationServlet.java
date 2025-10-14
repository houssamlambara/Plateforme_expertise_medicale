package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.platform_expertise_medicle.DAO.ConsultationDAO;
import org.platform_expertise_medicle.enums.Role;
import org.platform_expertise_medicle.model.Consultation;
import org.platform_expertise_medicle.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/generaliste/historique-consultations")
public class HistoriqueConsultationServlet extends HttpServlet {

    private final ConsultationDAO consultationDAO = new ConsultationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user.getRole() != Role.GENERALISTE) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // 🔹 Récupération de toutes les consultations du généraliste
        List<Consultation> consultations = consultationDAO.findByGeneralisteId(user.getId());
        request.setAttribute("consultations", consultations);

        request.getRequestDispatcher("/generaliste/historique-consultations.jsp").forward(request, response);
    }
}
