package Administrator.Model;

import Interfaces.IEntity;
import User.Model.User;

/**
 * Represents an administrator in the hospital system.
 * Implements the {@link IEntity} interface to support entity-specific operations.
 */
public class Admin implements IEntity {
    private String id;
    private String name;
    private String gender;
    private int age;
    private User user = null;

    /**
     * Constructs a new {@code Admin} instance with the specified details.
     *
     * @param id     The unique identifier of the admin.
     * @param name   The name of the admin.
     * @param gender The gender of the admin.
     * @param age    The age of the admin.
     */
    public Admin(String id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    /**
     * Gets the unique identifier of the admin.
     *
     * @return The admin's ID.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the admin.
     *
     * @param id The admin's ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the admin.
     *
     * @return The admin's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the admin.
     *
     * @param name The admin's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the gender of the admin.
     *
     * @return The admin's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the admin.
     *
     * @param gender The admin's gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the age of the admin.
     *
     * @return The admin's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the admin.
     *
     * @param age The admin's age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the associated user profile of the admin.
     *
     * @return The admin's user profile.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the associated user profile of the admin.
     *
     * @param user The admin's user profile.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Converts the admin details to a CSV-compatible string format.
     *
     * @return A string in CSV format containing the admin's details.
     */
    @Override
    public String toCSV() {
        return id + "," + name + "," + gender + "," + age;
    }

    /**
     * Provides a string representation of the admin's details.
     *
     * @return A string describing the admin.
     */
    @Override
    public String toString() {
        return "Admin{id='" + id + "', name='" + name + "', gender='" + gender + "', age=" + age + "}";
    }
}
