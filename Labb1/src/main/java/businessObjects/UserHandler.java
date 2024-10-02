package businessObjects;

import ui.UserInfo;

/*
Kontakt mellan ui och bo.
 */
public class UserHandler {
    public static UserInfo getUserByEmail(String email) {
        User foundUser = User.getUserByEmail(email);
        if (foundUser == null)
            return null;
        return new UserInfo(foundUser.getAuthority(), foundUser.getName(), foundUser.getEmail(), foundUser.getPassword());
    }

    public static void createUser(UserInfo user) {
        User userObj = new User(user.getAuthority(), user.getName(), user.getEmail(), user.getPassword());
        userObj.createUser(userObj);
    }
}