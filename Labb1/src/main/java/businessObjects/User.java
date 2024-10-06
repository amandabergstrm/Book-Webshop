package businessObjects;

import database.DbUser;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * The {@code User} class represents a user in the system. It contains user-related information such as authority,
 * name, email, and password. It also provides static methods for various operations like creating, retrieving,
 * updating, and deleting users.
 */
public class User {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private Authority authority;
    private String name;
    private String email;
    private String password;

    /**
     * Validates if the provided email matches the standard email format.
     *
     * @param email the email string to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Inserts a new user into the database.
     *
     * @param userObj the user object to be created
     */
    public void createUser(User userObj) {
        DbUser.executeUserInsert(userObj);
    }

    /**
     * Retrieves a user from the database by their email.
     *
     * @param email the email of the user to retrieve
     * @return the {@link User} object corresponding to the email, or null if not found
     */
    public static User getUserByEmail(String email) {
        return DbUser.searchUserByEmail(email);
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of {@link DbUser} objects representing all users
     */
    public static ArrayList<DbUser> importAllUsers() {
        return DbUser.importAllUsers();
    }

    /**
     * Updates an existing user in the database.
     *
     * @param userObj the user object containing updated information
     */
    public static void updateUser(User userObj) {
        DbUser.executeUserUpdate(userObj);
    }

    /**
     * Deletes a user from the database based on their email.
     *
     * @param email the email of the user to delete
     */
    public static void deleteUserByEmail(String email) {
        DbUser.executeUserRemove(email);
    }

    /**
     * Constructs a {@link User} with the provided authority, name, email, and password.
     *
     * @param authority the authority of the user
     * @param name      the name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     */
    protected User(Authority authority, String name, String email, String password) {
        this.authority = authority;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and setters

    /**
     * Returns the authority of the user.
     *
     * @return the user's authority
     */
    public Authority getAuthority() {
        return authority;
    }

    /**
     * Returns the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email of the user.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the authority of the user.
     *
     * @param authority the new authority to set
     */
    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the new email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string with user details
     */
    @Override
    public String toString() {
        return "User{" +
                "authority=" + authority +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}