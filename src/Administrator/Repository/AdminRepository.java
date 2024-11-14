package Administrator.Repository;

import Administrator.Model.Admin;
import Abstract.Repository;

import java.util.ArrayList;

public class AdminRepository extends Repository<Admin> {
    private static AdminRepository instance = null;

    public static AdminRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new AdminRepository(csvPath);
            instance.load(); 
        }
        return instance;
    }

    // Constructor
    private AdminRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Admin>();  
    }

    @Override
    protected Admin fromCSV(String csv) {
        String[] values = csv.split(",");
        String id = values[0];
        String name = values[1];
        String gender = values[2];
        int age = Integer.parseInt(values[3]);
        String phoneNumber = values[4];
        return new Admin(id, name, gender, age, phoneNumber);
    }

    @Override
    protected String getHeader() {
        return "id,name,gender,age,phoneNumber";
    }
}
