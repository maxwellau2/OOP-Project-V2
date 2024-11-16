package Doctor.Repository;
import Abstract.Repository;
import Doctor.Model.Doctor;
import java.util.ArrayList;

import static Util.RepositoryGetter.getDoctorRepository;

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
        return new Doctor(data[0], data[1], Integer.valueOf(data[2]), data[3], data[4]);
    }

    @Override
    protected String getHeader(){
        return "id,name,age,gender,specialization";
    }

    public Doctor createDoctor(String name, Integer age, String gender, String specialization){
        Doctor newDoctor = new Doctor(getDoctorRepository().generateId(), name, age, gender, specialization);
        return getDoctorRepository().create(newDoctor);
    }
}
