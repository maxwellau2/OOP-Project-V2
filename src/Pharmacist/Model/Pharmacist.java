package Pharmacist.Model;

import Interfaces.IEntity;


public class Pharmacist implements IEntity {
    private String id;
    private String name;
    private String qualification;

    public Pharmacist(String id, String name, String qualification){
        this.id = id;
        this.name = name;
        this.qualification = qualification;
    }
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
    public String getQualification(){
        return this.qualification;
    }
    public void setqualification(String qualification){
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return String.format("Pharmacist{ID: %s, Name: %s, QUalification: %s}", id, name, qualification);
    }
    public String toCSV() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toCSV'");
    }



}
