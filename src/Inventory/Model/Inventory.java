package Inventory.Model;

import Interfaces.IEntity;

public class Inventory implements IEntity {
    private String id;
    private String medicationName;
    private int quantity;
    private int lowStockAlert;

    public Inventory(String id, String medicationName, int quantity, int lowStockAlert) {
        this.id = id;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.lowStockAlert = lowStockAlert;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toCSV() {
        return id + "," + medicationName + "," + quantity + "," + lowStockAlert;
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

    @Override
    public String toString(){
        return "Inventory {id=" + id + ", medicationName=" + medicationName + ", quantity=" + quantity
                + ", lowStockAlert=" + lowStockAlert + "}";
    }
}
