package User.Model;

public enum UserRole {
    ADMIN("Administrator"),
    DOCTOR("Doctor"),
    PATIENT("Patient"),
    PHARMACIST("Pharmacist"); // Add more roles as needed

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.roleName.equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No role with name " + role + " found.");
    }
}
