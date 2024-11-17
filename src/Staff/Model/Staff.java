package Staff.Model;

import Interfaces.IEntity;


/**
 * The Staff class represents a staff member in the system.
 * It implements the {@link IEntity} interface and provides details such as ID, name, role, gender, and age.
 */
public class Staff implements IEntity {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;

    /**
     * Constructs a Staff object with the specified details.
     *
     * @param id     The unique identifier of the staff member.
     * @param name   The name of the staff member.
     * @param role   The role of the staff member (e.g., doctor, pharmacist, admin).
     * @param gender The gender of the staff member.
     * @param age    The age of the staff member.
     */
    public Staff(String id, String name, String role, String gender, int age) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }


    /**
     * Retrieves the unique identifier of the staff member.
     *
     * @return The ID of the staff member.
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the staff member.
     *
     * @param id The new ID to set.
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the staff member.
     *
     * @return The name of the staff member.
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name of the staff member.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the role of the staff member.
     *
     * @return The role of the staff member.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the staff member.
     *
     * @param role The new role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the gender of the staff member.
     *
     * @return The gender of the staff member.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the staff member.
     *
     * @param gender The new gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * Retrieves the age of the staff member.
     *
     * @return The age of the staff member.
     */
    public int getAge() {
        return age;
    }


    /**
     * Sets the age of the staff member.
     *
     * @param age The new age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }


    /**
     * Converts the staff member's details into a CSV-formatted string.
     *
     * @return A CSV representation of the staff member.
     */
    @Override
    public String toCSV() {
        return id + "," + name + "," + role + "," + gender + "," + Integer.toString(age);
    }

    /**
     * Returns a string representation of the staff member.
     *
     * @return A string containing the staff member's details.
     */
    public String toString(){
        return id + "," + name + "," + role + "," + gender + "," + Integer.toString(age);
    }

    /**
     * Prints the staff member's details in a formatted manner.
     */
    public void prettyPrint() {
        System.out.println("==================================");
        System.out.println("Staff ID       : " + id);
        System.out.println("Name           : " + name);
        System.out.println("Role           : " + role);
        System.out.println("Gender         : " + gender);
        System.out.println("Age            : " + age);
        System.out.println("==================================");
    }
}
