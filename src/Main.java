import Administrator.Controller.AdminActions;
import Administrator.Model.Admin;
import Administrator.View.AdminView;
import Doctor.Controller.DoctorController;
import Doctor.Model.Doctor;
import Doctor.View.DoctorView;
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

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // part 1: get credentials
        System.out.print("Enter your Hospital ID: ");
        String hospitalId = scanner.nextLine();

        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        User user = UserActions.login(hospitalId, password);
        if (user != null) {
            System.out.println("Successfully logged in as" + user.getRole());
        } else {
            System.out.println("Invalid credentials. Access denied.");
            return;
        }
        // check if they logged in before
//        if (user.getLastLogin() == null){
//            System.out.println("You have not logged in before! Change your password!");
//            String newPassword = scanner.nextLine();
//            User u = UserActions.updatePassword(user, newPassword);
//            if (u != null) {
//                System.out.println("You have successfully changed password!");
//            }
//        }
//        user = UserActions.updateLastLoginToNow(user);

        // look at role
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
        if (admin != null){
            System.out.println("Welcome Administrator.");
            AdminView adminView = new AdminView();
            adminView.displayMenu();
        }
        else System.out.println("Administrator profile creation failed");
    }

    private static void handlePharmacistRole(User user) {
        Pharmacist pharmacist = PharmacistActions.createPharmacistFromUser(user);
        if (pharmacist != null){
            System.out.println("Welcome Pharmacist.");
            PharmacistView pharmacistView = new PharmacistView();
            pharmacistView.displayMenu();            
        }
        else System.out.println("Pharmacist profile creation failed.");
    }


    
}