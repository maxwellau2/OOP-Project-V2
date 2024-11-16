package User.Controller;

import User.Model.User;
import User.Repository.UserRepository;

import java.time.LocalDateTime;

import static Util.PasswordUtils.hashPassword;
import static Util.RepositoryGetter.getUserRepository;


/**
 * Controller for managing user-related operations.
 * Provides methods for creating, updating, deleting, and authenticating users.
 */
public class UserController {

    /**
     * Deletes a user by their hospital ID.
     *
     * @param id The hospital ID of the user to be deleted.
     * @return {@code true} if the user was successfully deleted, otherwise {@code false}.
     */
    public static boolean deleteUser(String id){
        return getUserRepository().deleteById(id);
    }


    /**
     * Creates a new user with the specified hospital ID and role.
     * The default password for the new user is "password".
     *
     * @param hospitalId The hospital ID for the new user.
     * @param role       The role of the new user.
     * @return The newly created {@link User} object.
     */
    public static User createNewUser(String hospitalId, String role){
        return getUserRepository().create(new User(hospitalId,hashPassword("password"),role));
    }


    /**
     * Authenticates a user based on their hospital ID and password.
     *
     * @param hospitalId The hospital ID of the user attempting to log in.
     * @param password   The password provided by the user.
     * @return The {@link User} object if authentication is successful, otherwise {@code null}.
     */
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


    /**
     * Updates the password of the specified user.
     *
     * @param user     The user whose password is to be updated.
     * @param password The new password.
     * @return The updated {@link User} object, or {@code null} if the user was not found.
     */

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


    /**
     * Updates the last login time of the specified user to the current time.
     *
     * @param user The user whose last login time is to be updated.
     * @return The updated {@link User} object.
     */
    public static User updateLastLoginToNow(User user){
        user.setLastLogin(LocalDateTime.now());
        UserRepository repo = getUserRepository();
        User res = repo.update(user);
        return res;
    }

}
