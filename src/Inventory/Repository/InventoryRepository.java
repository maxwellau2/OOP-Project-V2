package Inventory.Repository;
import Abstract.Repository;
import Inventory.Model.Inventory;

import java.util.ArrayList;

public class InventoryRepository extends Repository<Inventory> {

    public InventoryRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<Inventory>();
    }

    @Override
    protected Inventory fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        int qty = Integer.parseInt(values[2]);
        int lowStockAlert = Integer.parseInt(values[3]);
        return new Inventory(values[0], values[1], qty, lowStockAlert);
    }

    @Override
    protected String getHeader() {
        return "id,medicationName,quantity,lowStockAlert";
    }
}
