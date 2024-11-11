package Prescription.Repository;

import Abstract.Repository;
import Prescription.Model.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository extends Repository<Prescription> {
    private static PrescriptionRepository instance;

    public PrescriptionRepository(String csvPath) {
        super(csvPath); 
        this.entities = new ArrayList<Prescription>();
    }
    public static PrescriptionRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new PrescriptionRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    @Override
    protected Prescription fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length == 6) {
            return new Prescription(data[0], data[1], data[2], data[3], data[4], data[5]);
        }
        return null;
    }

    @Override
    protected String getHeader() {
        return "prescriptionId,doctorId,patientId,medicationName,dosage,status";
    }


    // Find prescriptions by patientId
    public List<Prescription> getByPatientId(String patientId) {
        return this.getByFilter(prescription -> prescription.getPatientId().equals(patientId));
    }

    // Find prescriptions by doctorId
    public List<Prescription> getByDoctorId(String doctorId) {
        return this.getByFilter(prescription -> prescription.getDoctorId().equals(doctorId));
    }

    // Find prescriptions by status (e.g., pending, dispensed)
    public List<Prescription> getByStatus(String status) {
        return this.getByFilter(prescription -> prescription.getStatus().equalsIgnoreCase(status));
    }
}
