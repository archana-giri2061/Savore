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

@WebServlet(asyncSupported = true, urlPatterns = {"/LandingPage", "/"})
public class LandingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkDatabaseConnection(req);
        req.getRequestDispatcher("/WEB-INF/pages/LandingPage.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkDatabaseConnection(req);
        req.getRequestDispatcher("/WEB-INF/pages/LandingPage.jsp").forward(req, resp);
    }

    /**
     * Checks database connection and sets relevant request attributes.
     *
     * @param request HttpServletRequest to set attributes on
     */
    private void checkDatabaseConnection(HttpServletRequest request) {
        try (Connection connection = DbConfig.getDbConnection()) {
            System.out.println("✅ Successfully connected to database!");
            // Since getDbName() doesn't exist, use a hardcoded value or modify DbConfig to include it
            request.setAttribute("dbStatus", "Connected successfully to database: savore_db");

            // Perform a simple query to verify connection
            performSampleQuery(connection, request);

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            request.setAttribute("dbStatus", "Connection failed: " + e.getMessage());
        }
    }

    /**
     * Performs a sample query to demonstrate database connectivity.
     *
     * @param connection Database connection
     * @param request HttpServletRequest to set attributes on
     */
    private void performSampleQuery(Connection connection, HttpServletRequest request) {
        String query = "SELECT 1 AS test";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                request.setAttribute("queryStatus", "Sample query executed successfully");
                System.out.println("Sample query result: " + rs.getInt("test"));
            }
        } catch (SQLException e) {
            System.err.println("Sample query failed: " + e.getMessage());
            request.setAttribute("queryStatus", "Sample query failed: " + e.getMessage());
        }
    }
}