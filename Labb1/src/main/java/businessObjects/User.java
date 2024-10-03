package businessObjects;

import database.DbUser;
import java.util.regex.Pattern;

public class User {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private Authority authority;
    private String name;
    private String email;
    private String password;

    public static boolean isEmailValid(String isbn) {
        return EMAIL_PATTERN.matcher(isbn).matches();
    }

    /*Har kontakt mellan ob och db*/
    public static User getUserByEmail(String email) {
        User user = DbUser.searchUserByEmail(email);
        if (user == null)
            return null;
        return user;
    }

    public void createUser(User user) {
        DbUser.executeUserInsert(user);
    }

    public User(Authority authority, String name, String email, String password) {
        this.authority = authority;
        this.name = name;
        //if (!isEmailValid(email)) throw new IllegalArgumentException("Not a valid email-address.");
        this.email = email;
        this.password = password;
    }

    public Authority getAuthority() {
        return authority;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        //if (!isEmailValid(email)) throw new IllegalArgumentException("Not a valid email-address.");
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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