package Doctor.View;

import java.util.List;
import java.util.Scanner;

import Doctor.Controller.DoctorActions;
import Doctor.Model.Doctor;
import MedicalRecord.Model.MedicalRecord;

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
        List<MedicalRecord> records = DoctorActions.viewPatientRecord(doctor);
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

        // Logic to be implemented
    }

    // Stub for Test Case 11: View Personal Schedule
    public void viewPersonalSchedule() {
        System.out.println("=== View Personal Schedule ===");
        // Logic to be implemented
    }

    // Stub for Test Case 12: Set Availability for Appointments
    public void setAppointmentAvailability() {
        System.out.println("=== Set Availability for Appointments ===");
        // Logic to be implemented
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
