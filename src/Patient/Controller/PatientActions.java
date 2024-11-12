package Patient.Controller;

import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import Patient.Model.Patient;
import java.util.List;

import Patient.Repository.PatientRepository;
import User.Model.User;
import User.View.UserActions;

// todo: create auth controller

public class PatientActions {

    private static PatientRepository initPatientRepository() {
        return PatientRepository.getInstance("src/Data/Patient_List.csv");
    }

    private static MedicalRecordRepository initMedicalRecordRepository() {
        return MedicalRecordRepository.getInstance("src/Data/MedicalRecords_List.csv");
    }

    public static List<MedicalRecord> getMedicalRecords(Patient patient){
        MedicalRecordRepository repo = initMedicalRecordRepository();
        return repo.getByFilter((MedicalRecord record) -> record.getPatientId().equals(patient.getId()));
    }

    public static Patient changePassword(Patient patient, String newPassword){
        patient.setUser(UserActions.updatePassword(patient.getUser(), newPassword));
        User u = patient.getUser();
        u = UserActions.updatePassword(u, newPassword);
        patient.setUser(u);
        return patient;
    }

    // probably put under user actions is btr
    public static Patient createPatientFromUser(User user){
        PatientRepository repo = initPatientRepository();
//        repo.display();
        Patient patient = repo.read(user.getId());
        if (patient == null) {
            System.out.println("Patient not found");
            return null;
        }
        patient.setUser(user);
        return patient;
    }

    public static Patient updatePatient(Patient patient){
        PatientRepository repo = initPatientRepository();
        return repo.update(patient);
    }

    public static Patient getPatientById(String patientId) {
        return initPatientRepository().read(patientId);
    }
}
