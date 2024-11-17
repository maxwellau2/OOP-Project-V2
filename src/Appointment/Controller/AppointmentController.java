package Appointment.Controller;

import Appointment.Model.Appointment;
import Appointment.Repository.AppointmentRepository;
import Leaves.Controller.LeavesController;
import Leaves.Model.Leave;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Util.RepositoryGetter.getAppointmentRepository;


/**
 * Controller for managing Appointment-related operations.
 */
public class AppointmentController {

    /**
     * Creates a new Appointment and adds it to the repository.
     *
     * @param appointment The Appointment object to create.
     * @return The created Appointment.
     */
    public static Appointment createNewAppointment(Appointment appointment) {
        return getAppointmentRepository().create(appointment);
    }


    /**
     * Retrieves available timeslots for a doctor within the next three days.
     *
     * @param doctorId   The ID of the doctor.
     * @param startDate  The start date for generating timeslots.
     * @return A list of available LocalDateTime timeslots.
     */
    public static List<LocalDateTime> getAvailableTimeslots(String doctorId, LocalDateTime startDate) {
        AppointmentRepository repository = getAppointmentRepository();

        // Extend endDate to cover the next three days from startDate
        LocalDateTime startOfDay = startDate.withHour(9).withMinute(0).withSecond(0);
        LocalDateTime endDate = startDate.plusDays(3).with(LocalTime.of(17, 0));

        // Retrieve all appointments for the doctor within this period
        List<Appointment> appointments = repository.getByFilter(app ->
                app.getDoctorId().equals(doctorId) &&
                        !app.getStatus().equals("Cancelled") &&
                        app.getDate().isAfter(startOfDay.minusMinutes(1)) &&
                        app.getDate().isBefore(endDate.plusMinutes(1))
        );

        // Get doctor's leaves
        List<Leave> leaves = LeavesController.getStaffLeave(doctorId, startOfDay);

        // Doctor's working hours: 9 AM to 5 PM with 30-minute slots
        LocalTime startWorkingHours = LocalTime.of(9, 0);
        LocalTime endWorkingHours = LocalTime.of(17, 0);
        List<LocalDateTime> availableTimeslots = new ArrayList<>();

        // Iterate through each day in the range
        for (LocalDateTime day = startDate; day.isBefore(endDate); day = day.plusDays(1)) {
            LocalDateTime currentSlot = day.with(startWorkingHours);

            // Generate timeslots for the day and check availability
            while (currentSlot.toLocalTime().isBefore(endWorkingHours)) {
                boolean isAvailable = true;

                // Check if the current slot overlaps with any confirmed appointments
                for (Appointment appointment : appointments) {
                    LocalDateTime appointmentStart = appointment.getDate();
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(30);  // Assuming each appointment is 30 minutes

                    // Adjusted condition to skip overlapping timeslots
                    if (!(currentSlot.isBefore(appointmentStart) || currentSlot.isAfter(appointmentEnd.minusMinutes(1)))) {
                        isAvailable = false;
                        break;
                    }
                }

                // Check if the current slot overlaps with any leave periods
                if (isAvailable) {
                    boolean isNotOnLeave = true;
                    for (Leave leave : leaves) {
                        if (!currentSlot.isBefore(leave.getStart()) && currentSlot.isBefore(leave.getEnd())) {
                            isNotOnLeave = false;
                            break;
                        }
                    }

                    // Add timeslot if it's not on leave and not overlapping with an appointment
                    if (isNotOnLeave) {
                        availableTimeslots.add(currentSlot);
                    }
                }

                // Move to the next 30-minute slot
                currentSlot = currentSlot.plusMinutes(30);
            }
        }

        return availableTimeslots;
    }


    /**
     * Retrieves all appointments for a given doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of appointments for the doctor.
     */
    public static List<Appointment> getAppointmentByDoctor(String doctorId) {
        return getAppointmentRepository().getByFilter(app -> app.getDoctorId().equals(doctorId));
    }


    /**
     * Retrieves appointments for a doctor filtered by status.
     *
     * @param doctorId The ID of the doctor.
     * @param status   The status to filter by.
     * @return A list of filtered appointments.
     */
    public static List<Appointment> getAppointmentByDoctorAndStatus(String doctorId, String status) {
        return getAppointmentRepository().getByFilter(app -> app.getDoctorId().equals(doctorId) && app.getStatus().equals(status));
    }


    /**
     * Retrieves confirmed appointments for a doctor within a specific time range.
     *
     * @param doctorId  The ID of the doctor.
     * @param startDate The start date of the range.
     * @param days      The number of days from the start date.
     * @return A list of confirmed appointments.
     */
    public static List<Appointment> getAppointmentByDoctor(String doctorId, LocalDateTime startDate, Integer days) {
        return getAppointmentRepository().getByFilter(app -> (app.getDoctorId().equals(doctorId)
                && app.getDate().isAfter(startDate)
                && app.getDate().isBefore(startDate.plusDays(days)))
                && app.getStatus().equalsIgnoreCase("confirmed") );
    }


    /**
     * Retrieves all appointments for a patient, excluding cancelled ones.
     *
     * @param patientId The ID of the patient.
     * @return A list of appointments for the patient.
     */
    public static List<Appointment> getAppointmentByPatientId(String patientId){
        AppointmentRepository repository = getAppointmentRepository();
        return repository.getByFilter(a -> (a.getPatientId().equals(patientId) && !a.getStatus().equals("Cancelled")));
    }


    /**
     * Updates an existing Appointment.
     *
     * @param appointment The Appointment object with updated data.
     * @return The updated Appointment.
     */
    public static Appointment updateAppointment(Appointment appointment) {
        return getAppointmentRepository().update(appointment);
    }


    /**
     * Deletes an Appointment from the repository.
     *
     * @param appointment The Appointment to delete.
     * @return The deleted Appointment, or null if not found.
     */
    public static Appointment deleteAppointment(Appointment appointment) {
        return getAppointmentRepository().delete(appointment);
    }


    /**
     * Retrieves an Appointment by its ID.
     *
     * @param appointmentId The ID of the appointment.
     * @return The Appointment object if found, null otherwise.
     */
    public static Appointment getAppointmentById(String appointmentId) {
        return getAppointmentRepository().read(appointmentId);
    }


    /**
     * Creates a new Appointment object with "Pending" status.
     *
     * @param patientId  The ID of the patient.
     * @param doctorId   The ID of the doctor.
     * @param timeslot   The timeslot for the appointment.
     * @return The newly created Appointment object.
     */
    public static Appointment createPendingAppointmentObject(String patientId, String doctorId, LocalDateTime timeslot){
        return new Appointment(
                getAppointmentRepository().generateId(),  // Generate a unique ID
                patientId,
                doctorId,
                timeslot,
                "Pending"
                );
            }


    /**
     * Retrieves appointments for a doctor filtered by a specific status.
     *
     * @param id     The ID of the doctor.
     * @param status The status to filter by.
     * @return A list of appointments.
     */
    public static List<Appointment> getAppointmentsByDoctorAndStatus(String id, String status) {
        return getAppointmentRepository().getByFilter(appointment -> appointment.getDoctorId().equals(id) && appointment.getStatus().equalsIgnoreCase(status));
    }
}
