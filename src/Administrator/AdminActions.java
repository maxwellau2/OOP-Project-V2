package Administrator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Doctor.Model.Doctor;
import User.View.UserActions;
public class AdminActions extends UserActions{
	public void manageStaff(String csvPath) {
		int choice_int;
        List<String[]> rows = new ArrayList<>();
        System.out.println("1. Add staff member");
        System.out.println("2. Update staff member");
        System.out.println("3. Remove staff member");
		Scanner choice = new Scanner(System.in);
		choice_int = choice.nextInt();
		if (choice_int == 1)
		{
            System.out.println("1.Add Doctor");
            System.out.println("2.Add Pharmacist");
            Scanner choice1_2 = new Scanner(System.in);
            if(choice1_2.nextInt() == 1)
            {
			    int i;
                String[] values;
	            System.out.println("Enter the doctor's id, name, age, gender and specialization.");
		    	Scanner id = new Scanner(System.in);
		    	String id_Str = id.nextLine();
		    	Scanner name = new Scanner(System.in);
	    		String name_Str = name.nextLine();
	    		Scanner age = new Scanner(System.in);
		    	String age_Str = age.nextLine();
	    		Scanner gender = new Scanner(System.in);
	    		String gender_Str = gender.nextLine();
	    		Scanner spec = new Scanner(System.in);
	    		String spec_Str = spec.nextLine();
	    		Doctor d = new Doctor(id.nextLine(),name.nextLine(),age.nextInt(),gender.nextLine(),spec.nextLine());
	            try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
	                String line;
	                while ((line = br.readLine()) != null) {
	                    values = line.split(",");
	                    rows.add(values);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	                return;
	            }//
	    		String[] newRowData = {id_Str, name_Str, age_Str, gender_Str, spec_Str};
                int rowIndex = -1;
                for (i = 0; i < rows.size(); i++) {
                    if (rows.get(i)[0].equals(id_Str)) {
                    rowIndex = i;
                    break;
                    }
                }

	            if (rowIndex >= 0 && rowIndex < rows.size()) {
	                rows.set(rowIndex, newRowData);
	            } else {
	                System.out.println("Row index out of bounds.");
	                return;
	            }
	            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
	                for (String[] row : rows) {
	                    bw.write(String.join(",", row));
	                    bw.newLine();
	                }
	                System.out.println("Doctor added.");
	            } catch (IOException e) {
	                e.printStackTrace();
	            }   
                }
            if(choice1_2.nextInt() == 2)
            {
                //Pharmacist
            }
		}
        if (choice_int == 2) {
            int choice2 = 0;
            boolean found2 = false;
            System.out.println("Enter staff id:");
            Scanner id2 = new Scanner(System.in);
            String targetId = id2.nextLine();

            try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    rows.add(values);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        
            for (int i = 0; i < rows.size(); i++) {
                String[] values = rows.get(i);
                
                if (values.length > 0 && values[0].equals(targetId)) {
                    found2 = true;
                    System.out.println("Staff member found: " + String.join(", ", values));
                    
                    System.out.println("1. Update Name");
                    System.out.println("2. Update Age");
                    System.out.println("3. Update Gender");
                    System.out.println("4. Update Specialization");
                    Scanner updateChoice2 = new Scanner(System.in);
                    int updateChoice2_int = updateChoice2.nextInt();
                    updateChoice2.nextLine();
                    
                    switch (updateChoice2_int) {
                        case 1:
                            System.out.println("Enter new name:");
                            values[1] = updateChoice2.nextLine();
                            break;
                        case 2:
                            System.out.println("Enter new age:");
                            values[2] = updateChoice2.nextLine();
                            break;
                        case 3:
                            System.out.println("Enter new gender:");
                            values[3] = updateChoice2.nextLine();
                            break;
                        case 4:
                            System.out.println("Enter new specialization:");
                            values[4] = updateChoice2.nextLine();
                            break;
                        default:
                            System.out.println("Invalid choice.");
                            return;
                    }
                    rows.set(i, values);
                    break;
                }
            }
        
            if (!found2) {
                System.out.println("Staff member with ID " + targetId + " not found.");
            } else {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvPath))) {
                    for (String[] row : rows) {
                        bw.write(String.join(",", row));
                        bw.newLine();
                    }
                    System.out.println("Staff member details updated successfully.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		if (choice_int == 3)
		{
            
		}
	}
}
