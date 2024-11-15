import Administrator.Controller.AdminActions;
import Administrator.Model.Admin;
import Administrator.View.AdminView;
import Doctor.Controller.DoctorController;
import Doctor.Model.Doctor;
import Doctor.View.DoctorView;
import Leaves.Controller.LeavesController;
import Patient.Controller.PatientActions;
import Patient.Model.Patient;
import Patient.View.PatientView;
import Pharmacist.Controller.PharmacistActions;
import Pharmacist.Model.Pharmacist;
import Pharmacist.View.PharmacistView;
import User.Model.User;
import User.Model.UserRole;
import User.View.UserActions;

import java.util.Scanner;

import static Util.SafeScanner.getValidatedStringInput;

public class Main {
    public static void main(String[] args) {
        // Part 1: Get credentials
        String hospitalId = getValidatedStringInput(new Scanner(System.in), "Enter your Hospital ID: ", 50);
        String password = getValidatedStringInput(new Scanner(System.in), "Enter your Password: ", 50);

        User user = UserActions.login(hospitalId, password);
        if (user != null) {
            System.out.println("Successfully logged in as " + user.getRole());
        } else {
            System.out.println("Invalid credentials. Access denied.");
            return;
        }

        // Look at role
        UserRole role = UserRole.fromString(user.getRole());
        switch (role) {
            case PATIENT -> handlePatientRole(user);
            case DOCTOR -> handleDoctorRole(user);
            case ADMIN -> handleAdminRole(user);
            case PHARMACIST -> handlePharmacistRole(user);
            default -> System.out.println("Invalid role. Contact administration.");
        }
    }

    private static void handlePatientRole(User user) {
        Patient patient = PatientActions.createPatientFromUser(user);
        if (patient != null) {
            System.out.println("Welcome, " + patient.getName());
            PatientView patientView = new PatientView(patient);
            patientView.displayMenu();
        } else {
            System.out.println("Patient profile creation failed.");
        }
    }

    private static void handleDoctorRole(User user) {
        Doctor doctor = DoctorController.createDoctorFromUser(user);
        if (doctor != null) {
            System.out.println("Welcome, Dr. " + doctor.getName());
            DoctorView doctorView = new DoctorView(doctor);
            doctorView.displayMenu();
        } else {
            System.out.println("Doctor profile creation failed.");
        }
    }

    private static void handleAdminRole(User user) {
        Admin admin = AdminActions.createAdminFromUser(user);
        if (admin != null) {
            System.out.println("Welcome Administrator.");
            AdminView adminView = new AdminView();
            adminView.displayMenu();
        } else {
            System.out.println("Administrator profile creation failed.");
        }
    }

    private static void handlePharmacistRole(User user) {
        Pharmacist pharmacist = PharmacistActions.createPharmacistFromUser(user);
        if (pharmacist != null) {
            System.out.println("Welcome " + pharmacist.getName());
            PharmacistView pharmacistView = new PharmacistView();
            pharmacistView.displayMenu();
        } else {
            System.out.println("Pharmacist profile creation failed.");
        }
    }
}
