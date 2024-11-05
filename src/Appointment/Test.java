package Appointment;

import Appointment.Model.Appointment;
import Appointment.Repository.AppointmentRepository;

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        // create the Repo
        AppointmentRepository repo = new AppointmentRepository("apptRepo.csv");
//        repo.display();
//        repo.create(new Appointment("123","patient1","123", LocalDateTime.now(),"123"));
//        repo.create(new Appointment("123","patient2","123", LocalDateTime.now(),"123"));
//        repo.store();
        repo.load();
        repo.display();
        repo.store();
    }
}
