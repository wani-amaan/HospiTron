package security;

public class AuthService {

    private final String adminUsername;
    private final String adminPassword;

    public AuthService() {
        this("AMAAN", "12345");
    }

    public AuthService(String adminUsername, String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return adminUsername.equals(username.trim()) && adminPassword.equals(password);
    }
}