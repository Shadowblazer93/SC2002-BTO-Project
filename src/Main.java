
import boundary.Menu;
import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
import database.ReadCSV;
import database.SaveCSV;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import java.util.Map;



public class Main {
    public static void main(String[] args) {
        // Read save files
        ReadCSV.loadManager();
        ManagerController managerController = new ManagerController();
        Map<String, Manager> all = managerController.getAllManagers();
        for (Map.Entry<String, Manager> entry : all.entrySet()) {
            String nric = entry.getKey();
            Manager manager = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, manager.getName(), manager.getUserRole());
        }
        

        System.out.println();
        ReadCSV.loadApplicant();
        ApplicantController applicantController = new ApplicantController();
        Map<String, Applicant> allApplicants = applicantController.getAllApplicants();
        for (Map.Entry<String, Applicant> entry : allApplicants.entrySet()) {
            String nric = entry.getKey();
            Applicant applicant = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, applicant.getName(), applicant.getUserRole());
        }

        ReadCSV.loadOfficer();
        OfficerController officerController = new OfficerController();
        Map<String, Officer> allOfficers = officerController.getAllOfficers();
        for (Map.Entry<String, Officer> entry : allOfficers.entrySet()) {
            String nric = entry.getKey();
            Officer officer = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, officer.getName(), officer.getUserRole());
        }

        ReadCSV.loadEnquiry();
        ReadCSV.loadBTOApplication();
        ReadCSV.loadRegistration();
        ReadCSV.loadProject();
        
        // Start menu
        Menu menu = new Menu();
        menu.displayMenu();

        // Save CSV files
        SaveCSV.saveManagers();
        SaveCSV.saveApplicants();
        SaveCSV.saveOfficers();

        SaveCSV.saveProject();
        SaveCSV.saveEnquiries();
        SaveCSV.saveBTOApplications();
        SaveCSV.saveRegistrations();
    }
}