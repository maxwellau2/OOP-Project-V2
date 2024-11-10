package Administrator;
import User.Model.User;
public class Admin extends User {
	int id;
	String name;
	String gender;
	int age;
	public Admin() {
		this.id=0;
		this.name="";
		this.gender="";
		this.age=0;
	}
}
