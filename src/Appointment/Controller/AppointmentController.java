package Appointment.Controller;

import Appointment.Repository.AppointmentRepository;
import Appointment.Model.Appointment;
import AppointmentOutcome.Model.AppointmentOutcome;
import Leaves.Controller.LeavesController;
import Leaves.Model.Leave;
import Prescription.Model.Prescription;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Util.RepositoryGetter.*;

public class AppointmentController {

    public static Appointment createNewAppointment(Appointment appointment) {
        return getAppointmentRepository().create(appointment);
    }

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


    public static List<Appointment> getAppointmentByDoctor(String doctorId) {
        return getAppointmentRepository().getByFilter(app -> app.getDoctorId().equals(doctorId));
    }

    public static List<Appointment> getAppointmentByDoctorAndStatus(String doctorId, String status) {
        return getAppointmentRepository().getByFilter(app -> app.getDoctorId().equals(doctorId) && app.getStatus().equals(status));
    }

    public static List<Appointment> getAppointmentByDoctor(String doctorId, LocalDateTime startDate, Integer days) {
        return getAppointmentRepository().getByFilter(app -> (app.getDoctorId().equals(doctorId)
                && app.getDate().isAfter(startDate)
                && app.getDate().isBefore(startDate.plusDays(days))) );
    }

    public static List<Appointment> getAppointmentByPatientId(String patientId){
        AppointmentRepository repository = getAppointmentRepository();
        return repository.getByFilter(a -> (a.getPatientId().equals(patientId) && !a.getStatus().equals("Cancelled")));
    }

    public static Appointment updateAppointment(Appointment appointment) {
        return getAppointmentRepository().update(appointment);
    }

    public static Appointment deleteAppointment(Appointment appointment) {
        return getAppointmentRepository().delete(appointment);
    }


    public static Appointment getAppointmentById(String appointmentId) {
        return getAppointmentRepository().read(appointmentId);
    }

    public static List<AppointmentOutcome> getAppointmentOutcomeNotDispensed(){
        List<Prescription> prescriptions = getPrescriptionRepository().getByFilter(p -> p.getStatus().equals("pending"));
        System.out.println(prescriptions);
        List<String> appointmentIds = prescriptions.stream().map(Prescription::getAppointmentId).toList();
        List<AppointmentOutcome> appointmentOutcomes = new ArrayList<>();
        for (String appointmentId : appointmentIds) {
            AppointmentOutcome appointment = getAppointmentOutcomeRepository().read(appointmentId);
            appointmentOutcomes.add(appointment);
        }
        return appointmentOutcomes;
    }
}
