package Doctor;

import Doctor.Model.Doctor;
import Doctor.Repository.DoctorRepository;

public class Test {
    public static void main(String[] args) {
        String csvPath = "test2.csv";
        // create the doctors
        Doctor doc1 = new Doctor("123", "doc1", 69, "male", "ortho");
        Doctor doc2 = new Doctor("124", "doc2", 42, "female", "psych");
        // create the doctor repo
        DoctorRepository repo = DoctorRepository.getInstance(csvPath);
        repo.load();
//        repo.create(doc1);
//        repo.create(doc2);
        repo.display();
        repo.store();
    }
}
