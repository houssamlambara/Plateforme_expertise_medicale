<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plateforme d'Expertise Médicale - Test</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            border-bottom: 3px solid #3498db;
            padding-bottom: 10px;
        }
        h2 {
            color: #34495e;
            margin-top: 30px;
        }
        .status {
            display: inline-block;
            padding: 5px 15px;
            border-radius: 4px;
            font-weight: bold;
            margin-left: 10px;
        }
        .success {
            background-color: #2ecc71;
            color: white;
        }
        .info {
            background-color: #3498db;
            color: white;
        }
        .test-section {
            background-color: #ecf0f1;
            padding: 20px;
            margin: 15px 0;
            border-radius: 5px;
            border-left: 4px solid #3498db;
        }
        .test-item {
            margin: 10px 0;
            padding: 10px;
            background-color: white;
            border-radius: 3px;
        }
        a {
            color: #3498db;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .code {
            background-color: #2c3e50;
            color: #ecf0f1;
            padding: 10px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            margin: 10px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 15px 0;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #34495e;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🏥 Plateforme d'Expertise Médicale <span class="status success">✓ ACTIF</span></h1>
        
        <div class="test-section">
            <h2>📊 Informations Serveur</h2>
            <table>
                <tr>
                    <th>Paramètre</th>
                    <th>Valeur</th>
                </tr>
                <tr>
                    <td><strong>Date/Heure Serveur</strong></td>
                    <td><%= new java.util.Date() %></td>
                </tr>
                <tr>
                    <td><strong>Nom du Serveur</strong></td>
                    <td><%= request.getServerName() %></td>
                </tr>
                <tr>
                    <td><strong>Port</strong></td>
                    <td><%= request.getServerPort() %></td>
                </tr>
                <tr>
                    <td><strong>Contexte</strong></td>
                    <td><%= request.getContextPath() %></td>
                </tr>
                <tr>
                    <td><strong>Session ID</strong></td>
                    <td><%= session.getId() %></td>
                </tr>
                <tr>
                    <td><strong>Version JSP</strong></td>
                    <td><%= application.getMajorVersion() %>.<%= application.getMinorVersion() %></td>
                </tr>
            </table>
        </div>

        <div class="test-section">
            <h2>🔗 Tests de Navigation</h2>
            <div class="test-item">
                <strong>Page JSP actuelle :</strong> 
                <span class="status success">✓ Fonctionne</span>
                <p>URL : <code><%= request.getRequestURL() %></code></p>
            </div>
            
            <div class="test-item">
                <strong>API REST :</strong> 
                <a href="api/hello-world" target="_blank">Tester /api/hello-world</a>
            </div>
            
            <div class="test-item">
                <strong>Page JSF :</strong> 
                <a href="index.xhtml">Tester index.xhtml (JSF)</a>
            </div>
        </div>

        <div class="test-section">
            <h2>⚙️ Configuration JEE</h2>
            <table>
                <tr>
                    <th>Composant</th>
                    <th>Status</th>
                    <th>Description</th>
                </tr>
                <tr>
                    <td><strong>JSP</strong></td>
                    <td><span class="status success">✓</span></td>
                    <td>JavaServer Pages activé</td>
                </tr>
                <tr>
                    <td><strong>JSF</strong></td>
                    <td><span class="status info">?</span></td>
                    <td>Jakarta Faces configuré</td>
                </tr>
                <tr>
                    <td><strong>CDI</strong></td>
                    <td><span class="status info">?</span></td>
                    <td>Weld configuré</td>
                </tr>
                <tr>
                    <td><strong>JPA</strong></td>
                    <td><span class="status info">?</span></td>
                    <td>Hibernate configuré</td>
                </tr>
                <tr>
                    <td><strong>REST API</strong></td>
                    <td><span class="status info">?</span></td>
                    <td>Jersey configuré</td>
                </tr>
            </table>
        </div>

        <div class="test-section">
            <h2>📝 Commandes Utiles</h2>
            <div class="code">
                # Compiler le projet<br>
                mvn clean package<br><br>
                # Démarrer Tomcat<br>
                mvn tomcat7:run<br><br>
                # Arrêter Tomcat<br>
                Ctrl + C
            </div>
        </div>

        <div class="test-section">
            <h2>📂 Structure du Projet</h2>
            <ul>
                <li><strong>Controllers</strong> : org.platform_expertise_medicle.controllers</li>
                <li><strong>Services</strong> : org.platform_expertise_medicle.services</li>
                <li><strong>Entities</strong> : org.platform_expertise_medicle.entities</li>
                <li><strong>Repositories</strong> : org.platform_expertise_medicle.repositories</li>
            </ul>
        </div>

        <footer style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #ddd; text-align: center; color: #7f8c8d;">
            <p>© 2025 Plateforme d'Expertise Médicale - Tous droits réservés</p>
        </footer>
    </div>
</body>
</html>