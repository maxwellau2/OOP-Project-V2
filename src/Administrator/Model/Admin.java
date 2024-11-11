package Administrator.Model;
import User.Model.User;
import java.lang.String;

public class Admin extends User {
	int id;
	String name;
	String gender;
	int age;
	public Admin(String hospitalID, String passwordHash, String role) {
		super(hospitalID,passwordHash,role);
		this.id=0;
		this.name="";
		this.gender="";
		this.age=0;
	}
}
