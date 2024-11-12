package AppointmentOutcome.Repository;
import AppointmentOutcome.Model.AppointmentOutcome;
import Abstract.Repository;

import java.util.ArrayList;

public class AppointmentOutcomeRepository extends Repository<AppointmentOutcome> {
    private static AppointmentOutcomeRepository instance = null;
    private AppointmentOutcomeRepository(String csvPath){
        super(csvPath);
        this.entities = new ArrayList<>();
        this.load();
    }

    @Override
    protected AppointmentOutcome fromCSV(String csv) {
        String[] values = csv.split(",");
        return new AppointmentOutcome(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    @Override
    protected String getHeader() {
        return "appointmentId,patientId,doctorId,services,medication,consultationNotes";
    }

    public static AppointmentOutcomeRepository getInstance(String csvPath){
        if(instance == null){
            instance = new AppointmentOutcomeRepository(csvPath);
        }
        return instance;
    }
}
