package Leaves.Repository;

import Abstract.Repository;
import Leaves.Model.Leave;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LeavesRepository extends Repository<Leave> {
    private static LeavesRepository instance;

    private LeavesRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
        this.load();
    }


    public static LeavesRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new LeavesRepository(csvPath);
        }
        return instance;
    }

    @Override
    protected Leave fromCSV(String csv) {
        String[] values = csv.split(",");
        LocalDateTime start = LocalDateTime.parse(values[3]);
        LocalDateTime end = LocalDateTime.parse(values[4]);
        return new Leave(values[0], values[1], values[2], start, end);
    }

    @Override
    protected String getHeader() {
        return "leaveId,staffId,leaveName,start,end";
    }
}
