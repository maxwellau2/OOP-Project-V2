package Administrator.Model;

import Interfaces.IEntity;
import User.Model.User;

public class Admin implements IEntity {
    private String id;
    private String name;
    private String gender;
    private int age;
    private String phoneNumber;
	private User user = null;

    // Constructor
    public Admin(String id, String name, String gender, int age, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Implementing toCSV method from IEntity interface
    @Override
    public String toCSV() {
        return id + "," + name + "," + gender + "," + age + "," + phoneNumber;
    }

    @Override
    public String toString() {
        return "Admin{id='" + id + "', name='" + name + "', gender='" + gender + "', age=" + age + ", phoneNumber='" + phoneNumber + "'}";
    }
}
