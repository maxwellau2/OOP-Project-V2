package Administrator.View;

import Administrator.Controller.AdminActions;
import Appointment.Model.Appointment;
import Inventory.Model.Inventory;
import Staff.Model.Staff;
import static Util.SafeScanner.getValidatedIntInput;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    private Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        int choice;

        do {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("0. Exit");

            choice = getValidatedIntInput(scanner, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> viewAndManageHospitalStaff();
                case 2 -> viewAppointmentsDetails();
                case 3 -> viewAndManageMedicationInventory();
                case 4 -> approveReplenishmentRequests();
                case 0 -> System.out.println("Exiting the Administrator Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // View and manage hospital staff (Doctors, Pharmacists, etc.)
    public void viewAndManageHospitalStaff() {
        System.out.println("=== View and Manage Hospital Staff ===");
        List<Staff> staffList = AdminActions.getAllStaff(); 
        if (staffList.isEmpty()) {
            System.out.println("No hospital staff found.");
        } else {
            for (Staff staff : staffList) {
                System.out.println(staff);
            }
        }
        // Add or delete staff
        System.out.println("\n1. Add Staff");
        System.out.println("2. Delete Staff");
        int choice = getValidatedIntInput(scanner, "Choose an option: ", 1, 2);
        switch (choice) {
            case 1 -> addStaff();
            case 2 -> deleteStaff();
        }
    }

    // Add new staff member
    public void addStaff() {
        System.out.println("=== Add New Staff ===");
        System.out.print("Enter Staff ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role: ");
        String role = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Staff newStaff = new Staff(id, name, role, gender, age);
        Staff addedStaff = AdminActions.addStaff(newStaff); 
        System.out.println("Added staff: " + addedStaff);
    }

    // Delete a staff member by ID
    public void deleteStaff() {
        System.out.println("=== Delete Staff ===");
        System.out.print("Enter Staff ID to delete: ");
        String staffId = scanner.nextLine();
        Staff staffToDelete = new Staff(staffId, "", "", "", 0);
        boolean success = AdminActions.deleteStaff(staffToDelete); 
        if (success) {
            System.out.println("Staff member with ID " + staffId + " has been deleted.");
        } else {
            System.out.println("Staff member with ID " + staffId + " not found.");
        }
    }

    // View appointments details
    public void viewAppointmentsDetails() {
        System.out.println("=== View Appointments Details ===");
        List<Appointment> appointments = AdminActions.getAllAppointments(); 
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(appointment); 
            }
        }
    }


    // View and manage medication inventory 
    public void viewAndManageMedicationInventory() {
        System.out.println("=== View and Manage Medication Inventory ===");
        List<Inventory> inventoryList = AdminActions.getInventoryRepoInstance().getAll(); 
        if (inventoryList.isEmpty()) {
            System.out.println("No inventory items found.");
        } else {
            for (Inventory item : inventoryList) {
                System.out.println(item);
            }
        }
        // add functionality to update or remove items from inventory
        System.out.println("\n1. Update Stock");
        System.out.println("2. Add New Item");
        int choice = getValidatedIntInput(scanner, "Choose an option: ", 1, 2);
        switch (choice) {
            case 1 -> updateStock();
            case 2 -> addInventoryItem();
        }
    }

    // Update stock of an inventory item
    public void updateStock() {
        System.out.println("=== Update Stock ===");
        System.out.print("Enter Medication ID to update: ");
        String medicationId = scanner.nextLine();
        System.out.print("Enter New Quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Inventory itemToUpdate = new Inventory(medicationId, "", newQuantity, 0, false);
        boolean success = AdminActions.updateStock(itemToUpdate); 
        if (success) {
            System.out.println("Stock updated successfully.");
        } else {
            System.out.println("Stock update failed.");
        }
    }

    // Add new inventory item
    public void addInventoryItem() {
        System.out.println("=== Add New Inventory Item ===");
        System.out.print("Enter Medication ID: ");
        String medicationId = scanner.nextLine();
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Low Stock Alert Level: ");
        int lowStockAlert = scanner.nextInt();
        System.out.print("Is medication available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        Inventory newItem = new Inventory(medicationId, medicationName, quantity, lowStockAlert, isAvailable);
        boolean success = AdminActions.updateStock(newItem); 
        if (success) {
            System.out.println("New inventory item added successfully.");
        } else {
            System.out.println("Failed to add new item.");
        }
    }

    // Approve replenishment requests
    public void approveReplenishmentRequests() {
        System.out.println("=== Approve Replenishment Requests ===");
        List<Inventory> lowStockItems = AdminActions.getInventoryRepoInstance().getAll(); 
        if (lowStockItems.isEmpty()) {
            System.out.println("No replenishment requests pending approval.");
        } else {
            for (Inventory item : lowStockItems) {
                if (item.getQuantity() < item.getLowStockAlert()) {
                    System.out.println("Item: " + item.getMedicationName() + " is low on stock.");
                    System.out.print("Approve restock request? (yes/no): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        AdminActions.updateStock(item);  // Static method call corrected
                        System.out.println("Restock request approved for: " + item.getMedicationName());
                    }
                }
            }
        }
    }
}
