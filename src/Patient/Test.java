package Patient;

import Patient.Controller.PatientController;
import Patient.Model.Patient;
import User.Controller.UserController;
import User.Model.User;

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        User user = UserController.login("P001", "password");
        System.out.println(user);
        if (user == null){
            System.out.println("Login Failed");
        }

        Patient patient = PatientController.createPatientFromUser(user);
        if (patient == null){
            System.out.println("Login Failed");
        }
        String id = patient.getId();
        System.out.println(patient);
        patient.setName("jonathan tan");
        patient.setDob(LocalDateTime.now());
        Patient newpatient = PatientController.updatePatient(patient);
        if (newpatient == null){
            System.out.println("Update Failed");
        }
        System.out.println(newpatient);

        System.out.println("Checking DB");
        Patient p2 = PatientController.createPatientFromUser(user);
        System.out.println(p2);
    }
}
