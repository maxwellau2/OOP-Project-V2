package Inventory;

import Inventory.Model.Inventory;
import Inventory.Repository.InventoryRepository;

public class Test {
    public static void main(String[] args) {
        InventoryRepository repo = new InventoryRepository("testInventory.csv");
        repo.load();
        repo.display();
//        repo.store();
//        repo.create(new Inventory("11231233", "asdads", 123, 0));
////        repo.create(new Inventory("13", "anarex", 13, 0));
////        repo.create(new Inventory("132", "anarex2", 134, 1));
//        repo.store();

    }

}
