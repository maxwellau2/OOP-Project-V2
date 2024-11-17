package Appointment.Repository;

import Abstract.Repository;
import Appointment.Model.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Manages the storage and retrieval of Appointment entities.
 */
public class AppointmentRepository extends Repository<Appointment> {
    private static AppointmentRepository instance;

    /**
     * Private constructor for AppointmentRepository.
     *
     * @param csvPath The path to the CSV file storing appointment data.
     */
    private AppointmentRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Appointment>();
        this.load();
    }

    /**
     * Singleton access to the AppointmentRepository instance.
     *
     * @param csvPath The path to the CSV file storing appointment data.
     * @return The singleton instance of AppointmentRepository.
     */
    public static AppointmentRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new AppointmentRepository(csvPath);
        }
        return instance;
    }

    /**
     * Provides the header row for the CSV file.
     *
     * @return The header row as a comma-separated string.
     */
    @Override
    protected String getHeader(){
        return "id,patientId,doctorId,date,status";
    }

    /**
     * Converts a CSV row into an Appointment object.
     *
     * @param csv A comma-separated string representing an Appointment.
     * @return An Appointment object parsed from the CSV row.
     */
    @Override
    protected Appointment fromCSV(String csv) {
        String[] values = csv.split(",");
        LocalDateTime date = LocalDateTime.parse(values[3], DateTimeFormatter.ISO_DATE_TIME);
        return new Appointment(values[0], values[1], values[2], date, values[4]);
    }
}
