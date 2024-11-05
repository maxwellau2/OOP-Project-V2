package User.View;

import User.Model.User;

public class UserActions {
    public User user;
    public UserActions(User user){
        this.user = user;
    }

    public User login(String username, String password){
        return new User();
    }

    public void ViewProfile(){
        System.out.println("User Profile");
        System.out.println(this.user);
    }


}
