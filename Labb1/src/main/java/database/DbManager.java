package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    private static DbManager instance = null;
    private Connection con = null;

    private static DbManager getInstance() {
        if (instance == null)
            instance = new DbManager();
        return instance;
    }

    private DbManager() {
        String database = "DB_Webbshop";
        String userName = "admin";
        String password = "admin";
        //String server = "jdbc:mysql://130.229.177.107:3306/" + database + "?UseClientEnc=UTF8"; // Amanda
        String server = "jdbc:mysql://130.229.177.107:3006/" + database + "?UseClientEnc=UTF8";  // Betty

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, userName, password);
            //con = DriverManager.getConnection("jdbc:mysql://localhost/admin?user=root&password=admin");
            System.out.println("Connection successfull");
            con.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return getInstance().con;
    }
}