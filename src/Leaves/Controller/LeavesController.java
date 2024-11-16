package Leaves.Controller;

import Leaves.Model.Leave;
import Leaves.Repository.LeavesRepository;

import java.time.LocalDateTime;
import java.util.List;

import static Util.RepositoryGetter.getLeavesRepository;

/**
 * Controller class for managing leave-related operations.
 * The `LeavesController` provides methods for interacting with the `LeavesRepository`
 * and performing operations such as retrieving, creating, and deleting leave records.
 */
public class LeavesController {

    /**
     * Retrieves all leave records from the repository.
     *
     * @return A list of all `Leave` objects.
     */
    public static List<Leave> getAllLeaves() {
        return getLeavesRepository().getAll();
    }

    /**
     * Retrieves leave records for a specific staff member.
     *
     * @param id The ID of the staff member.
     * @return A list of `Leave` objects associated with the given staff member.
     */
    public static List<Leave> getStaffLeave(String id) {
        return getLeavesRepository().getByFilter(l -> l.getStaffId().equals(id));
    }

    /**
     * Retrieves leave records for a specific staff member starting after a specified date.
     *
     * @param id    The ID of the staff member.
     * @param start The start date to filter leave records.
     * @return A list of `Leave` objects for the staff member starting after the given date.
     */
    public static List<Leave> getStaffLeave(String id, LocalDateTime start) {
        return getLeavesRepository().getByFilter(l -> (l.getStaffId().equals(id) && l.getStart().isAfter(start)));
    }

    /**
     * Creates a new `Leave` object without adding it to the repository.
     *
     * @param staffId    The ID of the staff member taking the leave.
     * @param leaveName  The name or description of the leave.
     * @param start      The start date and time of the leave.
     * @param end        The end date and time of the leave.
     * @return A new `Leave` object.
     */
    public static Leave createLeave(String staffId, String leaveName, LocalDateTime start, LocalDateTime end) {
        return new Leave(getLeavesRepository().generateId(), staffId, leaveName, start, end);
    }

    /**
     * Adds a new leave record to the repository.
     *
     * @param newLeave The `Leave` object to be added.
     * @return The added `Leave` object if successful, or `null` if the operation failed.
     */
    public static Leave addLeave(Leave newLeave) {
        return getLeavesRepository().create(newLeave);
    }

    /**
     * Removes a leave record from the repository.
     *
     * @param leave The `Leave` object to be removed.
     * @return The removed `Leave` object if successful, or `null` if the operation failed.
     */
    public static Leave removeLeave(Leave leave) {
        return getLeavesRepository().delete(leave);
    }
}

