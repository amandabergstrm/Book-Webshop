package database;

import businessObjects.*;
import java.sql.*;

public class DbUser extends User {
    private DbUser(Authority authority, String name, String email, String password) {
        super(authority, name, email, password);
    }

    public static User getUser(String email) {
        User foundUser = null;
        String query = "SELECT T_User.* FROM T_User WHERE T_User.email LIKE ?";

        Connection con = DbManager.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
           // con.setAutoCommit(false);
            preparedStatement.setString(1, email + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String authorityString = resultSet.getString("Authority");
                    Authority authority = Authority.valueOf(authorityString);
                    String name = resultSet.getString("Name");
                    //email = resultSet.getString("Email");
                    String password = resultSet.getString("Password");

                    foundUser = new User(authority, name, email, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundUser;
    }
}