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
            String database = "DB_Webbshop";
            String userName = "client";
            String password = "client";
            String server = "jdbc:mysql://localhost:3306/" + database + "?UseClientEnc=UTF8";
            con = DriverManager.getConnection(server, userName, password);
            //con = DriverManager.getConnection("jdbc:mysql://localhost/admin?user=root&password=admin");
            System.out.println("Connection successfull");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return getInstance().con;
    }
}