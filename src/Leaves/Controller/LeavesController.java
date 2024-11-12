package Leaves.Controller;

import Leaves.Model.Leave;
import Leaves.Repository.LeavesRepository;

import java.time.LocalDateTime;
import java.util.List;

import static Util.RepositoryGetter.getLeavesRepository;

public class LeavesController {
    public static List<Leave> getAllLeaves() {
        return getLeavesRepository().getAll();
    }
    public static List<Leave> getStaffLeave(String id){
        return getLeavesRepository().getByFilter(l->l.getStaffId().equals(id));
    }
    public static List<Leave> getStaffLeave(String id, LocalDateTime start, LocalDateTime end){
        return getLeavesRepository().getByFilter(l->(l.getStaffId().equals(id)
                && l.getStart().isAfter(start)
                && l.getEnd().isBefore(end)));
    }

    public static Leave createLeave(String staffId, String leaveName, LocalDateTime start, LocalDateTime end){
        return new Leave(getLeavesRepository().generateId(), staffId, leaveName, start, end);
    }

    // Add a new leave for the doctor
    public static Leave addLeave(Leave newLeave) {
        return getLeavesRepository().create(newLeave);
    }

    // Remove a leave by its ID
    public static Leave removeLeave(Leave leave) {
        return getLeavesRepository().delete(leave);
    }

}
