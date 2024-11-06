package Patient.Repository;
import Patient.Model.Patient;
import Abstract.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PatientRepository extends Repository<Patient> {
    private static PatientRepository instance = null;
    public static PatientRepository getInstance(String csvPath){
        if(instance == null){
            instance = new PatientRepository(csvPath);
        }
        return instance;
    }

    private PatientRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Patient>();
    }

    @Override
    protected Patient fromCSV(String csv) {
        String[] values = csv.split(",");
        LocalDateTime date = LocalDateTime.parse(values[2], DateTimeFormatter.ISO_DATE_TIME);
        return new Patient(values[0], values[1], date, values[3], values[4], values[5]);
    }

    @Override
    protected String getHeader() {
        return "id,name,dob,gender,contactInfo,bloodType";
    }
}
