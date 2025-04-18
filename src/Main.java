
import boundary.Menu;
import database.ReadCSV;
import database.SaveCSV;



public class Main {
    public static void main(String[] args) {
        // Read save files
        ReadCSV.loadManager();
        ReadCSV.loadApplicant();
        ReadCSV.loadOfficer();

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