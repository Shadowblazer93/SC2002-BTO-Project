package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import entity.user.User;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;

public class LoginController {

    User currentUser;
    //declaration of where files are stored
    private final String applicantFile = "data/ApplicantList.csv";
    private final String managerFile = "data/ManagerList.csv";
    private final String officerFile = "data/OfficerList.csv";

    public LoginController() {

    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter NRIC: ");
        String nric = sc.nextLine().trim().toUpperCase();
        System.out.println("Enter password: ");
        String password = sc.nextLine();

        try {
            if(validateLogin(applicantFile, nric, password)){
                System.out.println("Login successful as Applicant.");            }
            else if (validateLogin(managerFile, nric, password)){
                System.out.println("Login successful as Manager");
            }
            else if (validateLogin(officerFile, nric, password)) {
                System.out.println("Login successful as Officer.");
                // Redirect to Officer menu
            }
            else{
                System.out.println("Invalid credentials, please try again!");
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
    }


    private boolean validateLogin(String filename, String nric, String password) throws IOException{
        
        //open file for reading 
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {           //read file line by line
                String[] parts = line.split(","); //split into NRIC, password....

                if (parts.length >= 2) {  //ensure it has nric and password if not is in incorrect
                    String fileNric = parts[0].trim().toUpperCase();   //get nric
                    String filePassword = parts[1].trim();  //get password

                    if (fileNric.equals(nric) && filePassword.equals(password)) { //if login credentials are correct
                        return true;
                    }
                }
            }
        }
        return false;   
    }
}
