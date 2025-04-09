package entity.user;
import enums.UserRole;
import java.util.Scanner;

// Change to interface ?
public class User {
    private String nric;
    private String name;
    private String password;
    private int age;
    private String maritalStatus;
    private UserRole userRole;

    public User(String nric, String name, String password, int age, String maritalStatus) {
        this.nric = nric;
        this.name = name;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public boolean login(String NRIC,String loginPass) {
        if (NRIC.equals(NRIC)&&loginPass.equals(password)) {return true;}
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

    public String getNRIC() {
        return nric;
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