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

}
