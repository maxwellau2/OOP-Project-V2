package AppointmentOutcome.Repository;
import AppointmentOutcome.Model.AppointmentOutcome;
import Abstract.Repository;

import java.util.ArrayList;


/**
 * Repository for managing AppointmentOutcome entities, providing functionality to load, store,
 * and retrieve appointment outcomes from a CSV file.
 */
public class AppointmentOutcomeRepository extends Repository<AppointmentOutcome> {
    private static AppointmentOutcomeRepository instance = null;

    /**
     * Private constructor to initialize the repository with the specified CSV path.
     *
     * @param csvPath The file path to the CSV file for storing appointment outcomes.
     */
    private AppointmentOutcomeRepository(String csvPath){
        super(csvPath);
        this.entities = new ArrayList<>();
        this.load();
    }


    /**
     * Retrieves the singleton instance of the AppointmentOutcomeRepository.
     *
     * @param csvPath The file path to the CSV file for storing appointment outcomes.
     * @return The singleton instance of the AppointmentOutcomeRepository.
     */
    public static AppointmentOutcomeRepository getInstance(String csvPath){
        if(instance == null){
            instance = new AppointmentOutcomeRepository(csvPath);
        }
        return instance;
    }

    /**
     * Converts a CSV line into an AppointmentOutcome object.
     *
     * @param csv The CSV string representing an AppointmentOutcome.
     * @return An AppointmentOutcome object parsed from the CSV string.
     */
    @Override
    protected AppointmentOutcome fromCSV(String csv) {
        String[] values = csv.split(",");
        return new AppointmentOutcome(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    /**
     * Gets the header for the CSV file storing AppointmentOutcome entities.
     *
     * @return The CSV header string.
     */
    @Override
    protected String getHeader() {
        return "appointmentId,patientId,doctorId,services,medication,consultationNotes";
    }

}
