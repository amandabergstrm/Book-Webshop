package database;

import businessObjects.*;
import java.sql.*;
import java.util.ArrayList;

public class DbUser extends User {
    private DbUser(Authority authority, String name, String email, String password) {
        super(authority, name, email, password);
    }

    public static void executeUserInsert(User userObj) {
        String command = "INSERT INTO " + "T_User(authority, name, email, password) VALUES(?, ?, ?, ?)";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, userObj.getAuthority().toString());
            preparedStatement.setString(2, userObj.getName());
            preparedStatement.setString(3, userObj.getEmail());
            preparedStatement.setString(4, userObj.getPassword());
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
        DbUser dbUser = null;
        String query = "SELECT T_User.* FROM T_User WHERE T_User.email LIKE ?";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            preparedStatement.setString(1, email + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String authorityString = resultSet.getString("authority");
                    Authority authority = Authority.valueOf(authorityString);
                    String name = resultSet.getString("name");
                    email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    dbUser = new DbUser(authority, name, email, password);
                }
                if (dbUser == null) System.out.println("User not found");
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
        return dbUser;
    }

    public static ArrayList<DbUser> importAllUsers() {
        ArrayList<DbUser> dbUsers = new ArrayList<>();

        String query = "SELECT T_User.* FROM T_User";
        Connection con = DbManager.getConnection();

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            con.setAutoCommit(false);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String authorityString = resultSet.getString("authority");
                    Authority authority = Authority.valueOf(authorityString);
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    dbUsers.add(new DbUser(authority, name, email, password));
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            } e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbUsers;
    }

    public static void executeUserUpdate(User userObj) {
        String command = "UPDATE T_User SET T_User.authority = ? WHERE email = ?";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, userObj.getAuthority().toString());
            preparedStatement.setString(2, userObj.getEmail());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book details updated successfully.");
            } else {
                System.out.println("No book found with the given itemId.");
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            } e.printStackTrace();
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

    public static void executeUserRemove(String email) {
        String command = "DELETE FROM T_User WHERE email = ?";
        Connection con = DbManager.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            preparedStatement.setString(1, email);
            preparedStatement.execute();
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            } e.printStackTrace();
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
}