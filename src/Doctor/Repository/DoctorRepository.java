package Doctor.Repository;
import Doctor.Model.Doctor;

import Abstract.Repository;

import java.util.ArrayList;

public class DoctorRepository extends Repository<Doctor> {
    public DoctorRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }

    @Override
    protected Doctor fromCSV(String csvLine){
        String[] data = csvLine.split(",");
        return new Doctor(data[0], data[1], data[2]);
    }

    @Override
    protected String getHeader(){
        return "id,name,specialization";
    }
}
