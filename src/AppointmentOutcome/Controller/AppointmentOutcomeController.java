package AppointmentOutcome.Controller;

import AppointmentOutcome.Model.AppointmentOutcome;
import Prescription.Model.Prescription;

import java.util.ArrayList;
import java.util.List;

import static Util.RepositoryGetter.getAppointmentOutcomeRepository;
import static Util.RepositoryGetter.getPrescriptionRepository;

/**
 * Controller for managing AppointmentOutcome entities, providing utility methods for
 * retrieving, creating, and filtering appointment outcomes.
 */
public class AppointmentOutcomeController {

    /**
     * Retrieves all appointment outcomes.
     *
     * @return A list of all AppointmentOutcome objects.
     */
    public static List<AppointmentOutcome> getAllAppointmentOutcome() {
        return getAppointmentOutcomeRepository().getAll();
    }


    /**
     * Retrieves appointment outcomes by a specific appointment ID.
     *
     * @param appointmentId The ID of the appointment.
     * @return A list of AppointmentOutcome objects associated with the given appointment ID.
     */
    public static List<AppointmentOutcome> getAppointmentOutcomeByAppointmentId(String appointmentId) {
        return getAppointmentOutcomeRepository().filterById(appointmentId);
    }


    /**
     * Retrieves appointment outcomes by a specific patient ID.
     *
     * @param patientId The ID of the patient.
     * @return A list of AppointmentOutcome objects associated with the given patient ID.
     */
    public static List<AppointmentOutcome> getAppointmentOutcomeByPatientId(String patientId) {
        return getAppointmentOutcomeRepository().getByFilter(a -> a.getPatientId().equals(patientId));
    }

    /**
     * Retrieves appointment outcomes by a specific doctor ID.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of AppointmentOutcome objects associated with the given doctor ID.
     */
    public static List<AppointmentOutcome> getAppointmentOutcomeByDoctorId(String doctorId) {
        return getAppointmentOutcomeRepository().getByFilter(a -> a.getDoctorId().equals(doctorId));
    }


    /**
     * Creates a new appointment outcome and adds it to the repository.
     *
     * @param appointmentOutcome The AppointmentOutcome object to be created.
     * @return The created AppointmentOutcome object.
     */
    public static AppointmentOutcome createAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        return getAppointmentOutcomeRepository().create(appointmentOutcome);
    }


    /**
     * Retrieves a list of appointment outcomes that have prescriptions not yet dispensed.
     *
     * @return A list of AppointmentOutcome objects associated with prescriptions marked as "pending."
     */
    public static List<AppointmentOutcome> getAppointmentOutcomeNotDispensed(){
        List<Prescription> prescriptions = getPrescriptionRepository().getByFilter(p -> p.getStatus().equals("pending"));
//        System.out.println(prescriptions);
        List<String> appointmentIds = prescriptions.stream().map(Prescription::getAppointmentId).toList();
        List<AppointmentOutcome> appointmentOutcomes = new ArrayList<>();
        for (String appointmentId : appointmentIds) {
            AppointmentOutcome appointment = getAppointmentOutcomeRepository().read(appointmentId);
            appointmentOutcomes.add(appointment);
        }
        return appointmentOutcomes;
    }
}
