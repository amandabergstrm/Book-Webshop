package businessObjects;

public class User {
    private Authority authority;
    private String name;
    private String email;
    private String password;

    public User(Authority authority, String name, String email, String password) {
        //kolla email
        this.authority = authority;
        this.name = name;
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
        // kolla email
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