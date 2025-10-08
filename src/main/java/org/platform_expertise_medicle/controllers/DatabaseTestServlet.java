package org.platform_expertise_medicle.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-db")
public class DatabaseTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Test de Connexion Base de Données</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }");
        out.println(".container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }");
        out.println(".success { color: #2ecc71; font-weight: bold; }");
        out.println(".error { color: #e74c3c; font-weight: bold; }");
        out.println(".info { background: #ecf0f1; padding: 15px; margin: 10px 0; border-radius: 4px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>🔍 Test de Connexion à la Base de Données</h1>");
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            out.println("<div class='info'>");
            out.println("<p>📡 Tentative de connexion à la base de données...</p>");
            out.println("</div>");
            
            // Créer l'EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("PlatformExpertiseMedicalePU");
            out.println("<p class='success'>✓ EntityManagerFactory créé avec succès!</p>");
            
            // Créer l'EntityManager
            em = emf.createEntityManager();
            out.println("<p class='success'>✓ EntityManager créé avec succès!</p>");
            
            // Tester les requêtes pour chaque entité
            em.getTransaction().begin();
            
            Long patientCount = em.createQuery("SELECT COUNT(p) FROM Patient p", Long.class).getSingleResult();
            out.println("<p class='success'>✓ Table 'patients' accessible - " + patientCount + " enregistrement(s)</p>");
            
            Long userCount = em.createQuery("SELECT COUNT(u) FROM Utilisateur u", Long.class).getSingleResult();
            out.println("<p class='success'>✓ Table 'utilisateurs' accessible - " + userCount + " enregistrement(s)</p>");
            
            Long roleCount = em.createQuery("SELECT COUNT(r) FROM Role r", Long.class).getSingleResult();
            out.println("<p class='success'>✓ Table 'roles' accessible - " + roleCount + " enregistrement(s)</p>");
            
            Long visiteCount = em.createQuery("SELECT COUNT(v) FROM Visite v", Long.class).getSingleResult();
            out.println("<p class='success'>✓ Table 'visites' accessible - " + visiteCount + " enregistrement(s)</p>");
            
            Long consultationCount = em.createQuery("SELECT COUNT(c) FROM Consultations c", Long.class).getSingleResult();
            out.println("<p class='success'>✓ Table 'consultations' accessible - " + consultationCount + " enregistrement(s)</p>");
            
            em.getTransaction().commit();
            
            out.println("<br><h2 class='success'>✅ Connexion à la base de données réussie!</h2>");
            out.println("<p>Toutes les tables sont accessibles et fonctionnelles.</p>");
            
        } catch (Exception e) {
            out.println("<h2 class='error'>❌ Erreur de connexion</h2>");
            out.println("<div class='info'>");
            out.println("<p><strong>Message d'erreur:</strong></p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            out.println("<p><strong>Type d'erreur:</strong> " + e.getClass().getName() + "</p>");
            out.println("</div>");
            
            out.println("<h3>Solutions possibles:</h3>");
            out.println("<ul>");
            out.println("<li>Vérifiez que MySQL est démarré</li>");
            out.println("<li>Vérifiez que la base de données 'platform_expertise_medicale' existe</li>");
            out.println("<li>Vérifiez les identifiants de connexion dans persistence.xml</li>");
            out.println("<li>Vérifiez que le driver MySQL est bien dans les dépendances</li>");
            out.println("</ul>");
            
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
