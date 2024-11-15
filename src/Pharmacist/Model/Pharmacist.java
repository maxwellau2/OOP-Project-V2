package Pharmacist.Model;

import Interfaces.IEntity;
import User.Model.User;


public class Pharmacist implements IEntity {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private User user = null;

    public Pharmacist(String id, String name, Integer age, String gender){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    @Override
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getAge(){
        return this.age;
    }
    public void setAge(Integer age){
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Pharmacist{ID: %s, Name: %s, Age: %s, Gender: %s}", id, name, age, gender);
    }
    @Override
    public String toCSV() {
        return id + "," + name + "," + age + "," + gender;
    }



}
