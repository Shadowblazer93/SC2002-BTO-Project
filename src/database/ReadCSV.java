package database;

import controller.BTOProjectController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCSV {
    public void loadManager() {

    }

    public void loadOfficer() {

    }

    public void loadApplicant() {

    }

    public void loadProject() {
        BTOProjectController projectController = new BTOProjectController();
        File projectsFile = new File("src/database/ProjectList.csv");
        try (Scanner projectsReader = new Scanner(projectsFile)) {
            while (projectsReader.hasNextLine()) {
                String data = projectsReader.nextLine();
                String[] projectData = data.split(",");
                String projectName = projectData[0];
                String projectLocation = projectData[1];
                String projectType = projectData[2];
                int projectSize = Integer.parseInt(projectData[3]);
                String projectStatus = projectData[4];
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public void loadEnquiry() {

    }

    public void loadApplication() {

    }

    public void loadRegistration() {

    }
}
