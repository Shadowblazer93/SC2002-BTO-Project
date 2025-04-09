package database;
import java.io.File;
import java.util.Scanner;
import entity.project.BTOProject;

public class ReadCSV {
    public void loadManager() {

    }

    public void loadOfficer() {

    }

    public void loadApplicant() {

    }

    public List<BTOProject> loadProject() {
        File projectsFile = new File("src/database/projects.csv");
        Scanner projectsReader = new Scanner(projectsFile);
        while (projectsReader.hasNextLine()) {
            String data = projectsReader.nextLine();
            String[] projectData = data.split(",");
            String projectName = projectData[0];
            String projectLocation = projectData[1];
            String projectType = projectData[2];
            int projectSize = Integer.parseInt(projectData[3]);
            String projectStatus = projectData[4];
        }
    }

    public void loadEnquiry() {

    }

    public void loadApplication() {

    }

    public void loadRegistration() {

    }
}
