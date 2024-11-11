package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class RandomSpotlightSelectionController extends BaseController {

    private static HashMap<String, String> selectedRandomProjects = new HashMap<>();


    @FXML
    public Button startrandomselectionbutton;

    public HashMap<String, String> getSelectedRandomProjects() {
        return selectedRandomProjects;
    }

    HashMap<String, HashMap<String, Object>> projectDetails = getProjectDetails();

    MainNavigationController controller = new MainNavigationController();
    ArrayList<String> categoryList = controller.getCategoryList();

    @FXML
    public void onClickStartRandomSelectionButton(ActionEvent actionEvent) throws FileNotFoundException {
        do {
            Random rand = new Random();
            ArrayList<String> prevCategory = new ArrayList<>();
            selectedRandomProjects.clear(); // If the selection starts again, it will be empty

            File file = new File("Project Details.txt");
            List<String> lines = new ArrayList<>();
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                lines.add(scan.nextLine());
            }
            scan.close();


            for (int i = 0; i < projectDetails.size(); i++) {
                int randomIndex = rand.nextInt(lines.size());
                String randomLine = lines.get(randomIndex);

                String[] details = randomLine.split("[,\\s\\{}\\[\\]]+");
                if (details.length == 0) {
                    continue;
                }
                String projectIdLine = details[0];

                HashMap<String, Object> projectDetail = projectDetails.get(projectIdLine);
                String category = null;
                if (projectDetail != null) {
                    category = (String) projectDetail.get("Category");
                } else {
                    showAlert("Error", "Project ID " + projectIdLine + " not found in project details.");
                    continue;
                }

                if (!(selectedRandomProjects.containsKey(projectIdLine)) && !(prevCategory.contains(category))) {
                    selectedRandomProjects.put(projectIdLine, randomLine);
                    prevCategory.add(category);
                }
            }
        } while (selectedRandomProjects.size() < categoryList.size());

        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, String> entry : selectedRandomProjects.entrySet()) {
            message.append("Project ID: ").append(entry.getKey())
                    .append("\nDetails: ").append(entry.getValue()).append("\n\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selected Projects");
        alert.setHeaderText("Selected Projects Details");
        alert.setContentText(message.toString());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    openNextScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void openNextScene() throws IOException {
        Stage previousStage = (Stage) startrandomselectionbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("awardwinningprojects.fxml"));
        previousStage.setScene(new Scene(root, 600, 400));
        previousStage.show();
    }
}
