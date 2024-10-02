package database;

import businessObjects.*;
import java.sql.*;

public class DbUser extends User {
    private DbUser(Authority authority, String name, String email, String password) {
        super(authority, name, email, password);
    }

    public void insertUser(DbUser user) {
        String command = "INSERT INTO " + "T_User(authority, name, email, password) VALUES(?, ?, ?, ?)";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, user.getAuthority().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.execute();
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DbUser searchUserByEmail(String email) {
        DbUser foundUser = null;
        String query = "SELECT T_User.* FROM T_User WHERE T_User.email LIKE ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, email + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String authorityString = resultSet.getString("authority");
                    Authority authority = Authority.valueOf(authorityString);
                    String name = resultSet.getString("name");
                    email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    foundUser = new DbUser(authority, name, email, password);
                }
                else System.out.println("User not found");
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return foundUser;
    }
}