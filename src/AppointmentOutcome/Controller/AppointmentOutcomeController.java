package AppointmentOutcome.Controller;

import AppointmentOutcome.Model.AppointmentOutcome;
import AppointmentOutcome.Repository.AppointmentOutcomeRepository;

import java.util.List;

public class AppointmentOutcomeController {
    private static AppointmentOutcomeRepository getAppointmentOutcomeRepository() {
        return AppointmentOutcomeRepository.getInstance("src/Data/AppointmentOutcome_List.csv");
    }

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
