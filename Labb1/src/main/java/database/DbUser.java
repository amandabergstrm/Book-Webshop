package database;

import businessObjects.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * The DbUser class handles database operations related to users, such as inserting,
 * updating, and retrieving users from the database. It extends the User class.
 */
public class DbUser extends User {

    /**
     * Constructs a DbUser object with the specified authority, name, email, and password.
     *
     * @param authority the authority level of the user (e.g., ADMIN, CUSTOMER)
     * @param name      the name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     */
    private DbUser(Authority authority, String name, String email, String password) {
        super(authority, name, email, password);
    }

    /**
     * Inserts a new user into the T_User table in the database.
     *
     * @param userObj the User object containing the user details to insert
     */
    public static void executeUserInsert(User userObj) {
        String command = "INSERT INTO T_User(authority, name, email, password) VALUES(?, ?, ?, ?)";
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

    /**
     * Searches for a user in the database by their email.
     *
     * @param email the email of the user to search for
     * @return a DbUser object if the user is found, otherwise null
     */
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

    /**
     * Retrieves all users from the T_User table in the database.
     *
     * @return a list of DbUser objects representing all users
     */
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
        return dbUsers;
    }

    /**
     * Updates the authority of a user in the T_User table in the database.
     *
     * @param userObj the User object containing the updated user details
     */
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
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("No user found with the given email.");
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
    }

    /**
     * Removes a user from the T_User table in the database by their email.
     *
     * @param email the email of the user to be removed
     */
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
}
