package Patient.View;

import MedicalRecord.Model.MedicalRecord;
import MedicalRecord.Repository.MedicalRecordRepository;
import Patient.Model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientActions {
    Patient patient;
    public PatientActions(Patient patient){
        this.patient = patient;
    }

    public List<MedicalRecord> getMedicalRecords(){
        MedicalRecordRepository repo = MedicalRecordRepository.getInstance("src/Data/MedicalRecords_List.csv");

        return repo.getByFilter((MedicalRecord record) -> record.getPatientId().equals(this.patient.getId()));
    }

}
