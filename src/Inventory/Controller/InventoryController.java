package Inventory.Controller;

import Inventory.Model.Inventory;
import java.util.List;

import static Util.RepositoryGetter.getInventoryRepoInstance;


/**
 * The InventoryController class provides static methods for managing inventory data.
 * It interacts with the InventoryRepository to perform CRUD operations and manage inventory items.
 */

public class InventoryController {

    /**
     * Retrieves a list of unique medication names from the inventory.
     *
     * @return A list of unique medication names in the inventory.
     */
    public static List<String> getUniqueInventoryItems() {
        return getInventoryRepoInstance()
                .getAll()
                .stream()
                .map(Inventory::getMedicationName)
                .distinct()
                .toList();
    }

    /**
     * Retrieves all inventory items.
     *
     * @return A list of all Inventory objects in the repository.
     */
    public static List<Inventory> getAllInventory() {
        return getInventoryRepoInstance().getAll();
    }

    /**
     * Retrieves inventory items with a "Pending" restock request status.
     *
     * @return A list of Inventory objects with a pending restock request.
     */
    public static List<Inventory> getLowStockInventoryPending() {
        return getInventoryRepoInstance().getByFilter(inventory ->
                ("Pending".equalsIgnoreCase(inventory.getRestockRequested())));
    }


    /**
     * Updates an existing inventory item.
     *
     * @param inventory The Inventory object with updated data.
     * @return The updated Inventory object, or null if the update failed.
     */
    public static Inventory updateInventory(Inventory inventory) {
        return getInventoryRepoInstance().update(inventory);
    }


    /**
     * Marks an inventory item's restock status as "pending".
     *
     * @param inventory The Inventory object to update.
     * @return The updated Inventory object with the restock status set to "pending".
     */
    public static Inventory updateInventoryStockRequest(Inventory inventory) {
        inventory.setRestockRequested("pending");
        return getInventoryRepoInstance().update(inventory);
    }


    /**
     * Creates a new inventory item with the specified details.
     *
     * @param medicationName The name of the medication.
     * @param quantity       The quantity of the medication to add to inventory.
     * @param lowStockAlert  The low stock alert level for the medication.
     * @return The newly created Inventory object, or null if creation failed.
     */
    public static Inventory createInventoryItem(String medicationName, int quantity, int lowStockAlert) {
        Inventory newItem = new Inventory(getInventoryRepoInstance().generateId(), medicationName, quantity, lowStockAlert, "Pending");
        return getInventoryRepoInstance().create(newItem);
    }
}
