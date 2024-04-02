package model;

public class AuthenticatedUser extends User {
    private String email;
    private String role;
    public AuthenticatedUser(String email, String role){
        if (email.equals(null)){
            throw new IllegalArgumentException("User email cannot be null");
        }
        if(role.equals(null) || ( !role.equals("AdminStaff") && !role.equals("TeachingStaff") && !role.equals("Student"))){
            throw new IllegalArgumentException("Unsupported user role");
        }
        this.email = email;
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
