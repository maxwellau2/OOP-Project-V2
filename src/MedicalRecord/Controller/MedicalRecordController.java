package MedicalRecord.Controller;

import MedicalRecord.Model.MedicalRecord;

import java.time.LocalDateTime;

import static Util.RepositoryGetter.getMedicalRecordRepository;

public class MedicalRecordController {
    public static MedicalRecord createNewMedicalRecordToday(String patientId, String doctorId, String diagnosis, String treatment) {
        return new MedicalRecord(getMedicalRecordRepository().generateId(), patientId, doctorId, diagnosis, treatment, LocalDateTime.now());
    }
}
