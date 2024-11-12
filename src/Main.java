import Doctor.Controller.DoctorController;
import Doctor.View.DoctorView;
import Patient.Controller.PatientActions;
import Patient.Model.Patient;
import Patient.View.PatientView;
import User.Model.User;
import User.View.UserActions;
import Doctor.Model.Doctor;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // part 1: get credentials
        System.out.print("Enter your Hospital ID: ");
        Scanner scanner = new Scanner(System.in);
        String hospitalId = scanner.nextLine();

        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        User user = UserActions.login(hospitalId, password);
        if (user != null) {
            System.out.println("You have successfully logged in!");
            System.out.println(user);
        }
        if (user == null) return ;
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
        switch (user.getRole()){
            case "Patient":{
                Patient patient = PatientActions.createPatientFromUser(user);
                if (patient == null)
                    System.out.println("Patient could not be created!");
                System.out.println("You have successfully created patient!");
                PatientView patientView = new PatientView(patient);
                patientView.displayMenu();
                break;
            }
            case "Doctor":{
                System.out.println("You are a doctor");
                Doctor doctor = DoctorController.createDoctorFromUser(user);
                DoctorView view = new DoctorView(doctor);
                view.displayMenu();
                break;
            }
        }


    }
}