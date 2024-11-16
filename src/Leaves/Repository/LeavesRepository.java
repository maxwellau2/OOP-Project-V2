package Leaves.Repository;

import Abstract.Repository;
import Leaves.Model.Leave;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Repository class for managing leave data.
 * The `LeavesRepository` class extends the abstract `Repository` to handle CRUD operations for `Leave` entities.
 */
public class LeavesRepository extends Repository<Leave> {

    private static LeavesRepository instance; // Singleton instance of the repository

    /**
     * Private constructor for initializing the repository with the CSV file path.
     *
     * @param csvPath The path to the CSV file containing leave data.
     */
    private LeavesRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
        this.load();
    }

    /**
     * Gets the singleton instance of the `LeavesRepository`.
     *
     * @param csvPath The path to the CSV file containing leave data.
     * @return The singleton instance of the repository.
     */
    public static LeavesRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new LeavesRepository(csvPath);
        }
        return instance;
    }

    /**
     * Converts a CSV-formatted string to a `Leave` object.
     *
     * @param csv A CSV-formatted string representing a leave entity.
     * @return A `Leave` object created from the CSV string.
     */
    @Override
    protected Leave fromCSV(String csv) {
        String[] values = csv.split(",");
        LocalDateTime start = LocalDateTime.parse(values[3]);
        LocalDateTime end = LocalDateTime.parse(values[4]);
        return new Leave(values[0], values[1], values[2], start, end);
    }

    /**
     * Returns the CSV header string for the `Leave` data.
     *
     * @return A string containing the header row for `Leave` data in CSV format.
     */
    @Override
    protected String getHeader() {
        return "leaveId,staffId,leaveName,start,end";
    }
}
