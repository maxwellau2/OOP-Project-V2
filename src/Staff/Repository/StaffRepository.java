package Staff.Repository;
import Staff.Model.Staff;
import Abstract.Repository;

import java.util.ArrayList;
import java.util.List;

public class StaffRepository extends Repository<Staff> {
    private static StaffRepository instance = null;
    private StaffRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Staff>();
        this.load();
    }

public static StaffRepository getInstance(String csvPath){
        if (instance == null){
            instance = new StaffRepository(csvPath);
        }
        return instance;
    }

    @Override
    protected Staff fromCSV(String csv) {
        String[] values = csv.split(",");
        int age = Integer.parseInt(values[4]);
        return new Staff(values[0], values[1], values[2], values[3], age);
    }

    @Override
    protected String getHeader() {
        return "id,name,role,gender,age";
    }
}
