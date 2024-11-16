package Staff.Controller;

import Staff.Model.Staff;

import static Util.RepositoryGetter.getStaffRepoInstance;


/**
 * Controller class for managing actions related to {@link Staff}.
 * Provides methods for retrieving and deleting staff by their ID.
 */
public class StaffActions {


    /**
     * Retrieves a {@link Staff} member by their ID.
     *
     * @param id The unique ID of the staff member.
     * @return The {@link Staff} object if found, or null if no staff with the specified ID exists.
     */
    public static Staff getStaffById(String id){
        return getStaffRepoInstance().read(id);
    }

    /**
     * Deletes a {@link Staff} member by their ID.
     *
     * @param id The unique ID of the staff member to delete.
     * @return True if the staff member was successfully deleted, false otherwise.
     */
    public static boolean deleteStaffById(String id){
        return getStaffRepoInstance().deleteById(id);
    }
}
