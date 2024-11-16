package Staff.Controller;

import Staff.Model.Staff;

import static Util.RepositoryGetter.getStaffRepoInstance;

public class StaffActions {

    public static Staff getStaffById(String id){
        return getStaffRepoInstance().read(id);
    }
    public static boolean deleteStaffById(String id){
        return getStaffRepoInstance().deleteById(id);
    }
}
