package security;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Modern Java security authentication service with credential records.
 */
public final class AuthService {

    public record AdminCredentials(String username, String password) {}

    private final AdminCredentials credentials;

    public AuthService() {
        this(new AdminCredentials("AMAAN", "12345"));
    }

    public AuthService(String username, String password) {
        this(new AdminCredentials(username, password));
    }

    public AuthService(AdminCredentials credentials) {
        this.credentials = credentials;
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        // Constant-time comparison to prevent timing attacks
        boolean usernameMatches = credentials.username().equalsIgnoreCase(username.strip());
        boolean passwordMatches = MessageDigest.isEqual(
                credentials.password().getBytes(StandardCharsets.UTF_8),
                password.getBytes(StandardCharsets.UTF_8)
        );

        return usernameMatches && passwordMatches;
    }
}