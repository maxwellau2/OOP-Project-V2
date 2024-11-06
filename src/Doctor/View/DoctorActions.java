package Doctor.View;

import Doctor.Model.Doctor;
import Doctor.Repository.DoctorRepository;
import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import User.Model.User;
import User.View.UserActions;
import java.util.List;

public class DoctorActions {
    private Doctor doctor;
    private UserActions userActions;
    public DoctorActions(User user) {
        DoctorRepository repo = DoctorRepository.getInstance("src/Data/Doctor_List.csv");
        Doctor doc = repo.read(user.getId());
        if (doc == null){
            System.out.println("Doctor not found");
        }
        this.userActions = new UserActions(user);
        this.doctor = doc;
    }

    public UserActions getUserActions() {
        return userActions;
    }

    public void setUserActions(UserActions userActions) {
        this.userActions = userActions;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<MedicalRecord> viewPatientRecord(){
        MedicalRecordRepository medRepo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        return medRepo.getByFilter((MedicalRecord record) -> record.getDoctorId().equals(this.doctor.getId()));
    }

    public void updatePatientRecord(MedicalRecord entity){
        MedicalRecordRepository repo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        repo.update(entity);
    }
}
