package User.Repository;

import Abstract.Repository;
import User.Model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Repository for managing {@link User} entities.
 * This class provides CRUD operations for users and handles data persistence via a CSV file.
 */
public class UserRepository extends Repository<User> {
    private static UserRepository instance; // The singleton instance

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param csvPath Path to the CSV file used for storing user data.
     */
    private UserRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>(); // Initialize the list of users
    }


    /**
     * Retrieves the singleton instance of {@link UserRepository}.
     *
     * @param csvPath Path to the CSV file used for storing user data.
     * @return The singleton instance of {@link UserRepository}.
     */
    public static UserRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new UserRepository(csvPath);
            instance.load();
        }
        return instance;
    }


    /**
     * Converts a CSV line into a {@link User} object.
     *
     * @param csvLine A line of CSV data representing a user.
     * @return A {@link User} object parsed from the CSV data.
     */
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

    /**
     * Provides the header string for the CSV file.
     *
     * @return A string representing the header for user CSV data.
     */
    @Override
    protected String getHeader() {
        return "hospitalID,passwordHash,role,isBlacklist,lastLogin";
    }
}
