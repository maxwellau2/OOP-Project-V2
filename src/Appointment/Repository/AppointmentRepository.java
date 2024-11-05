package Appointment.Repository;

import Abstract.Repository;
import Appointment.Model.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentRepository extends Repository<Appointment> {

    public AppointmentRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Appointment>();
    }

    @Override
    protected String getHeader(){
        return "id,patientId,doctorId,date,status";
    }

    @Override
    protected Appointment fromCSV(String csv) {
        String[] values = csv.split(",");
        LocalDateTime date = LocalDateTime.parse(values[3], DateTimeFormatter.ISO_DATE_TIME);
        return new Appointment(values[0], values[1], values[2], date, values[4]);
    }
}
