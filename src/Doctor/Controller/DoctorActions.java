package Doctor.Controller;

import Doctor.Model.Doctor;
import Doctor.Repository.DoctorRepository;
import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;

import java.util.List;

public class DoctorActions {

    public DoctorRepository getDoctorRepository() {
        return DoctorRepository.getInstance("src/Data/Doctor_List.csv");
    }

    public List<MedicalRecord> viewPatientRecord(Doctor doctor){
        MedicalRecordRepository medRepo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        return medRepo.getByFilter((MedicalRecord record) -> record.getDoctorId().equals(doctor.getId()));
    }

    public void updatePatientRecord(MedicalRecord entity){
        MedicalRecordRepository repo = MedicalRecordRepository.getInstance("src/Data/MedicalRecord_List.csv");
        repo.update(entity);
    }
}
