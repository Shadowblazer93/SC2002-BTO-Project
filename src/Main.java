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
import interfaces.IApplicantService;
import interfaces.IApplicationService;
import interfaces.IEnquiryService;
import interfaces.IManagerService;
import interfaces.IOfficerService;
import interfaces.IProjectService;
import interfaces.IRegistrationService;


public class Main {
    public static void main(String[] args) {

        IApplicantService applicantService = new ApplicantController();
        IOfficerService officerService = new OfficerController();
        IManagerService managerService = new ManagerController();
        IApplicationService applicationService = new ApplicationController();
        IEnquiryService enquiryService = new EnquiryController();
        IProjectService projectService = new BTOProjectController();
        IRegistrationService registrationService = new RegistrationController();

        ReadCSV readCSV = new ReadCSV(applicantService, officerService, managerService, 
                                        applicationService, enquiryService, projectService, registrationService);
        SaveCSV saveCSV = new SaveCSV(applicantService, officerService, managerService,
                                        applicationService, enquiryService, projectService, registrationService);

        // Read save files
        readCSV.loadManager();
        readCSV.loadApplicant();
        readCSV.loadOfficer();
        readCSV.loadEnquiry();
        readCSV.loadBTOApplication();
        readCSV.loadRegistration();  
        readCSV.loadProject(); 
        
        // Start menu
        Menu menu = new Menu(applicantService, officerService, managerService, 
                            applicationService, enquiryService, projectService, registrationService);
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