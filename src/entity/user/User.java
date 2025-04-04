package entity.user;
import java.util.Scanner;

import enums.UserRole;

// Change to interface ?
public class User {
    private String UserID;
    private String name;
    private String password;
    private int age;
    private String maritalStatus;
    private UserRole userRole;

    public User(){}

    public boolean login(String NRIC,String loginPass) {
        if (NRIC.equals(UserID)&&loginPass.equals(password)) {return true;}
        else {return false;}
    }

    public void logout() {}

    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new password: ");
        String newPassword = sc.next();
        
        this.password = newPassword;
        System.out.println("Password successfully changed.");
        // logout afterwards
    }

    public String getUserID() {
        return UserID;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getMaritalStatus(){
        return maritalStatus;
    }
}