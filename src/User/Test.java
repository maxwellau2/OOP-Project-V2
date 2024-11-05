package User;
import User.Model.User;
import User.Repository.UserRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // Test file path for storing and loading
        String testFilePath = "src/Data/User_List.csv";

        // Initialize UserRepository and add sample users
        UserRepository userRepository = new UserRepository(testFilePath);
        userRepository.load();
        userRepository.display();
//        userRepository.display();
//        User user1 = new User("H001", "hashedPass1", "Patient");
//        user1.setLastLogin(LocalDateTime.now());
//        User user2 = new User("H002", "hashedPass2", "Doctor");
//        user2.setBlacklist(true);
//        user2.setLastLogin(LocalDateTime.now().minusDays(1));
//
//        userRepository.create(user1);
//        userRepository.create(user2);

        // Test storing to CSV
//        System.out.println("Testing store method...");
//        boolean storeSuccess = userRepository.store();
//        System.out.println("Store successful: " + storeSuccess);

//        userRepository.delete("H001");
//
//        userRepository.display();
//
//        userRepository.store();


        // Clear repository to test load functionality
//        userRepository.getAll().clear();
//
//        // Test loading from CSV
//        System.out.println("Testing load method...");
//        boolean loadSuccess = userRepository.load(testFilePath, User::fromCSV);
//        System.out.println("Load successful: " + loadSuccess);
////
////        // Retrieve loaded users and display them
//        List<User> loadedUsers = userRepository.getAll();
//        System.out.println("Loaded Users:");
//        loadedUsers.forEach(System.out::println);
//
//        // Clean up the test file
//        deleteTestFile(testFilePath);
    }

    // Utility method to delete the test CSV file after tests
    private static void deleteTestFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Test file deleted: " + filePath);
            } else {
                System.err.println("Failed to delete test file: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
