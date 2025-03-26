package user;
import java.util.Scanner;

public class User {
    private String UserID;
    private String password;

    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new password: ");
        String newPassword = sc.next();
        
        this.password = newPassword;
        System.out.println("Password successfully changed.");
    }
}