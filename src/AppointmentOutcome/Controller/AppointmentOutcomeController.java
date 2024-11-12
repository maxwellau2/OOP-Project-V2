package AppointmentOutcome.Controller;

import AppointmentOutcome.Model.AppointmentOutcome;
import AppointmentOutcome.Repository.AppointmentOutcomeRepository;

import java.util.List;

import static Util.RepositoryGetter.getAppointmentOutcomeRepository;

public class AppointmentOutcomeController {

    public static List<AppointmentOutcome> getAllAppointmentOutcome() {
        return getAppointmentOutcomeRepository().getAll();
    }

    public static List<AppointmentOutcome> getAppointmentOutcomeByAppointmentId(String appointmentId) {
        return getAppointmentOutcomeRepository().filterById(appointmentId);
    }

    public static List<AppointmentOutcome> getAppointmentOutcomeByPatientId(String patientId) {
        return getAppointmentOutcomeRepository().getByFilter(a -> a.getPatientId().equals(patientId));
    }

    public static List<AppointmentOutcome> getAppointmentOutcomeByDoctorId(String doctorId) {
        return getAppointmentOutcomeRepository().getByFilter(a -> a.getDoctorId().equals(doctorId));
    }

    public static AppointmentOutcome createAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        return getAppointmentOutcomeRepository().create(appointmentOutcome);
    }
}
