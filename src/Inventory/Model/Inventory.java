package Inventory.Model;

import Interfaces.IEntity;

public class Inventory implements IEntity {
    private String id;
    private String medicationName;
    private int quantity;
    private int lowStockAlert;
    private boolean restockRequested;

    public Inventory(String id, String medicationName, int quantity, int lowStockAlert, boolean restockRequested) {
        this.id = id;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.lowStockAlert = lowStockAlert;
        this.restockRequested = restockRequested;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public boolean isRestockRequested() {
        return restockRequested;
    }

    public void setRestockRequested(boolean restockRequested) {
        this.restockRequested = restockRequested;
    }

    @Override
    public String toString() {
        return "Inventory {" +
                "id='" + id + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", quantity=" + quantity +
                ", lowStockAlert=" + lowStockAlert +
                ", restockRequested=" + restockRequested +
                '}';
    }

    @Override
    public String toCSV() {
        return String.join(",", id, medicationName, String.valueOf(quantity), String.valueOf(lowStockAlert), String.valueOf(restockRequested));
    }

    public void prettyPrint() {
        System.out.println("=== Inventory Item ===");
        System.out.println("ID                : " + id);
        System.out.println("Medication Name   : " + medicationName);
        System.out.println("Quantity          : " + quantity);
        System.out.println("Low Stock Alert   : " + lowStockAlert);
        System.out.println("Restock Requested : " + (restockRequested ? "Yes" : "No"));
        System.out.println("======================");
    }
}
