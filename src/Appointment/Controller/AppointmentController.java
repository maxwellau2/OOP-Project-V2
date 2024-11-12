package Appointment.Controller;

import Appointment.Repository.AppointmentRepository;
import Appointment.Model.Appointment;
import Leaves.Controller.LeavesController;
import Leaves.Model.Leave;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Util.RepositoryGetter.getAppointmentRepository;
import static Util.RepositoryGetter.getLeavesRepository;

public class AppointmentController {

    public static Appointment createNewAppointment(Appointment appointment) {
        return getAppointmentRepository().create(appointment);
    }

    public static List<LocalDateTime> getAvailableTimeslots(String doctorId, LocalDateTime startDate) {
        AppointmentRepository repository = getAppointmentRepository();

        // Extend endDate to cover the next three days from startDate
        LocalDateTime endDate = startDate.plusDays(3).with(LocalTime.of(17, 0));

        // Retrieve all appointments for the doctor within this period
        List<Appointment> appointments = repository.getByFilter(app ->
                app.getDoctorId().equals(doctorId) &&
                        !app.getStatus().equals("Cancelled") &&
                        app.getDate().isAfter(startDate.minusMinutes(1)) &&
                        app.getDate().isBefore(endDate.plusMinutes(1))
        );

        // get doctors leaves
        List<Leave> leaves = LeavesController.getStaffLeave(doctorId, startDate, endDate);

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

                for (Appointment appointment : appointments) {
                    LocalDateTime appointmentStart = appointment.getDate();
                    LocalDateTime appointmentEnd = appointmentStart.plusMinutes(30);  // Assuming each appointment is 30 minutes

                    if (!currentSlot.isBefore(appointmentStart) && !currentSlot.isAfter(appointmentEnd.minusMinutes(1))) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable) {
                    availableTimeslots.add(currentSlot);
                }
                currentSlot = currentSlot.plusMinutes(30); // Move to the next slot
            }
        }
        return availableTimeslots;
    }

    public static List<Appointment> getAppointmentByDoctor(String doctorId) {
        return getAppointmentRepository().getByFilter(app -> app.getDoctorId().equals(doctorId));
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


}
