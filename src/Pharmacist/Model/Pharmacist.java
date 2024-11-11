package Pharmacist.Model;

import Interfaces.IEntity;


public class Pharmacist implements IEntity {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private String qualification;

    public Pharmacist(String id, String name, Integer age, String gender, String qualification){
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.qualification = qualification;
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

    public String getQualification(){
        return this.qualification;
    }
    public void setqualification(String qualification){
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return String.format("Pharmacist{ID: %s, Name: %s, Age: %s, Gender: %s, QUalification: %s}", id, name, age, gender, qualification);
    }
    @Override
    public String toCSV() {
        return id + "," + name + "," + age + "," + gender + "," + qualification;
    }



}
