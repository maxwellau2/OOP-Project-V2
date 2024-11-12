package Doctor.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import Appointment.Controller.AppointmentController;
import Appointment.Model.Appointment;
import Leaves.Controller.LeavesController;
import Leaves.Model.Leave;
import Patient.Model.Patient;
import Doctor.Controller.DoctorController;
import Doctor.Model.Doctor;
import MedicalRecord.Model.MedicalRecord;
import Patient.Controller.PatientActions;
import static Util.SafeScanner.getValidatedIntInput;

public class DoctorView {
    private Doctor doctor;
    private Scanner scanner = new Scanner(System.in);

    public DoctorView(Doctor doctor) {
        this.doctor = doctor;
    }

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

            choice = getValidatedIntInput(scanner,"Enter your choice: ", 0, 7);
            scanner.nextLine(); // Clear the buffer after an integer input


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
                case 4:
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
            }
        } while (choice != 0);

        scanner.close();
    }

    // Test Case 9: View Patient Medical Records
    public void viewPatientMedicalRecords() {
        System.out.println("=== View Patient Medical Records ===");
        List<MedicalRecord> records = DoctorController.viewPatientRecord(doctor);
        if (records != null && records.isEmpty()) {
            System.out.println("There are no patient medical records under you.");
        } else{
            for (MedicalRecord record : records) {
                record.prettyPrintMedicalRecord();
            }
        }
        // Logic to be implemented
    }

    // Test Case 10: Update Patient Medical Records
    public void updatePatientMedicalRecords() {
        System.out.println("=== Update Patient Medical Records ===");

        // Step 1: Prompt the doctor to enter the patient ID
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        // Step 2: Check if the patient exists (optional step to verify)
        Patient patient = PatientActions.getPatientById(patientId);
        if (patient == null) {
            System.out.println("No patient found with ID: " + patientId);
            return;
        }

        // Step 3: Prompt the doctor to enter the new medical record details
        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();

        System.out.print("Enter Treatment Plan: ");
        String treatmentPlan = scanner.nextLine();

        // Step 4: Create a new MedicalRecord object
        MedicalRecord newRecord = MedicalRecord.createNewMedicalRecordToday(patientId, doctor.getId(), diagnosis, treatmentPlan);
        MedicalRecord record = DoctorController.addPatientRecord(newRecord);
        // null check
        if (record != null) {
            System.out.println("Patient Medical Records updated successfully.");
            return;
        }
        System.out.println("Patient Medical Records update failed.");
    }


    // Stub for Test Case 11: View Personal Schedule
    public void viewPersonalSchedule() {
        System.out.println("=== View Personal Schedule ===");
        // call Appointment controller stuff
        List<Appointment> appointments = AppointmentController.getAppointmentByDoctor(doctor.getId(), LocalDateTime.now().toLocalDate().atStartOfDay(), 3);
        for (Appointment appointment : appointments) {
            appointment.prettyPrint();
        }
    }

    // Stub for Test Case 12: Set Availability for Appointments
    public void setAppointmentAvailability() {
        System.out.println("=== Set Availability for Appointments ===");

        boolean continueSettingAvailability = true;
        while (continueSettingAvailability) {
            System.out.println("1. Add Leave");
            System.out.println("2. Remove Leave");
            System.out.println("0. Back to Main Menu");
            int choice = getValidatedIntInput(scanner, "Choose an option: ", 0, 2);
            scanner.nextLine(); // Clear the buffer after an integer input

            switch (choice) {
                case 1 -> addLeave();
                case 2 -> removeLeave();
                case 0 -> continueSettingAvailability = false;
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void addLeave() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("Enter the leave name (e.g., Vacation, Conference): ");
        String leaveName = scanner.nextLine();

        LocalDateTime start;
        while (true) {
            System.out.print("Enter leave start date and time (yyyy-MM-dd HH:mm): ");
            String startDateTimeStr = scanner.nextLine();
            try {
                start = LocalDateTime.parse(startDateTimeStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in yyyy-MM-dd HH:mm format.");
            }
        }

        LocalDateTime end;
        while (true) {
            System.out.print("Enter leave end date and time (yyyy-MM-dd HH:mm): ");
            String endDateTimeStr = scanner.nextLine();
            try {
                end = LocalDateTime.parse(endDateTimeStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in yyyy-MM-dd HH:mm format.");
            }
        }

        // Create a new leave using the LeavesController
        Leave newLeave = LeavesController.createLeave(doctor.getId(), leaveName, start, end);

        // Add the leave to the repository
        if (LeavesController.addLeave(newLeave) != null) {
            System.out.println("Leave added successfully.");
        } else {
            System.out.println("Failed to add leave.");
        }
    }


    private void removeLeave() {
        System.out.println("Existing leaves:");
        List<Leave> leaves = LeavesController.getStaffLeave(doctor.getId());
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


    // Stub for Test Case 13: Accept or Decline Appointment Requests
    public void handleAppointmentRequests() {
        System.out.println("=== Accept or Decline Appointment Requests ===");
        // Logic to be implemented
    }

    // Stub for Test Case 14: View Upcoming Appointments
    public void viewUpcomingAppointments() {
        System.out.println("=== View Upcoming Appointments ===");
        // Logic to be implemented
    }

    // Stub for Test Case 15: Record Appointment Outcome
    public void recordAppointmentOutcome() {
        System.out.println("=== Record Appointment Outcome ===");
        // Logic to be implemented
    }

}
