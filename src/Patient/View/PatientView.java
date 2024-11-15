package Patient.View;
import Appointment.Model.Appointment;
import AppointmentOutcome.Controller.AppointmentOutcomeController;
import Doctor.Model.Doctor;
import Appointment.Controller.AppointmentController;
import Doctor.Controller.DoctorController;
import MedicalRecord.Model.MedicalRecord;
import Patient.Controller.PatientActions;
import Patient.Model.Patient;
import Util.TimeRangeMerger;
import AppointmentOutcome.Model.AppointmentOutcome;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static Util.RepositoryGetter.getAppointmentRepository;
import static Util.SafeScanner.getValidatedIntInput;
import static Util.TimeRangeMerger.mergeTimeslotsIntoRanges;

public class PatientView {
    Patient patient;
    Scanner scanner = new Scanner(System.in);
    public PatientView(Patient patient){
        this.patient = patient;
    }

    public void displayMenu() {
        int choice;

        do {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointments Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointment");
            System.out.println("8. View Past Appointment Outcomes");
            System.out.println("0. Exit");

            choice = getValidatedIntInput(scanner, "Enter your choice: ", 0, 8);

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    viewAvailableAppointmentsSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomes();
                    break;
                case 0:
                    System.out.println("Exiting the Patient Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

        scanner.close(); // Close only once after the menu loop ends
    }

    // test case 1
    public void viewMedicalRecord(){
        System.out.println("=======Medical Records======");
        // get patient info
        System.out.println("Name : "+ patient.getName());
        System.out.println("Gender : "+ patient.getGender());
        System.out.println("Date of Birth : " + patient.getDob());
        System.out.println("Email : " + patient.getEmail());
        System.out.println("Phone Number : " + patient.getPhoneNumber());
        System.out.println("Blood Type : " + patient.getBloodType());
        // get medical records
        List<MedicalRecord> records =  PatientActions.getMedicalRecords(patient);
        for (MedicalRecord record : records){
            record.prettyPrintMedicalRecord();
        }
    }

    // test case 2
    public Patient updatePersonalInformation(){
        System.out.println("Welcome to the Patient Record System!");
        System.out.println("Current Patient Information:\n");

        System.out.println("Email : " + patient.getEmail());
        System.out.println("Phone Number : " + patient.getPhoneNumber());

        // Prompt for update choice
        System.out.println("\nChoose an option to update:");
        System.out.println("1. Update Email");
        System.out.println("2. Update Contact Number");
        System.out.println("3. Update Both Email and Contact Number");
        System.out.print("Enter your choice (1, 2, or 3): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new email address: ");
                String newEmail = scanner.nextLine();
                patient.setEmail(newEmail);
                System.out.println("Email updated successfully.");
                break;
            case 2:
                System.out.print("Enter new contact number: ");
                String newContactNumber = scanner.nextLine();
                patient.setPhoneNumber(newContactNumber);
                System.out.println("Contact number updated successfully.");
                break;
            case 3:
                System.out.print("Enter new email address: ");
                newEmail = scanner.nextLine();
                patient.setEmail(newEmail);

                System.out.print("Enter new contact number: ");
                newContactNumber = scanner.nextLine();
                patient.setPhoneNumber(newContactNumber);

                System.out.println("Email and contact number updated successfully.");
                break;
            default:
                System.out.println("Invalid choice. Please run the program again.");
                return patient;
        }

        // Display updated patient information
        System.out.println("\nUpdated Patient Information:");
        System.out.println("Email : " + patient.getEmail());
        System.out.println("Phone Number : " + patient.getPhoneNumber());
        // update in the repo
        PatientActions.updatePatient(patient);

        return patient;
    }

    private static void printBorder(){
        System.out.println("\n===============================================");
        return;
    }

    // test case 3
    public void viewAvailableAppointmentsSlots(){
        // get each doctor's doctor id
        List<Doctor> doctors = DoctorController.getAllDoctors();
        for(Doctor doctor : doctors){
            printBorder();
            System.out.println("Doctor Name : " + doctor.getName());
            System.out.println("Doctor Gender : " + doctor.getGender());
            System.out.println("Doctor Specialization : " + doctor.getSpecialization());
            // get today's local time
            LocalDateTime timeNow = LocalDateTime.now();
            // get 7 days after
            LocalDateTime timeFuture = timeNow.plusDays(7);
            // get appointments within these 7 days
            List<LocalDateTime> timeslots = AppointmentController.getAvailableTimeslots(doctor.getId(), timeNow);
            List<String> formatted = mergeTimeslotsIntoRanges(timeslots);
            for(String f : formatted){
                System.out.println(f);
            }
        }
    }

    public void scheduleAppointment() {
        System.out.println("=== Schedule an Appointment ===");

        // Step 1: Display list of doctors
        List<Doctor> doctors = DoctorController.getAllDoctors();
        System.out.println("Available Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).getName() + " - " + doctors.get(i).getSpecialization());
        }

        // Step 2: Choose a doctor
        System.out.print("Select a doctor by number: ");
        int doctorIndex = scanner.nextInt() - 1;
        Doctor selectedDoctor = doctors.get(doctorIndex);

        // Step 3: Display available timeslots for the next 3 days
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime timeFuture = timeNow.plusDays(3);
        List<LocalDateTime> availableTimeslots = AppointmentController.getAvailableTimeslots(selectedDoctor.getId(), timeNow);

        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected doctor in the next 3 days.");
            return;
        }

        System.out.println("Available Timeslots:");
        for (int i = 0; i < availableTimeslots.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimeslots.get(i));
        }

