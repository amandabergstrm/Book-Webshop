package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager {
    private static DbManager instance = null;
    private Connection con = null;

    private static DbManager getInstance() {
        if (instance == null)
            instance = new DbManager();
        return instance;
    }

    private DbManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/admin?user=root&password=admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return getInstance().con;
    }
}