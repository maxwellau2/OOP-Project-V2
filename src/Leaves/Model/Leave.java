package Leaves.Model;

import Interfaces.IEntity;

import java.time.LocalDateTime;

public class Leave implements IEntity {
    private String leaveId;
    private String staffId;
    private String leaveName;
    private LocalDateTime start;
    private LocalDateTime end;
    public Leave(String leaveId, String staffId, String leaveName, LocalDateTime start, LocalDateTime end) {
        this.leaveId = leaveId;
        this.staffId = staffId;
        this.leaveName = leaveName;
        this.start = start;
        this.end = end;
    }

    @Override
    public String getId() {
        return this.leaveId;
    }
    public String setId(String id) {
        return this.leaveId = id;
    }
    public String getLeaveName() {
        return this.leaveName;
    }
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }
    public LocalDateTime getStart() {
        return this.start;
    }
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    public LocalDateTime getEnd() {
        return this.end;
    }
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toCSV() {
        return leaveId + "," + staffId + "," + leaveName + "," + start + "," + end;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
