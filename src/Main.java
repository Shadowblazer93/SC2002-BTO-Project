
import boundary.Menu;
import boundary.PrintRegistrations;
import controller.ApplicationController;
import controller.BTOProjectController;
import controller.EnquiryController;
import controller.RegistrationController;
import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
import database.ReadCSV;
import database.SaveCSV;
import entity.application.BTOApplication;
import entity.enquiry.Enquiry;
import entity.project.BTOProject;
import entity.registration.Registration;
import entity.user.Applicant;
import entity.user.Manager;
import entity.user.Officer;
import java.util.List;
import java.util.Map;



public class Main {
    public static void main(String[] args) {
        // Read save files
        ReadCSV.loadManager();
        Map<String, Manager> all = ManagerController.getAllManagers();
        System.out.println("\nManagers:");
        for (Map.Entry<String, Manager> entry : all.entrySet()) {
            String nric = entry.getKey();
            Manager manager = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, manager.getName(), manager.getUserRole());
        }
        

        System.out.println();
        ReadCSV.loadApplicant();
        System.out.println("\nApplicants:");
        Map<String, Applicant> allApplicants = ApplicantController.getAllApplicants();
        for (Map.Entry<String, Applicant> entry : allApplicants.entrySet()) {
            String nric = entry.getKey();
            Applicant applicant = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, applicant.getName(), applicant.getUserRole());
        }

        ReadCSV.loadOfficer();
        System.out.println("\nOfficers:");
        Map<String, Officer> allOfficers = OfficerController.getAllOfficers();
        for (Map.Entry<String, Officer> entry : allOfficers.entrySet()) {
            String nric = entry.getKey();
            Officer officer = entry.getValue();
            System.out.printf("NRIC: %s Name: %s Role: %s\n", nric, officer.getName(), officer.getUserRole());
        }

        ReadCSV.loadEnquiry();
        System.out.println("\nEnquiries:");
        Map<Integer, Enquiry> allEnquiries = EnquiryController.getAllEnquiries();
        for (Map.Entry<Integer, Enquiry> entry : allEnquiries.entrySet()) {
            int id = entry.getKey();
            Enquiry enquiry = entry.getValue();
            System.out.printf("Enquiry ID: %d Applicant NRIC: %s Project Name: %s Status: %s\n", id, enquiry.getApplicantNRIC(), enquiry.getProjectName(), enquiry.getStatus());
        }

        ReadCSV.loadBTOApplication();
        System.out.println("\nApplications:");
        Map<String, BTOApplication> allApplications = ApplicationController.getAllApplications();
        for (Map.Entry<String, BTOApplication> entry : allApplications.entrySet()) {
            String nric = entry.getKey();
            BTOApplication application = entry.getValue();
            System.out.printf("NRIC: %s Project Name: %s Flat Type: %s Status: %s\n", nric, application.getProjectName(), application.getFlatType(), application.getStatus());
        }

        ReadCSV.loadRegistration();
        System.out.println("\nRegistrations:");
        Map<String, List<Registration>> allRegistrations = RegistrationController.getAllRegistrations();
        PrintRegistrations printRegistrations = new PrintRegistrations();
        printRegistrations.printMapList(allRegistrations);

        ReadCSV.loadProject();
        System.out.println("\nProjects:");
        Map<String, BTOProject> allProjects = BTOProjectController.getAllProjects();
        for (Map.Entry<String, BTOProject> entry : allProjects.entrySet()) {
            String projectName = entry.getKey();
            BTOProject project = entry.getValue();
            System.out.printf("Project Name: %s\n", projectName);
            System.out.print("\tRegistrations" + project.getRegistrations());
            System.out.print("\n\tEnquiries" + project.getEnquiries());
            System.out.print("\n\tApplications" + project.getApplications());
            System.out.print("\n\tAssigned Officers" + project.getAssignedOfficers());
            System.out.println();
        }
        
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