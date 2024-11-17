package Inventory.Model;

import Interfaces.IEntity;
/**
 * Represents an inventory item in the medical inventory system.
 * Implements IEntity for compatibility with repository operations.
 */
public class Inventory implements IEntity {
    private String id;                  // Unique identifier for the inventory item
    private String medicationName;      // Name of the medication
    private int quantity;               // Current stock quantity of the medication
    private int lowStockAlert;          // Minimum threshold for stock level
    private String restockRequested;    // Restock status (e.g., "pending", "approved")

    /**
     * Constructs an Inventory object with specified attributes.
     *
     * @param id                Unique identifier for the inventory item.
     * @param medicationName    Name of the medication.
     * @param quantity          Current stock quantity.
     * @param lowStockAlert     Minimum threshold for stock level.
     * @param restockRequested  Restock status (e.g., "pending", "approved").
     */
    public Inventory(String id, String medicationName, int quantity, int lowStockAlert, String restockRequested) {
        this.id = id;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.lowStockAlert = lowStockAlert;
        this.restockRequested = restockRequested;
    }

    /**
     * Gets the unique identifier of the inventory item.
     *
     * @return The inventory item ID.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the inventory item.
     *
     * @param id The new ID for the inventory item.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the medication name of the inventory item.
     *
     * @return The medication name.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Sets the medication name of the inventory item.
     *
     * @param medicationName The new medication name.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Gets the current stock quantity of the inventory item.
     *
     * @return The stock quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the current stock quantity of the inventory item.
     *
     * @param quantity The new stock quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the low stock alert threshold of the inventory item.
     *
     * @return The low stock alert threshold.
     */
    public int getLowStockAlert() {
        return lowStockAlert;
    }

    /**
     * Sets the low stock alert threshold of the inventory item.
     *
     * @param lowStockAlert The new low stock alert threshold.
     */
    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    /**
     * Gets the restock status of the inventory item.
     *
     * @return The restock status (e.g., "pending", "approved").
     */
    public String getRestockRequested() {
        return restockRequested;
    }

    /**
     * Sets the restock status of the inventory item.
     *
     * @param restockRequested The new restock status.
     */
    public void setRestockRequested(String restockRequested) {
        this.restockRequested = restockRequested;
    }

    /**
     * Converts the inventory item to a string representation.
     *
     * @return A string containing the inventory item details.
     */
    @Override
    public String toString() {
        return "Inventory {" +
                "id='" + id + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", quantity=" + quantity +
                ", lowStockAlert=" + lowStockAlert +
                ", restockRequested='" + restockRequested + '\'' +
                '}';
    }

    /**
     * Converts the inventory item to a CSV representation.
     *
     * @return A CSV-formatted string of the inventory item.
     */
    @Override
    public String toCSV() {
        return String.join(",", id, medicationName, String.valueOf(quantity), String.valueOf(lowStockAlert), restockRequested);
    }

    /**
     * Prints the inventory item details in a formatted manner.
     */
    public void prettyPrint() {
        System.out.println("=== Inventory Item ===");
        System.out.println("ID                : " + id);
        System.out.println("Medication Name   : " + medicationName);
        System.out.println("Quantity          : " + quantity);
        System.out.println("Low Stock Alert   : " + lowStockAlert);
        System.out.println("Restock Requested : " + restockRequested);
        System.out.println("======================");
    }
}
