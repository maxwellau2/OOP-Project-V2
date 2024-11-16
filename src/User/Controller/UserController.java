package User.Controller;

import User.Model.User;
import User.Repository.UserRepository;

import java.time.LocalDateTime;

import static Util.PasswordUtils.hashPassword;

public class UserController {

    public static UserRepository getUserRepository(){
        return UserRepository.getInstance("src/Data/User_List.csv");

    }

    public static User login(String hospitalId, String password){
        UserRepository repo = getUserRepository();
        User foundUser = repo.read(hospitalId);
        if (foundUser == null) {
            System.out.println("User not found.");
            return null;
        }
        // user exists
        // 1. check the blacklist
        if (foundUser.isBlacklist()) {
            System.out.println("User is blacklisted.");
            return null;
        }
        // 2. check the password
        String hashedPassword = hashPassword(password);
//        System.out.println(hashedPassword);
        if(!hashedPassword.equals(foundUser.getPasswordHash())) {
            System.out.println("Incorrect password.");
            return null;
        }
        // 3. all checks passed
        System.out.println("Login successful.");
        return foundUser;
    }

    public static User updatePassword(User user, String password){
        //UserRepository repo = getUserRepository();
        if (user == null) {
            System.out.println("User not found, have you logged in?");
            return null;
        }
        // user not null, just call update...
        user.setPasswordHash(hashPassword(password));
        user = updateLastLoginToNow(user);
        return user;
    }

    public static User updateLastLoginToNow(User user){
        user.setLastLogin(LocalDateTime.now());
        UserRepository repo = getUserRepository();
        User res = repo.update(user);
        return res;
    }

    public void viewProfile(User user){
        System.out.println("====User Profile===");
        System.out.println(user);
    }
}
