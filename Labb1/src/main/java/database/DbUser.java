package database;

import businessObjects.*;
import java.sql.*;

public class DbUser extends User {
    private DbUser(Authority authority, String name, String email, String password) {
        super(authority, name, email, password);
    }

    public static User searchUserByEmail(String email) {
        DbUser foundUser = null;
        String query = "SELECT T_User.* FROM T_User WHERE T_User.email LIKE ?";

        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            // con.setAutoCommit(false);
            preparedStatement.setString(1, email + "%");
            System.out.println("Bettys fel");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String authorityString = resultSet.getString("authority");
                    Authority authority = Authority.valueOf(authorityString);
                    String name = resultSet.getString("name");
                    //email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    System.out.println("Seed2");
                    foundUser = new DbUser(authority, name, email, password);
                    System.out.println("Seed3");
                    System.out.println("User found: " + name);
                }
                else System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundUser;
    }
}