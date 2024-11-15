package Prescription.Repository;

import Abstract.Repository;
import Prescription.Model.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepository extends Repository<Prescription> {
    private static PrescriptionRepository instance;

    private PrescriptionRepository(String csvPath) { // make sure constructor is private
        super(csvPath); 
        this.entities = new ArrayList<Prescription>();
        this.load();
    }
    public static PrescriptionRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new PrescriptionRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    public Prescription createPrescription(Prescription prescription){
        // returns a prescription if is unique, else none
        if (prescription.getId() == null){
            prescription.setId(this.generateId());
        }
        if (this.create(prescription) != null){
            return prescription;
        }
        return null;
    }

    @Override
    protected Prescription fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length == 7) {
            return new Prescription(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
        }
        return null;
    }

    @Override
    protected String getHeader() {
        return "prescriptionId,doctorId,patientId,medicationName,dosage,status,appointmentId";
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

    // update prescription status
    public Prescription updatePrescriptionStatus(Prescription prescription, String status) {
        prescription.setStatus(status);
        return this.update(prescription);
    }
}
