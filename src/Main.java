
import boundary.Menu;
import controller.user.ManagerController;
import database.ReadCSV;
import database.SaveCSV;
import entity.user.Manager;
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

        // Start menu
        Menu menu = new Menu();
        menu.displayMenu();

        // Save CSV files
        SaveCSV.saveProject();
        SaveCSV.saveManageras();
    }
}