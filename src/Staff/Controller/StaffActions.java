package Staff.Controller;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;

import java.util.List;

public class StaffActions {
    public static void main(String[] args) {
        StaffRepository repo = StaffRepository.getInstance("src/Data/Staff_List.csv");
        List<Staff> s = repo.getAll();
        System.out.println(s);
    }
}
