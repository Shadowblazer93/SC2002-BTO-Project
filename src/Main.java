
import boundary.Menu;
import controller.ApplicationController;
import controller.BTOProjectController;
import controller.EnquiryController;
import controller.RegistrationController;
import database.ReadCSV;
import database.SaveCSV;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;



public class Main {
    public static void main(String[] args) {
        IApplicationService applicationService = new ApplicationController();
        IEnquiryService enquiryService = new EnquiryController();
        IProjectService projectService = new BTOProjectController();
        IRegistrationService registrationService = new RegistrationController();

        ReadCSV readCSV = new ReadCSV(applicationService, enquiryService, projectService, registrationService);
        SaveCSV saveCSV = new SaveCSV(applicationService, enquiryService, projectService, registrationService);

        // Read save files
        readCSV.loadManager();
        readCSV.loadApplicant();
        readCSV.loadOfficer();

        readCSV.loadEnquiry();
        readCSV.loadBTOApplication();
        readCSV.loadRegistration();
        readCSV.loadProject();
        
        // Start menu
        Menu menu = new Menu(applicationService, enquiryService, projectService, registrationService);
        menu.displayMenu();

        // Save CSV files
        saveCSV.saveManagers();
        saveCSV.saveApplicants();
        saveCSV.saveOfficers();

        saveCSV.saveProject();
        saveCSV.saveEnquiries();
        saveCSV.saveBTOApplications();
        saveCSV.saveRegistrations();
    }
}