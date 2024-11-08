package User.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Abstract.Repository;
import User.Model.User;

public class UserRepository extends Repository<User> {
    private static UserRepository instance; // The singleton instance

    private UserRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>(); // Initialize the list of users
    }

    public static UserRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new UserRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    @Override
    protected User fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        User user = new User(data[0], data[1], data[2]);
        user.setBlacklist(Boolean.parseBoolean(data[3]));
        if (data.length >= 5 && !data[4].isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd format
            LocalDateTime dateTime = LocalDate.parse(data[4], formatter).atStartOfDay();
            user.setLastLogin(dateTime); // Set to midnight
        }
        return user;
    }

    @Override
    protected String getHeader() {
        return "hospitalID,passwordHash,role,isBlacklist,lastLogin";
    }
}
