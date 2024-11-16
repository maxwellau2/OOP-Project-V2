package Inventory.Controller;

import Inventory.Model.Inventory;
import java.util.List;

import static Util.RepositoryGetter.getInventoryRepoInstance;

public class InventoryController {
    public static List<String> getUniqueInventoryItems() {
        return getInventoryRepoInstance()
                .getAll()
                .stream()
                .map(Inventory::getMedicationName)
                .distinct()
                .toList();
    }

    public static List<Inventory> getAllInventory() {
        return getInventoryRepoInstance().getAll();
    }

    public static List<Inventory> getLowStockInventoryPending() {
        return getInventoryRepoInstance().getByFilter(inventory ->
                (inventory.getQuantity() < inventory.getLowStockAlert()) &&
                        "Pending".equalsIgnoreCase(inventory.getRestockRequested()));
    }

    public static Inventory updateInventory(Inventory inventory) {
        return getInventoryRepoInstance().update(inventory);
    }

    public static Inventory updateInventoryStockRequest(Inventory inventory) {
        inventory.setRestockRequested("pending");
        return getInventoryRepoInstance().update(inventory);
    }

    public static Inventory createInventoryItem(String medicationName, int quantity, int lowStockAlert) {
        Inventory newItem = new Inventory(getInventoryRepoInstance().generateId(), medicationName, quantity, lowStockAlert, "Pending");
        return getInventoryRepoInstance().create(newItem);
    }
}
