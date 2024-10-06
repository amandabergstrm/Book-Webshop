package businessObjects;

import ui.view.UserInfo;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The {@code UserHandler} class provides methods for managing {@link User} objects.
 * It handles the creation, retrieval, updating, and deletion of users from the database,
 * and converts {@link User} objects to {@link UserInfo} for easier representation in the UI.
 */
public class UserHandler {

    /**
     * Creates a new user and inserts it into the database.
     *
     * @param userInfo the {@link UserInfo} object containing user data
     */
    public static void createUser(UserInfo userInfo) {
        User userObj = new User(userInfo.getAuthority(), userInfo.getName(), userInfo.getEmail(), userInfo.getPassword());
        userObj.createUser(userObj);
    }

    /**
     * Retrieves a user by email and converts the {@link User} object to {@link UserInfo}.
     *
     * @param email the email of the user to retrieve
     * @return the {@link UserInfo} object representing the user, or null if not found
     */
    public static UserInfo getUserByEmail(String email) {
        User userObj = User.getUserByEmail(email);
        if (userObj == null)
            return null;
        return new UserInfo(userObj.getAuthority(), userObj.getName(), userObj.getEmail(), userObj.getPassword());
    }

    /**
     * Retrieves all users from the database and converts them to {@link UserInfo} objects.
     *
     * @return a collection of {@link UserInfo} objects representing all users
     */
    public static Collection<UserInfo> getAllUsers() {
        Collection c = User.importAllUsers();
        ArrayList<UserInfo> usersInfo = new ArrayList<>();
        for (Object u : c) {
            User userObj = (User) u;
            usersInfo.add(new UserInfo(userObj.getAuthority(), userObj.getName(), userObj.getEmail(), userObj.getPassword()));
        }
        return usersInfo;
    }

    /**
     * Updates the details of an existing user.
     *
     * @param userInfo the {@link UserInfo} object containing updated user data
     */
    public static void updateUser(UserInfo userInfo) {
        User userObj = new User(userInfo.getAuthority(), userInfo.getName(), userInfo.getEmail(), userInfo.getPassword());
        User.updateUser(userObj);
    }

    /**
     * Deletes a user from the database by their email.
     *
     * @param email the email of the user to delete
     */
    public static void deleteUserByEmail(String email) {
        User.deleteUserByEmail(email);
    }
}