import Administrator.Controller.AdminController;
import Administrator.Model.Admin;
import Administrator.View.AdminView;
import Doctor.Controller.DoctorController;
import Doctor.Model.Doctor;
import Doctor.View.DoctorView;
import Patient.Controller.PatientController;
import Patient.Model.Patient;
import Patient.View.PatientView;
import Pharmacist.Controller.PharmacistController;
import Pharmacist.Model.Pharmacist;
import Pharmacist.View.PharmacistView;
import User.Model.User;
import User.Model.UserRole;
import User.Controller.UserController;

import java.util.Scanner;

import static Util.SafeScanner.*;


/**
 * Main class for the hospital management system.
 * It handles user authentication, role-based redirection, and initializes the respective views.
 */
public class Main {
    public static void main(String[] args) {
        // Part 1: Get credentials
        String hospitalId = getValidatedStringInput(new Scanner(System.in), "Enter your Hospital ID: ", 50);
        String password = readPasswordMasked(new Scanner(System.in), "Enter your Password: ");

        User user = UserController.login(hospitalId, password);
        if (user != null) {
            System.out.println("Successfully logged in as " + user.getRole());
        } else {
            System.out.println("Invalid credentials. Access denied.");
            return;
        }

        // look at blacklist
        if (user.isBlacklist()){
            System.out.println("You are blacklisted. Good bye!");
        }

//        System.out.println(user);

//         look at last login, if last login is null, prompt to change password
        if (user.getLastLogin() == null){
            // user never login in before
            String newPassword = getStrongPassword(new Scanner(System.in), "You never logged in before, Please change your password: ");
//            String newPassword = getStrongPasswordHidden("You never logged in before, Please change your password: ");
            user = UserController.updatePassword(user,newPassword);
            if (user == null){
                System.out.println("Something went wrong, please try again.");
                return;
            }
        }
        user = UserController.updateLastLoginToNow(user);
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

    /**
     * Handles redirection for users with the Patient role.
     *
     * @param user the logged-in user
     */
    private static void handlePatientRole(User user) {
        Patient patient = PatientController.createPatientFromUser(user);
        if (patient != null) {
            System.out.println("Welcome, " + patient.getName());
            PatientView patientView = new PatientView(patient);
            patientView.displayMenu();
        } else {
            System.out.println("Patient profile creation failed.");
        }
    }

    /**
     * Handles redirection for users with the Doctor role.
     *
     * @param user the logged-in user
     */
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

    /**
     * Handles redirection for users with the Administrator role.
     *
     * @param user the logged-in user
     */
    private static void handleAdminRole(User user) {
        Admin admin = AdminController.createAdminFromUser(user);
        if (admin != null) {
            System.out.println("Welcome Administrator.");
            AdminView adminView = new AdminView(admin);
            adminView.displayMenu();
        } else {
            System.out.println("Administrator profile creation failed.");
        }
    }


    /**
     * Handles redirection for users with the Pharmacist role.
     *
     * @param user the logged-in user
     */
    private static void handlePharmacistRole(User user) {
        Pharmacist pharmacist = PharmacistController.createPharmacistFromUser(user);
        if (pharmacist != null) {
            System.out.println("Welcome " + pharmacist.getName());
            PharmacistView pharmacistView = new PharmacistView();
            pharmacistView.displayMenu();
        } else {
            System.out.println("Pharmacist profile creation failed.");
        }
    }
}
