package Doctor.Repository;
import Doctor.Model.Doctor;

import Abstract.Repository;

import java.util.ArrayList;

public class DoctorRepository extends Repository<Doctor> {
    private static DoctorRepository instance = null;
    private DoctorRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }

    public static DoctorRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new DoctorRepository(csvPath);
            instance.load();
        }
        return instance;
    }
    @Override
    protected Doctor fromCSV(String csvLine){
        String[] data = csvLine.split(",");
        return new Doctor(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4]);
    }

    @Override
    protected String getHeader(){
        return "id,name,age,gender,specialization";
    }
}
