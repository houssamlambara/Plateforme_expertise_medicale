package org.platform_expertise_medicle.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.platform_expertise_medicle.DAO.PatientDAO;
import org.platform_expertise_medicle.model.Patient;

import java.io.IOException;
import java.util.List;

@WebServlet("/infirmier/liste-patient")
public class ListePatientServlet extends HttpServlet {
    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Récupérer la liste des patients depuis la base de données
            List<Patient> patients = patientDAO.findAll();

            // Ajouter la liste des patients à la requête
            request.setAttribute("patients", patients);

            // Transférer à la JSP
            request.getRequestDispatcher("/infirmier/liste-patient.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors de la récupération des patients : " + e.getMessage());
            request.getRequestDispatcher("/infirmier/dashboard.jsp")
                    .forward(request, response);
        }
    }
}