package Administrator.View;

import Administrator.Controller.AdminActions;
import Administrator.Model.Admin;
import Appointment.Model.Appointment;
import Doctor.Model.Doctor;
import Inventory.Controller.InventoryController;
import Inventory.Model.Inventory;
import Pharmacist.Model.Pharmacist;
import Pharmacist.Repository.PharmacistRepository;
import Staff.Model.Staff;
import Staff.Repository.StaffRepository;
import User.Controller.UserController;

import java.util.List;
import java.util.Scanner;

import static Util.RepositoryGetter.*;
import static Util.SafeScanner.getValidatedIntInput;
import static Util.SafeScanner.getValidatedStringInput;

public class AdminView {
    private Scanner scanner;
    private Admin admin;
    public AdminView(Admin admin) {
        this.admin = admin;
        scanner = new Scanner(System.in);
    }
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
        // remove self
        staffList.removeIf(staff -> staff.getId().equalsIgnoreCase(this.admin.getId()));
        List<String> staffIds = staffList.stream().map(staff -> staff.getId()).toList();
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
        System.out.println("3. Update Staff");
        System.out.println("0. Exit");
        int choice = getValidatedIntInput(scanner, "Choose an option: ", 0, 3);
        switch (choice) {
            case 1 -> addStaff();
            case 2 -> deleteStaff();
            case 3 -> updateStaff();
            case 0 -> System.out.println("Quitting...\n");
        }
    }

    private void updateStaff() {
        System.out.println("=== Update Staff ===");

        // Retrieve all staff members, excluding the current admin
        List<Staff> allStaff = AdminActions.getAllStaff();
        allStaff.removeIf(staff -> staff.getId().equalsIgnoreCase(this.admin.getId()));

        if (allStaff.isEmpty()) {
            System.out.println("No staff available for updating.");
            return;
        }

        // Display the list of staff members with their indices
        System.out.println("Select a staff member to update:");
        for (int i = 0; i < allStaff.size(); i++) {
            System.out.println((i + 1) + ". " + allStaff.get(i).toString());
        }

        // Ask user to select a staff member by index
        int selectedIndex = getValidatedIntInput(scanner,
                "Enter the index of the staff member to update (or 0 to exit): ", 0, allStaff.size()) - 1;

        if (selectedIndex == -1) {
            System.out.println("Exiting staff update process.");
            return;
        }

        Staff selectedStaff = allStaff.get(selectedIndex);

        System.out.println("Selected Staff: ");
        selectedStaff.prettyPrint();

        // Update staff attributes
        System.out.println("\n=== Update Attributes ===");
        System.out.println("Enter 0 to keep the current value.");

        String newName = getValidatedStringInput(scanner,
                "Enter new name for the staff member: ", 30);
        if (newName.equals("0")) {
            newName = selectedStaff.getName();
        }

        String newGender = getValidatedStringInput(scanner,
                "Enter new gender (male/female): ", List.of("male", "female", "0"));
        if (newGender.equals("0")) {
            newGender = selectedStaff.getGender();
        }

        int newAge = getValidatedIntInput(scanner,
                "Enter new age (0 to keep unchanged): ", 0, 100);
        if (newAge == 0) {
            newAge = selectedStaff.getAge();
        }

        // Apply updates to the staff member
        selectedStaff.setName(newName);
        selectedStaff.setGender(newGender);
        selectedStaff.setAge(newAge);

        // Update the staff in the repository
        Staff updatedStaff = AdminActions.updateStaff(selectedStaff);

        if (updatedStaff != null) {
            System.out.println("Staff updated successfully:");
            updatedStaff.prettyPrint();
        } else {
            System.out.println("Failed to update staff. Please try again.");
        }
    }


    // Add new staff member
    public void addStaff() {
        System.out.println("=== Add New Staff ===");

        // Step 1: Ask for the role first
        String role = getValidatedStringInput(scanner, "Enter Role (doctor, pharmacist, admin): ", List.of("doctor", "pharmacist", "admin"));

        Staff newStaff = null;

        switch (role.toLowerCase()) {
            case "doctor" -> {
                String name = getValidatedStringInput(scanner, "Enter Name: ", 30);
                String gender = getValidatedStringInput(scanner, "Enter Gender (male, female): ", List.of("male", "female"));
                int age = getValidatedIntInput(scanner, "Enter Age: ", 18, 100);
                String specialization = getValidatedStringInput(scanner, "Enter Specialization: ", 50);

                // Create Doctor and convert to Staff
                Doctor newDoctor = getDoctorRepository().createDoctor(name,age,gender,specialization);
                newStaff = getStaffRepoInstance().createStaffFromDoctor(newDoctor);
            }
            case "pharmacist" -> {
                String name = getValidatedStringInput(scanner, "Enter Name: ", 30);
                String gender = getValidatedStringInput(scanner, "Enter Gender (male, female): ", List.of("male", "female"));
                int age = getValidatedIntInput(scanner, "Enter Age: ", 18, 100);

                // Create Pharmacist and convert to Staff
                Pharmacist newPharmacist = PharmacistRepository.createPharmacist(name, age, gender);
                newStaff = StaffRepository.getInstance("").createStaffFromPharmacist(newPharmacist);

            }
            case "admin" -> {
                String name = getValidatedStringInput(scanner, "Enter Name: ", 30);
                String gender = getValidatedStringInput(scanner, "Enter Gender (male, female): ", List.of("male", "female"));
                int age = getValidatedIntInput(scanner, "Enter Age: ", 18, 100);

                // Create Admin and convert to Staff
                Admin newAdmin = getAdminRepository().createAdmin(name, gender, age);
                newStaff = getStaffRepoInstance().createStaffFromAdmin(newAdmin);
                // create new user profile
            }
            default -> System.out.println("Invalid role. Please try again.");
        }

        // Step 2: Add the new Staff object to the Staff repository
        if (newStaff != null) {
            newStaff = getStaffRepoInstance().create(newStaff);
            UserController.createNewUser(newStaff.getId(), newStaff.getRole());

            System.out.println("Successfully added staff:");
            newStaff.prettyPrint(); // Assuming `prettyPrint` is implemented in the `Staff` class
        } else {
            System.out.println("Failed to add new staff. Please try again.");
        }
    }


    // Delete a staff member by ID
    public void deleteStaff() {
        System.out.println("=== Delete Staff ===");

        // Retrieve all staff members, excluding the current admin
        List<Staff> allStaff = AdminActions.getAllStaff();
        allStaff.removeIf(staff -> staff.getId().equalsIgnoreCase(this.admin.getId()));

        if (allStaff.isEmpty()) {
            System.out.println("No staff available for deletion (cannot delete yourself).");
            return;
        }

        // Display all staff members with their indices
        System.out.println("Select a staff member to delete:");
        for (int i = 0; i < allStaff.size(); i++) {
            System.out.println((i + 1) + ". " + allStaff.get(i).toString());
        }

        // Ask the user to select a staff member by index
        int selectedIndex = getValidatedIntInput(scanner,
                "Enter the index of the staff member to delete (or 0 to exit): ", 0, allStaff.size()) - 1;

        if (selectedIndex == -1) {
            System.out.println("Exiting staff deletion process.");
            return;
        }

        Staff selectedStaff = allStaff.get(selectedIndex);

        System.out.println("Selected Staff for Deletion: ");
        selectedStaff.prettyPrint();

        // Confirm deletion
        String confirmation = getValidatedStringInput(scanner,
                "Are you sure you want to delete this staff member? (yes/no): ", List.of("yes", "no"));

        if (confirmation.equalsIgnoreCase("yes")) {
            boolean success = AdminActions.deleteStaff(selectedStaff);

            if (success) {
                System.out.println("Staff member with ID " + selectedStaff.getId() + " has been deleted.");
            } else {
                System.out.println("Failed to delete staff member with ID " + selectedStaff.getId() + ". Please try again.");
            }
        } else {
            System.out.println("Deletion canceled.");
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
                appointment.prettyPrint();
            }
        }
    }

    // View and manage medication inventory 
    public void viewAndManageMedicationInventory() {
        System.out.println("=== View and Manage Medication Inventory ===");
        List<Inventory> inventoryList = getInventoryRepoInstance().getAll();
        if (inventoryList.isEmpty()) {
            System.out.println("No inventory items found.");
        } else {
            for (Inventory item : inventoryList) {
                item.prettyPrint();
            }
        }
        // add functionality to update or remove items from inventory
        System.out.println("\n1. Update Stock");
        System.out.println("2. Add New Item");
        System.out.println("0. Quit");

        int choice = getValidatedIntInput(scanner, "Choose an option: ", 0, 2);
        switch (choice) {
            case 1 -> updateStock();
            case 2 -> addInventoryItem();
            case 0 -> System.out.println("Quitting");
        }
    }

    // Update stock of an inventory item
    public void updateStock() {
        System.out.println("=== Update Stock ===");

        // Step 1: Get and display low-stock items
        List<Inventory> lowStockItems = InventoryController.getAllInventory();

        if (lowStockItems.isEmpty()) {
            System.out.println("No low-stock items to update.");
            return;
        }

        System.out.println("Low-Stock Items:");
        for (int i = 0; i < lowStockItems.size(); i++) {
            System.out.print((i + 1) + ". ");
            lowStockItems.get(i).prettyPrint(); // Assuming `prettyPrint()` is implemented in the `Inventory` class
        }

        // Step 2: Ask the user to select an item to update
        int itemIndex = getValidatedIntInput(scanner, "Select the item number to update: ", 1, lowStockItems.size()) - 1;
        Inventory selectedItem = lowStockItems.get(itemIndex);

        // Step 3: Get the new quantity for the selected item
        int newQuantity = getValidatedIntInput(scanner, "Enter new quantity for " + selectedItem.getMedicationName() + ": ", 0, 1000);

        // Step 4: Update the inventory
        selectedItem.setQuantity(newQuantity);
        selectedItem.setRestockRequested("approved"); // Clear restock flag since stock is updated
        Inventory updatedItem = InventoryController.updateInventory(selectedItem);

        // Step 5: Display the result
        if (updatedItem != null) {
            System.out.println("Stock updated successfully:");
            updatedItem.prettyPrint();
        } else {
            System.out.println("Stock update failed. Please try again.");
        }
    }


    // Add new inventory item
    public void addInventoryItem() {
        System.out.println("=== Add New Inventory Item ===");

        // Step 1: Get user input for medication details
        String medicationName = getValidatedStringInput(scanner, "Enter Medication Name: ", 50);
        int quantity = getValidatedIntInput(scanner, "Enter Quantity: ", 0, 99999);
        int lowStockAlert = getValidatedIntInput(scanner, "Enter Low Stock Alert Level: ", 0, 1000);

        // Step 2: Create new inventory item using the factory method
        Inventory newItem = InventoryController.createInventoryItem(medicationName, quantity, lowStockAlert);

        // Step 3: Provide feedback to the user
        if (newItem != null) {
            System.out.println("New inventory item added successfully:");
            newItem.prettyPrint(); // Assuming prettyPrint() is implemented in the Inventory class
        } else {
            System.out.println("Failed to add new inventory item. Please try again.");
        }
    }


    // Approve replenishment requests
    public void approveReplenishmentRequests() {
        System.out.println("=== Approve Replenishment Requests ===");

        // Retrieve all pending low stock items
        List<Inventory> lowStockItems = InventoryController.getLowStockInventoryPending();

        if (lowStockItems.isEmpty()) {
            System.out.println("No replenishment requests pending approval.");
            return;
        }

        // Display all low stock items in a single line with their indices
        System.out.println("Pending Replenishment Requests:");
        for (int i = 0; i < lowStockItems.size(); i++) {
            Inventory item = lowStockItems.get(i);
            System.out.println((i + 1) + ". " + item.getMedicationName() +
                    " (Current Quantity: " + item.getQuantity() +
                    ", Low Stock Alert Level: " + item.getLowStockAlert() + ")");
        }

        // Prompt user to select an item by index
        int selectedIndex = getValidatedIntInput(scanner,
                "Enter the index of the medication to approve restock (or 0 to quit): ", 0, lowStockItems.size()) - 1;

        if (selectedIndex == -1) { // User selected 0 to quit
            System.out.println("Exiting replenishment approval process.");
            return;
        }

        Inventory selectedItem = lowStockItems.get(selectedIndex);

        // Approve restock request and set stock level to 10 * low stock alert level + original
        selectedItem.setQuantity(selectedItem.getLowStockAlert() * 10 + selectedItem.getQuantity());
        selectedItem.setRestockRequested("approved"); // Mark as approved

        // Update inventory
        Inventory updatedItem = InventoryController.updateInventory(selectedItem);

        if (updatedItem != null) {
            System.out.println("Restock request approved for: " + selectedItem.getMedicationName());
            System.out.println("New Stock Level: " + updatedItem.getQuantity());
        } else {
            System.out.println("Failed to update stock for: " + selectedItem.getMedicationName());
        }
    }


}
