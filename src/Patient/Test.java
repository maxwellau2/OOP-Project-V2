package Patient;

import Patient.Controller.PatientActions;
import Patient.Model.Patient;
import Patient.Repository.PatientRepository;
import User.View.UserActions;
import User.Model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        User user = UserActions.login("P001", "password");
        System.out.println(user);
        if (user == null){
            System.out.println("Login Failed");
        }

        Patient patient = PatientActions.createPatientFromUser(user);
        if (patient == null){
            System.out.println("Login Failed");
        }
        String id = patient.getId();
        System.out.println(patient);
        patient.setName("jonathan tan");
        patient.setDob(LocalDateTime.now());
        Patient newpatient = PatientActions.updatePatient(patient);
        if (newpatient == null){
            System.out.println("Update Failed");
        }
        System.out.println(newpatient);

        System.out.println("Checking DB");
        Patient p2 = PatientActions.createPatientFromUser(user);
        System.out.println(p2);
    }
}
