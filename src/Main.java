import boundary.Menu;
import controller.ApplicationController;
import controller.BTOProjectController;
import controller.EnquiryController;
import controller.RegistrationController;
import controller.user.ApplicantController;
import controller.user.ManagerController;
import controller.user.OfficerController;
import database.ReadCSV;
import database.SaveCSV;
import entity.user.Officer;
import interfaces.IApplicantService;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IManagerService;
import interfaces.IOfficerService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;
import java.util.Map;

/**
 * Main class is initalizes controllers, and saveas and writes data to the CSV files for the BTO Management System.
 */
public class Main {

    /**
     * Main method initializes services, loads data, and starts the application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {

        // Initialize services
        IApplicantService applicantService = new ApplicantController();
        IOfficerService officerService = new OfficerController();
        IManagerService managerService = new ManagerController();
        IApplicationService applicationService = new ApplicationController();
        IEnquiryService enquiryService = new EnquiryController();
        IProjectService projectService = new BTOProjectController();
        IRegistrationService registrationService = new RegistrationController();

        // Initialize CSV readers and writers
        ReadCSV readCSV = new ReadCSV(applicantService, officerService, managerService, 
                                        applicationService, enquiryService, projectService, registrationService);
        SaveCSV saveCSV = new SaveCSV(applicantService, officerService, managerService,
                                        applicationService, enquiryService, projectService, registrationService);

        // Load data from CSV files
        readCSV.loadManager();
        readCSV.loadApplicant();
        readCSV.loadOfficer();
        readCSV.loadEnquiry();
        readCSV.loadBTOApplication();
        readCSV.loadRegistration();  
        Map<String, Officer> allOfficers = officerService.getAllOfficers();
        for (Officer officer : allOfficers.values()) {
            System.out.printf("Officer %s assigned %s\n", officer.getName(), officer.getAssignedProject());
        }
        readCSV.loadProject(); 
        
        // Start main menu
        Menu menu = new Menu(applicantService, officerService, managerService, 
                            applicationService, enquiryService, projectService, registrationService);
        menu.displayMenu();

        // Save data to CSV files
        saveCSV.saveManagers();
        saveCSV.saveApplicants();
        saveCSV.saveOfficers();
        saveCSV.saveProject();
        saveCSV.saveEnquiries();
        saveCSV.saveBTOApplications();
        saveCSV.saveRegistrations();
    }
}