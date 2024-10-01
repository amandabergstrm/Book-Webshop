package businessObjects;

import ui.UserInfo;

/*
Kontakt mellan ui och bo.
 */
public class UserHandler {
    public static UserInfo getUserByEmail(String email) {
        User foundUser = User.searchUserByEmail(email);
        return new UserInfo(foundUser.getAuthority(), foundUser.getName(), foundUser.getEmail(), foundUser.getPassword());
    }
}