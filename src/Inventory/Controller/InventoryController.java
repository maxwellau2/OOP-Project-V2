package Inventory.Controller;

import Inventory.Model.Inventory;

import java.util.List;

import static Administrator.Controller.AdminActions.getInventoryRepoInstance;

public class InventoryController {
    public static List<String> getUniqueInventoryItems() {
        return getInventoryRepoInstance()
                .getAll() // Get all inventory items
                .stream()
                .map(Inventory::getMedicationName) // Extract medication names
                .distinct() // Ensure uniqueness
                .toList(); // Collect as a list
    }

    public static List<Inventory> getAllInventory() {
        return getInventoryRepoInstance().getAll();
    }

    public static List<Inventory> getLowStockInventoryPending() {
        return getInventoryRepoInstance().getByFilter(inventory -> (inventory.getQuantity() < inventory.getLowStockAlert()) && (!inventory.isRestockRequested()));
    }

    public static Inventory updateInventoryStockRequest(Inventory inventory) {
        inventory.setRestockRequested(true);
        return getInventoryRepoInstance().update(inventory);
    }
}
