package Patient.View;

import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import Patient.Model.Patient;

import java.util.ArrayList;
import java.util.List;

import Patient.Repository.PatientRepository;
import User.Model.User;
import User.View.UserActions;

public class PatientActions {
    private Patient patient;
    private User user;
    private UserActions userActions;
    public PatientActions(User user){
        this.user = user;
        PatientRepository patientRepo = PatientRepository.getInstance("src/Data/Patient_List.csv");
        Patient foundPatient = patientRepo.read(user.getId());
        if (foundPatient == null){
            System.out.println("Patient not found");
            throw new IllegalArgumentException("Patient not found");
        }
        this.patient = foundPatient;
        this.userActions = new UserActions(user);
    }

    public List<MedicalRecord> getMedicalRecords(){
        MedicalRecordRepository repo = MedicalRecordRepository.getInstance("src/Data/MedicalRecords_List.csv");
        return repo.getByFilter((MedicalRecord record) -> record.getPatientId().equals(this.patient.getId()));
    }

    public boolean changePassword(String newPassword){
        return this.userActions.updatePassword(newPassword);
    }

}
