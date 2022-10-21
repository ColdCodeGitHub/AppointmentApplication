/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LabUser
 */
public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//127.0.0.1/";
    private static final String databaseName = "sys";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "root"; // Username
    private static final String password = "ElectronicFunkMusic!12345"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * This method allows access to the database from the application.
     * @return 
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
       
        return connection;
    }

    /**
     *  This method closes the connection to the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
