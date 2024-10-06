package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DbManager class is responsible for managing the database connection.
 * It implements a singleton pattern to ensure only one instance is created for the entire application.
 */
public class DbManager {
    private static DbManager instance = null;
    private Connection con = null;

    /**
     * Retrieves the singleton instance of DbManager.
     * If the instance does not exist, it creates one.
     *
     * @return the singleton instance of DbManager
     */
    private static DbManager getInstance() {
        if (instance == null)
            instance = new DbManager();
        return instance;
    }

    /**
     * Private constructor to establish a connection to the database.
     * This constructor is only called once via the singleton pattern.
     */
    private DbManager() {
        String database = "DB_Webbshop";
        String userName = "admin";
        String password = "admin";
        //String server = "jdbc:mysql://130.229.177.107:3306/" + database + "?UseClientEnc=UTF8";
        String server = "jdbc:mysql://130.229.177.107:3006/" + database + "?UseClientEnc=UTF8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            con = DriverManager.getConnection(server, userName, password); // Establish connection
            System.out.println("Connection successful");
            con.setAutoCommit(false); // Disable auto-commit for transaction management
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides the database connection object.
     * Ensures the connection is established via the singleton instance.
     *
     * @return the Connection object for the database
     */
    public static Connection getConnection() {
        return getInstance().con;
    }
}
