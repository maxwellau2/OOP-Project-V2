package Doctor.View;

import Appointment.Controller.AppointmentController;
import Appointment.Model.Appointment;
import AppointmentOutcome.Controller.AppointmentOutcomeController;
import AppointmentOutcome.Model.AppointmentOutcome;
import Doctor.Controller.DoctorController;
import Doctor.Model.Doctor;
import Inventory.Controller.InventoryController;
import Leaves.Controller.LeavesController;
import Leaves.Model.Leave;
import MedicalRecord.Controller.MedicalRecordController;
import MedicalRecord.Model.MedicalRecord;
import Patient.Controller.PatientController;
import Patient.Model.Patient;
import Prescription.Controller.PrescriptionController;
import Prescription.Model.Prescription;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static Util.SafeScanner.getValidatedIntInput;
import static Util.SafeScanner.getValidatedStringInput;


/**
 * DoctorView class provides the user interface and menu for doctor operations.
 * Allows doctors to view and manage appointments, medical records, availability, and outcomes.
 */
public class DoctorView {
    private Doctor doctor;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for DoctorView.
     *
     * @param doctor The Doctor entity representing the logged-in doctor.
     */
    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
    }


    /**
     * Displays the main menu for the doctor.
     * Allows navigation to various operations.
     */
    public void displayMenu() {
        int choice;

        do {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("0. Exit");

            choice = getValidatedIntInput(scanner, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1:
                    viewPatientMedicalRecords();
                    break;
                case 2:
                    updatePatientMedicalRecords();
                    break;
                case 3:
                    viewPersonalSchedule();
                    break;
                case 4 :
                    setAppointmentAvailability();
                    break;
                case 5:
                    handleAppointmentRequests();
                    break;
                case 6:
                    viewUpcomingAppointments();
                    break;
                case 7:
                    recordAppointmentOutcome();
                    break;
                case 0:
                    System.out.println("Exiting the Doctor Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    // Test case 9
    /**
     * Displays all medical records assigned to the logged-in doctor.
     * Retrieves records using DoctorController.
     */
    public void viewPatientMedicalRecords() {
        System.out.println("=== View Patient Medical Records ===");
        List<MedicalRecord> records = DoctorController.viewPatientRecord(doctor);
        if (records.isEmpty()) {
            System.out.println("There are no patient medical records under you.");
        } else {
            for (MedicalRecord record : records) {
                record.prettyPrintMedicalRecord();
            }
        }
    }

    // Test case 10
    /**
     * Allows the doctor to update medical records for a specific patient.
     * Includes diagnosis and treatment plan updates.
     */
    public void updatePatientMedicalRecords() {
        System.out.println("=== Update Patient Medical Records ===");

        String patientId = getValidatedStringInput(scanner, "Enter Patient ID: ", 20);

        Patient patient = PatientController.getPatientById(patientId);
        if (patient == null) {
            System.out.println("No patient found with ID: " + patientId);
            return;
        }

        String diagnosis = getValidatedStringInput(scanner, "Enter Diagnosis: ", 100);
        String treatmentPlan = getValidatedStringInput(scanner, "Enter Treatment Plan: ", 100);

        MedicalRecord newRecord = MedicalRecordController.createNewMedicalRecordToday(patientId, doctor.getId(), diagnosis, treatmentPlan);
        MedicalRecord record = DoctorController.addPatientRecord(newRecord);
        if (record != null) {
            System.out.println("Patient Medical Records updated successfully.");
        } else {
            System.out.println("Patient Medical Records update failed.");
        }
    }

    // Test case 11
    /**
     * Displays the doctor's schedule for the next three days.
     * Includes confirmed appointments and pending requests.
     */
    public void viewPersonalSchedule() {
        System.out.println("=== View Personal Schedule ===");
        List<Appointment> appointments = AppointmentController.getAppointmentByDoctor(doctor.getId(), LocalDateTime.now().toLocalDate().atStartOfDay(), 7);
        for (Appointment appointment : appointments) {
            appointment.prettyPrint();
        }
    }

    // Test case 12
    /**
     * Manages the doctor's availability for appointments.
     * Includes options to add or remove leave.
     */
    public void setAppointmentAvailability() {
        System.out.println("=== Set Availability for Appointments ===");

        boolean continueSettingAvailability = true;
        while (continueSettingAvailability) {
            System.out.println("1. Add Leave");
            System.out.println("2. Remove Leave");
            System.out.println("3. View Upcoming Leaves");
            System.out.println("0. Back to Main Menu");
            int choice = getValidatedIntInput(scanner, "Choose an option: ", 0, 3);

            switch (choice) {
                case 1 -> addLeave();
                case 2 -> removeLeave();
                case 3 -> viewLeaves();
                case 0 -> continueSettingAvailability = false;
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void viewLeaves() {
        System.out.println("=== View Leaves ===");
        List<Leave> leaves = LeavesController.getStaffLeave(doctor.getId(), LocalDateTime.now().toLocalDate().atStartOfDay());
        for (int i=0; i<leaves.size(); i++){
            System.out.print(i+1 + "- ");
            leaves.get(i).prettyPrint();
        }
    }

    /**
     * Adds a new leave for the doctor.
     * Prompts the doctor to enter leave details such as start and end times.
     */
    private void addLeave() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String leaveName = getValidatedStringInput(scanner, "Enter the leave name (e.g., Vacation, Conference): ", 50);

        LocalDateTime start;
        while (true) {
            try {
                String startDateTimeStr = getValidatedStringInput(scanner, "Enter leave start date and time (yyyy-MM-dd HH:mm): ", 16);
                start = LocalDateTime.parse(startDateTimeStr, formatter);
                if (start.isAfter(LocalDateTime.now())) {
                    break;
                } else {
                    System.out.println("Please enter a valid start date and time (yyyy-MM-dd HH:mm): ");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in yyyy-MM-dd HH:mm format.");
            }
        }

        LocalDateTime end;
        while (true) {
            try {
                String endDateTimeStr = getValidatedStringInput(scanner, "Enter leave end date and time (yyyy-MM-dd HH:mm): ", 16);
                end = LocalDateTime.parse(endDateTimeStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in yyyy-MM-dd HH:mm format.");
            }
        }

        Leave newLeave = LeavesController.createLeave(doctor.getId(), leaveName, start, end);

        if (LeavesController.addLeave(newLeave) != null) {
            System.out.println("Leave added successfully.");
        } else {
            System.out.println("Failed to add leave.");
        }
    }

    /**
     * Removes an existing leave for the doctor.
     * Displays a list of leaves and allows selection by index.
     */
    private void removeLeave() {
        System.out.println("Existing leaves:");
        List<Leave> leaves = LeavesController.getStaffLeave(doctor.getId(), LocalDateTime.now().toLocalDate().atStartOfDay());
        if (leaves.isEmpty()) {
            System.out.println("No leaves found.");
            return;
        }

        for (int i = 0; i < leaves.size(); i++) {
            Leave leave = leaves.get(i);
            System.out.println((i + 1) + ". " + leave.getLeaveName() + " (" + leave.getStart() + " to " + leave.getEnd() + ")");
        }

        int leaveIndex = getValidatedIntInput(scanner, "Enter the leave number to remove: ", 1, leaves.size()) - 1;
        Leave selectedLeave = leaves.get(leaveIndex);

        if (LeavesController.removeLeave(selectedLeave) != null) {
            System.out.println("Leave removed successfully.");
        } else {
            System.out.println("Failed to remove leave.");
        }
    }

    // Test case 13
    /**
     * Handles pending appointment requests for the doctor.
     * Allows the doctor to accept or decline requests.
     */
    public void handleAppointmentRequests() {
        System.out.println("=== Accept or Decline Appointment Requests ===");

        List<Appointment> appointments = AppointmentController.getAppointmentByDoctorAndStatus(doctor.getId(), "Pending");

        if (appointments.isEmpty()) {
            System.out.println("No pending appointment requests.");
            return;
        }

        for (Appointment appointment : appointments) {
            appointment.prettyPrint();

            int choice = getValidatedIntInput(scanner, "Enter 1 to Accept or 2 to Decline this appointment: ", 1, 2);

            if (choice == 1) {
                appointment.setStatus("Confirmed");
                System.out.println("Appointment accepted.");
            } else {
                appointment.setStatus("Cancelled");
                System.out.println("Appointment declined.");
            }

            AppointmentController.updateAppointment(appointment);
        }

        System.out.println("All pending appointment requests have been handled.");
    }

    // Test case 14
    /**
     * Displays the doctor's upcoming appointments.
     * Includes confirmed appointments within the next three days.
     */
    public void viewUpcomingAppointments() {
        System.out.println("=== View Upcoming Appointments ===");
        List<Appointment> appointments = AppointmentController.getAppointmentByDoctor(doctor.getId(), LocalDateTime.now().withHour(0), 7);
        if (appointments.isEmpty()){
            System.out.println("No upcoming appointments ");
            return;
        }
        for (Appointment appointment : appointments) {
            appointment.prettyPrint();
        }
    }

    // Test case 15
    /**
     * Records the outcome of a confirmed appointment.
     * Includes consultation notes, services provided, and optional prescription.
     */
    public void recordAppointmentOutcome() {
        System.out.print("=== Record Appointment Outcome ===\n");

        // Step 1: Get a list of confirmed appointments
        List<Appointment> confirmedAppointments = AppointmentController.getAppointmentsByDoctorAndStatus(doctor.getId(), "confirmed");
        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments available for recording outcomes.");
            return;
        }

        // Step 2: Display the list of confirmed appointments
        System.out.println("Select an appointment from the list below:");
        for (int i = 0; i < confirmedAppointments.size(); i++) {
            System.out.print((i + 1) + ". ");
            confirmedAppointments.get(i).prettyPrint(); // Assuming prettyPrint displays appointment details
        }

        // Step 3: Let the user select an appointment by index
        int selectedIndex = getValidatedIntInput(scanner, "Enter the index of the appointment: ", 1, confirmedAppointments.size()) - 1;
        Appointment selectedAppointment = confirmedAppointments.get(selectedIndex);

        // Step 4: Record consultation notes
        String consultationNotes = getValidatedStringInput(scanner, "Enter consultation notes: ", 300);

        String services = getValidatedStringInput(scanner, "Enter services provided: ", 30);

        // Step 5: Ask if medication is prescribed
        String prescribeMedication = getValidatedStringInput(scanner, "Would you like to prescribe medication? (yes/no): ", List.of("yes", "no"));
        String medication = "None";
        String dosage = "None";

        if (prescribeMedication.equalsIgnoreCase("yes")) {
            medication = getValidatedStringInput(scanner, "Enter prescribed medication (from inventory): " + InventoryController.getUniqueInventoryItems(), InventoryController.getUniqueInventoryItems());
            dosage = getValidatedStringInput(scanner, "Enter dosage: ", 100);

            // Step 6: Create a new prescription
            Prescription newPrescription = PrescriptionController.createNewPendingPrescription(
                    selectedAppointment.getDoctorId(),
                    selectedAppointment.getPatientId(),
                    medication,
                    dosage,
                    selectedAppointment.getId()
            );

            // Add the prescription to the repository
            if (PrescriptionController.addPrescription(newPrescription) != null) {
                System.out.println("Prescription created successfully.");
            } else {
                System.out.println("Failed to create prescription. Please try again.");
            }
        } else {
            System.out.println("No medication prescribed.");
        }

        // Step 7: Mark the appointment as done
        selectedAppointment.setStatus("Completed");
        if (AppointmentController.updateAppointment(selectedAppointment) != null) {
            System.out.println("Appointment marked as Completed.");
        } else {
            System.out.println("Failed to mark the appointment as Completed.");
            return;
        }

        // Step 8: Create a new AppointmentOutcome object
        AppointmentOutcome newOutcome = new AppointmentOutcome(
                selectedAppointment.getId(),
                selectedAppointment.getPatientId(),
                selectedAppointment.getDoctorId(),
                services,
                medication,
                consultationNotes
        );

        // Add the outcome to the repository
        if (AppointmentOutcomeController.createAppointmentOutcome(newOutcome) != null) {
            System.out.println("Appointment outcome recorded successfully.");
        } else {
            System.out.println("Failed to record appointment outcome. Please try again.");
        }
    }

}
