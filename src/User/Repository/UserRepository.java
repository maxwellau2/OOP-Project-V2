package User.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Abstract.Repository;
import User.Model.User;

public class UserRepository extends Repository<User> {

    public UserRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>(); // Initialize the list of users
    }

    @Override
    protected User fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        User user = new User(data[0], data[1], data[2]);
        user.setBlacklist(Boolean.parseBoolean(data[3]));
        if (!data[4].isEmpty()) {
            user.setLastLogin(LocalDateTime.parse(data[4], DateTimeFormatter.ISO_DATE_TIME));
        }
        return user;
    }

    @Override
    protected String getHeader() {
        return "hospitalID,passwordHash,role,isBlacklist,lastLogin";
    }
}
