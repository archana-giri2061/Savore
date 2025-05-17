/**
 * DbConfig.java
 * This class is responsible for establishing a connection to the MySQL database.
 * It uses JDBC to connect to the specified database and provides a static method
 * for retrieving a connection instance.
 *
 * author: 23048573_ArchanaGiri
 */

package com.Savore.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

    // Database name
    private static final String DB_NAME = "savore_db";

    // JDBC URL with SSL disabled and timezone set
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";

    // MySQL database username
    private static final String USERNAME = "root";

    // MySQL database password
    private static final String PASSWORD = ""; // Replace with your actual MySQL password

    /**
     * This method loads the JDBC driver and establishes a connection
     * to the configured MySQL database.
     *
     * @return Connection object to the MySQL database
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC driver is not found
     */
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
        return DriverManager.getConnection(URL, USERNAME, PASSWORD); // Return DB connection
    }

    /**
     * Placeholder method for retrieving the database name.
     * Currently not implemented.
     *
     * @return null
     */
    public static String getDbName() {
        // TODO: Return actual database name if needed in future
        return null;
    }
}
