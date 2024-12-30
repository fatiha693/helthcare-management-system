/**
 * User class following Single Responsibility Principle
 * Base class for both patients and administrators
 */
public class User {
    private String username;
    private String password;
    private String role; // "ADMIN" or "PATIENT"
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
} 