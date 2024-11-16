package Pharmacist.Model;

import Interfaces.IEntity;
import User.Model.User;


/**
 * Represents a Pharmacist entity in the system.
 * Implements the {@link Interfaces.IEntity} interface for ID-based operations.
 */
public class Pharmacist implements IEntity {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private User user = null;


    /**
     * Constructs a Pharmacist object with the specified attributes.
     *
     * @param id     The unique ID of the pharmacist.
     * @param name   The name of the pharmacist.
     * @param age    The age of the pharmacist.
     * @param gender The gender of the pharmacist.
     */
    public Pharmacist(String id, String name, Integer age, String gender){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Gets the unique ID of the pharmacist.
     *
     * @return The ID of the pharmacist.
     */
    @Override
    public String getId(){
        return this.id;
    }

    /**
     * Sets the unique ID of the pharmacist.
     *
     * @param id The new ID for the pharmacist.
     */
    public void setId(String id){
        this.id = id;
    }


    public String getName(){
        return this.name;
    }

    /**
     * Gets the name of the pharmacist.
     *
     * @return The name of the pharmacist.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the age of the pharmacist.
     *
     * @return The age of the pharmacist.
     */
    public Integer getAge(){
        return this.age;
    }

    /**
     * Sets the age of the pharmacist.
     *
     * @param age The new age for the pharmacist.
     */
    public void setAge(Integer age){
        this.age = age;
    }

    /**
     * Gets the gender of the pharmacist.
     *
     * @return The gender of the pharmacist.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the pharmacist.
     *
     * @param gender The new gender for the pharmacist.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the associated user account for the pharmacist.
     *
     * @return The associated {@link User} object.
     */
    public User getUser() {
        return user;
    }


    /**
     * Sets the associated user account for the pharmacist.
     *
     * @param user The {@link User} object to associate with the pharmacist.
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Converts the pharmacist object to a CSV-formatted string.
     *
     * @return A CSV string representation of the pharmacist.
     */
    @Override
    public String toString() {
        return String.format("Pharmacist{ID: %s, Name: %s, Age: %s, Gender: %s}", id, name, age, gender);
    }

    /**
     * Provides a string representation of the pharmacist object.
     *
     * @return A formatted string with the pharmacist's details.
     */
    @Override
    public String toCSV() {
        return id + "," + name + "," + age + "," + gender;
    }



}
