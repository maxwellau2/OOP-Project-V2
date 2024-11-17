package Inventory.Repository;

import Abstract.Repository;
import Inventory.Model.Inventory;
import java.util.ArrayList;
import java.util.List;


/**
 * The InventoryRepository class provides methods to manage inventory items,
 * including CRUD operations, low stock checks, and restock requests.
 * This repository interacts with the data layer through a CSV file.
 */
public class InventoryRepository extends Repository<Inventory> {
    private static InventoryRepository instance;

    /**
     * Constructor to initialize the inventory repository.
     *
     * @param csvPath The path to the CSV file for inventory data storage.
     */
    public InventoryRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }


    /**
     * Retrieves the singleton instance of the InventoryRepository.
     *
     * @param csvPath The path to the CSV file for inventory data storage.
     * @return The singleton instance of the repository.
     */
    public static InventoryRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new InventoryRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    /**
     * Retrieves an inventory item by its medication name.
     *
     * @param medicationName The name of the medication to search for.
     * @return The Inventory object if found, otherwise null.
     */
    public Inventory getItemByName(String medicationName) {
        return entities.stream()
                .filter(item -> item.getMedicationName().equalsIgnoreCase(medicationName))
                .findFirst()
                .orElse(null);
    }


    /**
     * Retrieves a list of inventory items that are below the low stock alert threshold.
     *
     * @return A list of Inventory objects that are low on stock.
     */
    public List<Inventory> getLowStockItems() {
        return entities.stream()
                .filter(item -> item.getQuantity() < item.getLowStockAlert())
                .toList();
    }


    /**
     * Submits a restock request for a specific inventory item.
     *
     * @param item The inventory item to request a restock for.
     */
    public void submitRestockRequest(Inventory item) {
        System.out.println("Restock request submitted for item: " + item.getMedicationName());
        item.setRestockRequested("Requested");
        update(item);
    }


    /**
     * Decreases the quantity of a specified medication.
     *
     * @param medicationName The name of the medication to decrease stock for.
     * @param quantity The quantity to decrease.
     * @return True if the operation was successful, false otherwise.
     */
    public boolean decreaseQuantity(String medicationName, int quantity) {
        Inventory item = getItemByName(medicationName);
        if (item != null) {
            item.setQuantity(item.getQuantity()-quantity);
            update(item);
            store();
            return true;
        }
        return false;
    }


    /**
     * Converts a CSV line to an Inventory object.
     *
     * @param csvLine A CSV-formatted string representing an inventory item.
     * @return The Inventory object parsed from the CSV line.
     */
    @Override
    protected Inventory fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        int qty = Integer.parseInt(values[2]);
        int lowStockAlert = Integer.parseInt(values[3]);
        String restockRequested = values[4]; // Now a string
        return new Inventory(values[0], values[1], qty, lowStockAlert, restockRequested);
    }


    /**
     * Retrieves the header string for the inventory CSV file.
     *
     * @return The header string for the inventory CSV file.
     */
    @Override
    protected String getHeader() {
        return "id,medicationName,quantity,lowStockAlert,restockRequested";
    }
}
