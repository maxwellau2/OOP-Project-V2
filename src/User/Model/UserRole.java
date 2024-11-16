/**
 * Enum representing various user roles within the system.
 * Provides utility methods for role management and conversion.
 */
package User.Model;

public enum UserRole {
    /**
     * Represents the Administrator role.
     */
    ADMIN("Administrator"),

    /**
     * Represents the Doctor role.
     */
    DOCTOR("Doctor"),

    /**
     * Represents the Patient role.
     */
    PATIENT("Patient"),

    /**
     * Represents the Pharmacist role.
     */
    PHARMACIST("Pharmacist"); // Add more roles as needed

    private final String roleName;

    /**
     * Constructs a {@link UserRole} with the specified role name.
     *
     * @param roleName The display name of the role.
     */
    UserRole(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Retrieves the name of the role.
     *
     * @return The display name of the role.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Converts the role to a string representation.
     *
     * @return The display name of the role.
     */
    @Override
    public String toString() {
        return this.roleName;
    }

    /**
     * Converts a string to a corresponding {@link UserRole}.
     *
     * @param role The role name as a string.
     * @return The {@link UserRole} that matches the specified role name.
     * @throws IllegalArgumentException If no matching role is found.
     */
    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.roleName.equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No role with name " + role + " found.");
    }
}
