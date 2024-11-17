package Pharmacist.Repository;

import Pharmacist.Model.Pharmacist;
import Abstract.Repository;

import java.util.ArrayList;

import static Util.RepositoryGetter.getPharmacistRepository;



/**
 * Repository class for managing {@link Pharmacist} entities.
 * Extends the {@link Abstract.Repository} class to provide common repository functionalities.
 */
public class PharmacistRepository extends Repository<Pharmacist> {
    private static PharmacistRepository instance = null;

    /**
     * Private constructor to initialize the repository with a specified CSV path.
     *
     * @param csvPath The path to the CSV file for storing pharmacist data.
     */
    private PharmacistRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }


    /**
     * Retrieves the singleton instance of the PharmacistRepository.
     * Initializes the repository if it hasn't been created yet.
     *
     * @param csvPath The path to the CSV file for storing pharmacist data.
     * @return The singleton instance of the repository.
     */
    public static PharmacistRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new PharmacistRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    /**
     * Parses a CSV line into a {@link Pharmacist} object.
     *
     * @param csvLine A string in CSV format representing a pharmacist.
     * @return A {@link Pharmacist} object constructed from the CSV data.
     */
    @Override
    protected Pharmacist fromCSV(String csvLine){
        String[] data = csvLine.split(",");
        return new Pharmacist(data[0], data[1], Integer.valueOf(data[2]), data[3]);
    }


    /**
     * Provides the header for the CSV file storing pharmacist data.
     *
     * @return A string representing the CSV header.
     */
    @Override
    protected String getHeader(){
        return "id,name,qualification";
    }


    /**
     * Factory method for creating a new {@link Pharmacist} entity.
     * Generates a unique ID for the pharmacist and stores it in the repository.
     *
     * @param name   The name of the pharmacist.
     * @param age    The age of the pharmacist.
     * @param gender The gender of the pharmacist.
     * @return The newly created {@link Pharmacist} object.
     */
    public static Pharmacist createPharmacist(String name, Integer age, String gender){
        Pharmacist pharmacist = new Pharmacist(getPharmacistRepository().generateId(), name, age, gender);
        return getPharmacistRepository().create(pharmacist);
    }
}



