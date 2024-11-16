package Staff.Model;

import Interfaces.IEntity;

public class Staff implements IEntity {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;

    public Staff(String id, String name, String role, String gender, int age) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }



    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String toCSV() {
        return id + "," + name + "," + role + "," + gender + "," + Integer.toString(age);
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

    public String toString(){
        return id + "," + name + "," + role + "," + gender + "," + Integer.toString(age);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
