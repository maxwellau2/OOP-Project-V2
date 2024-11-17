package Leaves.Model;

import Interfaces.IEntity;

import java.time.LocalDateTime;


/**
 * Represents a leave entity for hospital staff.
 * The Leave class implements IEntity and encapsulates details about a staff member's leave.
 */
public class Leave implements IEntity {
    private String leaveId;         // Unique identifier for the leave
    private String staffId;         // Identifier of the staff member associated with the leave
    private String leaveName;       // Name or description of the leave (e.g., Vacation, Conference)
    private LocalDateTime start;    // Start date and time of the leave
    private LocalDateTime end;      // End date and time of the leave

    /**
     * Constructs a new Leave object with the specified details.
     *
     * @param leaveId   The unique identifier for the leave.
     * @param staffId   The ID of the staff member taking the leave.
     * @param leaveName The name or description of the leave.
     * @param start     The start date and time of the leave.
     * @param end       The end date and time of the leave.
     */
    public Leave(String leaveId, String staffId, String leaveName, LocalDateTime start, LocalDateTime end) {
        this.leaveId = leaveId;
        this.staffId = staffId;
        this.leaveName = leaveName;
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the unique identifier of the leave.
     *
     * @return The leave ID.
     */
    @Override
    public String getId() {
        return this.leaveId;
    }

    /**
     * Sets the unique identifier of the leave.
     *
     * @param id The new leave ID.
     * @return The updated leave ID.
     */
    public String setId(String id) {
        return this.leaveId = id;
    }

    /**
     * Gets the name or description of the leave.
     *
     * @return The leave name.
     */
    public String getLeaveName() {
        return this.leaveName;
    }

    /**
     * Sets the name or description of the leave.
     *
     * @param leaveName The new leave name.
     */
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    /**
     * Gets the start date and time of the leave.
     *
     * @return The leave's start time.
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Sets the start date and time of the leave.
     *
     * @param start The new start time.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the end date and time of the leave.
     *
     * @return The leave's end time.
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Sets the end date and time of the leave.
     *
     * @param end The new end time.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Converts the leave object to a CSV-formatted string.
     *
     * @return A CSV representation of the leave.
     */
    @Override
    public String toCSV() {
        return leaveId + "," + staffId + "," + leaveName + "," + start + "," + end;
    }

    /**
     * Gets the ID of the staff member associated with this leave.
     *
     * @return The staff ID.
     */
    public String getStaffId() {
        return staffId;
    }

    /**
     * Sets the ID of the staff member associated with this leave.
     *
     * @param staffId The new staff ID.
     */
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    /**
     * Converts the leave object to a string representation.
     *
     * @return A string representation of the leave object.
     */
    @Override
    public String toString() {
        return "{ id: " + this.leaveId +
                ", staffId: " + this.staffId +
                ", leaveName: " + this.leaveName +
                ", start: " + this.start +
                ", end: " + this.end + " }";
    }

    /**
     * Prints the leave details in a formatted, human-readable manner.
     */
    public void prettyPrint() {
        System.out.println("Leave Details:");
        System.out.println("==============");
        System.out.println("Leave ID    : " + this.leaveId);
        System.out.println("Staff ID    : " + this.staffId);
        System.out.println("Leave Name  : " + this.leaveName);
        System.out.println("Start Time  : " + this.start);
        System.out.println("End Time    : " + this.end);
        System.out.println("==============");
    }

}
