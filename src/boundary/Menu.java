package boundary;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("1. Login\n" +
                            "2. Change Password\n" +
                            "3. Exit"); 
        do {
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Login");
                    break;
                case 2:
                    System.out.println("Change Password");
                    break;
                case 3:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);

        
        sc.close();
    }
}
