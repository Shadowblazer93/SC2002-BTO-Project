
import boundary.Menu;
import database.ReadCSV;
import database.SaveCSV;



public class Main {
    public static void main(String[] args) {
        // Read save files
        ReadCSV.loadProject();

        // Start menu
        Menu menu = new Menu();
        menu.displayMenu();

        // Save CSV files
        SaveCSV.saveProject();
    }
}