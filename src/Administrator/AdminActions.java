package Administrator;

import Staff.Repository.StaffRepository;

import java.util.List;

import Staff.Model.Staff;

public class AdminActions{
    public static void main (String[] args){
//        System.out.println(AdminActions.getAllStaff());
        updateStaff();
    }

    private static StaffRepository getStaffRepoInstance(){
        StaffRepository repo = StaffRepository.getInstance("src/Data/Staff_List.csv");
        return repo;
    }
    public static List<Staff> getAllStaff(){
        StaffRepository repo = getStaffRepoInstance();
        return repo.getAll();
    }
    public static boolean addStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        return repo.create(staff);
    }
    public static Staff updateStaff(Staff staff){
        StaffRepository repo = getStaffRepoInstance();
        repo.update(staff);
        return staff;
    }
}