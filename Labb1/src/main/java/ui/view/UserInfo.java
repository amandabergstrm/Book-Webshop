package ui.view;

import businessObjects.Authority;

/**
 * The UserInfo class represents a user's information including their authority,
 * name, email, and password.
 */
public class UserInfo {
    private Authority authority;
    private String name;
    private String email;
    private String password;

    /**
     * Constructs a UserInfo object with the specified authority, name, email, and password.
     *
     * @param authority the authority level of the user (e.g., ADMIN, CUSTOMER)
     * @param name      the name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     */
    public UserInfo(Authority authority, String name, String email, String password) {
        this.authority = authority;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the authority level of the user.
     *
     * @return the authority of the user
     */
    public Authority getAuthority() {
        return authority;
    }

    /**
     * Returns the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the authority level of the user.
     *
     * @param authority the new authority level to set
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
}
