
import boundary.Menu;
import controller.EnquiryController;
import controller.user.ApplicantController;
import controller.user.ManagerController;
import database.ReadCSV;
import database.SaveCSV;
import entity.enquiry.Enquiry;
import entity.user.Applicant;
import entity.user.Manager;

import java.util.List;
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
        ReadCSV.loadProject();

        System.out.println();
        ReadCSV.loadApplicant();
        ApplicantController applicantController = new ApplicantController();
        Map<String, Applicant> allApplicants = applicantController.getAllApplicants();
        for (Map.Entry<String, Applicant> entry : allApplicants.entrySet()) {
            String nric = entry.getKey();
            Applicant applicant = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, applicant.getName(), applicant.getUserRole());
        }

        ReadCSV.loadEnquiry();
        EnquiryController enquiryController = new EnquiryController();
        List<Enquiry> allEnquiries = EnquiryController.getAllEnquiries();

        // Start menu
        Menu menu = new Menu();
        menu.displayMenu();

        // Save CSV files
        SaveCSV.saveProject();
        SaveCSV.saveManageras();
        SaveCSV.saveApplicantas();
        SaveCSV.saveEnquiries();
    }
}