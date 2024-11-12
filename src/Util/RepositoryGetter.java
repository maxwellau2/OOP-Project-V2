package Util;

import Appointment.Repository.AppointmentRepository;
import AppointmentOutcome.Repository.AppointmentOutcomeRepository;
import Doctor.Repository.DoctorRepository;
import Prescription.Repository.PrescriptionRepository;

public class RepositoryGetter {


    public static DoctorRepository getDoctorRepository() {
        return DoctorRepository.getInstance("src/Data/Doctor_List.csv");
    }

    public static PrescriptionRepository initPrescriptionRepository() {
        return PrescriptionRepository.getInstance("src/Data/Prescription_List.csv");
    }


    public static AppointmentRepository getAppointmentRepository() {
        return AppointmentRepository.getInstance("src/Data/Appointment_List.csv");
    }


    public static AppointmentOutcomeRepository getAppointmentOutcomeRepository() {
        return AppointmentOutcomeRepository.getInstance("src/Data/AppointmentOutcome_List.csv");
    }
}
