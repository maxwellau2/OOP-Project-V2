package MedicalRecord.Repository;

import Abstract.Repository;
import MedicalRecord.Model.MedicalRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static Util.RepositoryGetter.getMedicalRecordRepository;

public class MedicalRecordRepository extends Repository<MedicalRecord> {
    private static MedicalRecordRepository instance;

    private MedicalRecordRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<MedicalRecord>();
    }
    public static MedicalRecord createNewMedicalRecordToday(String patientId, String doctorId, String diagnosis, String treatment) {
        return new MedicalRecord(getMedicalRecordRepository().generateId(), patientId, doctorId, diagnosis, treatment, LocalDateTime.now());
    }

    public static MedicalRecordRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new MedicalRecordRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    @Override
    protected MedicalRecord fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length < 6) {
            throw new IllegalArgumentException("Invalid CSV line for MedicalRecord: " + csvLine);
        }

        String recordId = data[0];
        String patientId = data[1];
        String doctorId = data[2];
        String diagnosis = data[3];
        String treatment = data[4];
        LocalDateTime date = data[5].isEmpty() ? null : LocalDateTime.parse(data[5], DateTimeFormatter.ISO_DATE_TIME);

        return new MedicalRecord(recordId, patientId, doctorId, diagnosis, treatment, date);
    }

    @Override
    protected String getHeader() {
        return "recordId,patientId,doctorId,diagnosis,treatment,date";
    }
}
