package Doctor.View;

import Doctor.Model.Doctor;

import java.util.Scanner;

public class DoctorView {
    Doctor doctor;
    Scanner scanner;
    public DoctorView(Doctor doctor){
        this.doctor = doctor;
        scanner = new Scanner(System.in);
    }
    
}
