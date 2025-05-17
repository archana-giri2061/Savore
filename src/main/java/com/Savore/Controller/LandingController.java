package com.Savore.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Savore.config.DbConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller for handling the root ("/") and landing page ("/LandingPage").
 * It verifies the database connection and forwards to the LandingPage.jsp.
 * 
 * author: 23048573_ArchanaGiri
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/LandingPage", "/"})
public class LandingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to "/" or "/LandingPage".
     * Verifies DB connection and forwards to LandingPage.jsp.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkDatabaseConnection(req);
        req.getRequestDispatcher("/WEB-INF/pages/LandingPage.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests to "/" or "/LandingPage".
     * Reuses the GET logic to validate DB connection.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkDatabaseConnection(req);
        req.getRequestDispatcher("/WEB-INF/pages/LandingPage.jsp").forward(req, resp);
    }

    /**
     * Checks if the application can connect to the database.
     * Sets success/failure messages as request attributes.
     */
    private void checkDatabaseConnection(HttpServletRequest request) {
        try (Connection connection = DbConfig.getDbConnection()) {
            System.out.println("Successfully connected to database.");
            request.setAttribute("dbStatus", "Connected successfully to database: savore_db");

            // Optional: Perform a sample query to confirm operational DB access
            performSampleQuery(connection, request);

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            request.setAttribute("dbStatus", "Connection failed: " + e.getMessage());
        }
    }

    /**
     * Executes a simple query (`SELECT 1`) to validate DB operations.
     * Adds query status as a request attribute for UI use.
     */
    private void performSampleQuery(Connection connection, HttpServletRequest request) {
        String query = "SELECT 1 AS test";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int result = rs.getInt("test");
                request.setAttribute("queryStatus", "Sample query executed successfully");
                System.out.println("Sample query result: " + result);
            }

        } catch (SQLException e) {
            System.err.println("Sample query failed: " + e.getMessage());
            request.setAttribute("queryStatus", "Sample query failed: " + e.getMessage());
        }
    }
}
