package Inventory.Repository;

import Abstract.Repository;
import Inventory.Model.Inventory;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository extends Repository<Inventory> {
    private static InventoryRepository instance;

    public InventoryRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }

    public static InventoryRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new InventoryRepository(csvPath);
            instance.load();
        }
        return instance;
    }

    public Inventory getItemByName(String medicationName) {
        return entities.stream()
                .filter(item -> item.getMedicationName().equalsIgnoreCase(medicationName))
                .findFirst()
                .orElse(null);
    }

    public List<Inventory> getLowStockItems() {
        return entities.stream()
                .filter(item -> item.getQuantity() < item.getLowStockAlert())
                .toList();
    }

    public void submitRestockRequest(Inventory item) {
        System.out.println("Restock request submitted for item: " + item.getMedicationName());
        item.setRestockRequested("Requested");
        update(item);
    }

    @Override
    protected Inventory fromCSV(String csvLine) {
        String[] values = csvLine.split(",");
        int qty = Integer.parseInt(values[2]);
        int lowStockAlert = Integer.parseInt(values[3]);
        String restockRequested = values[4]; // Now a string
        return new Inventory(values[0], values[1], qty, lowStockAlert, restockRequested);
    }

    @Override
    protected String getHeader() {
        return "id,medicationName,quantity,lowStockAlert,restockRequested";
    }
}
