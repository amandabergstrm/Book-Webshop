package businessObjects;

import ui.UserInfo;
import java.util.ArrayList;
import java.util.Collection;

public class UserHandler {
    public static void createUser(UserInfo userInfo) {
        User userObj = new User(userInfo.getAuthority(), userInfo.getName(), userInfo.getEmail(), userInfo.getPassword());
        userObj.createUser(userObj);
    }

    public static UserInfo getUserByEmail(String email) {
        User userObj = User.getUserByEmail(email);
        if (userObj == null)
            return null;
        return new UserInfo(userObj.getAuthority(), userObj.getName(), userObj.getEmail(), userObj.getPassword());
    }

    public static Collection<UserInfo> getAllUsers() {
        Collection c = User.importAllUsers();
        ArrayList<UserInfo> usersInfo = new ArrayList<>();
        for (Object u : c) {
            User userObj = (User) u;
            usersInfo.add(new UserInfo(userObj.getAuthority(), userObj.getName(), userObj.getEmail(), userObj.getPassword()));
        }
        return usersInfo;
    }

    public static void updateUser(UserInfo userInfo) {
        User userObj = new User(userInfo.getAuthority(), userInfo.getName(), userInfo.getEmail(), userInfo.getPassword());
        User.updateUser(userObj);
    }

    public static void deleteUserByEmail(String email) {
        User.deleteUserByEmail(email);
    }
}