        // Step 4: Select a timeslot
        System.out.print("Select a timeslot by number: ");
        int timeslotIndex = scanner.nextInt() - 1;
        LocalDateTime selectedTimeslot = availableTimeslots.get(timeslotIndex);

        // Step 5: Make  the appointment Pending
        Appointment newAppointment = new Appointment(
                getAppointmentRepository().generateId(),  // Generate a unique ID
                patient.getId(),
                selectedDoctor.getId(),
                selectedTimeslot,
                "Pending"
        );

        Appointment createdAppt = AppointmentController.createNewAppointment(newAppointment);

        if (createdAppt != null) {
            System.out.println("Appointment successfully scheduled at " + selectedTimeslot + " with Dr. " + selectedDoctor.getName());
            System.out.println("Please wait for Dr " + selectedDoctor.getName() + " to confirm the appointment.");
        } else {
            System.out.println("Failed to schedule appointment. Please try again.");
        }
    }
    // test case 5
    public void rescheduleAppointment() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Display the patient's confirmed appointments
        List<Appointment> appointments = AppointmentController.getAppointmentByPatientId(patient.getId());
        if (appointments.isEmpty()) {
            System.out.println("You have no appointments to reschedule.");
            return;
        }

        System.out.println("=== Your Appointments ===");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.println((i + 1) + ". Appointment with Dr. " + appointment.getDoctorId() +
                    " on " + appointment.getDate());
        }

        // Step 2: Choose an appointment to reschedule
        System.out.print("Select the appointment number to reschedule: ");
        int appointmentIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (appointmentIndex < 0 || appointmentIndex >= appointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Appointment selectedAppointment = appointments.get(appointmentIndex);

        // Step 3: Display available timeslots for the next 3 days for the selected doctor
        LocalDateTime startDate = LocalDateTime.now();
        List<LocalDateTime> availableTimeslots = AppointmentController.getAvailableTimeslots(
                selectedAppointment.getDoctorId(), startDate);

        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for rescheduling in the next 3 days.");
            return;
        }

        System.out.println("=== Available Timeslots ===");
        for (int i = 0; i < availableTimeslots.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimeslots.get(i));
        }

        // Step 4: Choose a new timeslot
        System.out.print("Select a new timeslot number: ");
        int timeslotIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (timeslotIndex < 0 || timeslotIndex >= availableTimeslots.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        LocalDateTime newTimeslot = availableTimeslots.get(timeslotIndex);

        // Step 5: Update the appointment with the new timeslot
        selectedAppointment.setDate(newTimeslot);
        Appointment updatedAppointment = AppointmentController.updateAppointment(selectedAppointment);

        if (updatedAppointment != null) {
            System.out.println("Appointment rescheduled successfully to " + newTimeslot);
        } else {
            System.out.println("Failed to reschedule appointment. The selected timeslot may no longer be available.");
        }
    }

    // test case 6
    public void cancelAppointment() {
        // Step 1: Display the patient's appointments
        List<Appointment> appointments = AppointmentController.getAppointmentByPatientId(patient.getId());
        if (appointments.isEmpty()) {
            System.out.println("You have no appointments to cancel.");
            return;
        }

        System.out.println("=== Your Appointments ===");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.println((i + 1) + ". Appointment with Dr. " + DoctorController.getDoctorById(appointment.getDoctorId()).getName() +
                    " on " + appointment.getDate() + " - Status: " + appointment.getStatus());
        }

        // Step 2: Choose an appointment to cancel
        int appointmentIndex = getValidatedIntInput(scanner, "Select the appointment number to cancel: ", 1, appointments.size()) - 1;
        Appointment selectedAppointment = appointments.get(appointmentIndex);

        // Step 3: Confirm cancellation
        System.out.print("Are you sure you want to cancel this appointment? (yes/no): ");
        String confirmation = scanner.next().trim().toLowerCase();
        if (!confirmation.equals("yes")) {
            System.out.println("Cancellation aborted.");
            return;
        }

        // Step 4: Delete the appointment
        Appointment deletedAppointment = AppointmentController.deleteAppointment(selectedAppointment);

        if (deletedAppointment != null) {
            System.out.println("Appointment canceled successfully.");
        } else {
            System.out.println("Failed to cancel the appointment. Please try again.");
        }
    }
    // test case 7
    public void viewScheduledAppointments() {
        // Step 1: Retrieve all upcoming appointments for the patient
        List<Appointment> appointments = AppointmentController.getAppointmentByPatientId(patient.getId());

        // Step 2: Check if there are any upcoming appointments
        if (appointments.isEmpty()) {
            System.out.println("You have no upcoming appointments.");
            return;
        }

        System.out.println("=== Your Scheduled Appointments ===");

        // Step 3: Display each appointment with details
        for (Appointment appointment : appointments) {
            // Retrieve doctor details for display
            Doctor doctor = DoctorController.getDoctorById(appointment.getDoctorId());
            if (doctor != null) {
                System.out.println("Doctor: Dr. " + doctor.getName() + " - " + doctor.getSpecialization());
            } else {
                System.out.println("Doctor: Unknown (ID: " + appointment.getDoctorId() + ")");
            }

            System.out.println("Date & Time: " + appointment.getDate());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println("--------------------------------------");
        }
    }
    // test case 8
    public void viewPastAppointmentOutcomes() {
        // Step 1: Retrieve all appointment outcomes for the patient
        List<AppointmentOutcome> outcomes = AppointmentOutcomeController.getAppointmentOutcomeByPatientId(patient.getId());

        // Step 2: Check if there are any past outcomes
        if (outcomes.isEmpty()) {
            System.out.println("You have no past appointment outcomes.");
            return;
        }

        System.out.println("=== Past Appointment Outcomes ===");
        // Step 3: Display each outcome's details
        for (AppointmentOutcome outcome : outcomes) {
            System.out.println("Appointment ID: " + outcome.getAppointmentId());
            System.out.println("Doctor Name: " + DoctorController.getDoctorById(outcome.getDoctorId()).getName());
            System.out.println("Services Provided: " + outcome.getServices());
            System.out.println("Prescribed Medication: " + outcome.getMedication());
            System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
            System.out.println("--------------------------------------");
        }
    }

}
