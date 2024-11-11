package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewingProjectDetailsController extends BaseController {

    @FXML
    public TableView<ProjectDetails> viewtable;
    @FXML
    public TableColumn<ProjectDetails, String> projectidcolumn;
    @FXML
    public TableColumn<ProjectDetails, String> projectnamecolumn;
    @FXML
    public TableColumn<ProjectDetails, String> projectcategorycolumn;
    @FXML
    public TableColumn<ProjectDetails, String> teammemberscolumn;
    @FXML
    public TableColumn<ProjectDetails, String> descriptioncolumn;
    @FXML
    public TableColumn<ProjectDetails, String> countrycolumn;
    @FXML
    public TableColumn<ProjectDetails, ImageView> teamlogocolumn;
    @FXML
    public Button gotomainpagebutton;

    private HashMap<String, ProjectDetails> projectDetailsMap = new HashMap<>();

    public void initialize() {
        projectidcolumn.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        projectnamecolumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        projectcategorycolumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        teammemberscolumn.setCellValueFactory(new PropertyValueFactory<>("teamMembers"));
        descriptioncolumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        countrycolumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        teamlogocolumn.setCellValueFactory(new PropertyValueFactory<>("teamLogo"));

        HashMap<String, HashMap<String, Object>> projectDetails = getProjectDetails();
        ArrayList<String> projectIds = new ArrayList<>(projectDetails.keySet());
        sortProjectIds(projectIds);

        for (String id : projectIds) {
            HashMap<String, Object> details = projectDetails.get(id); //gets the details of each ID.
            ProjectDetails project = new ProjectDetails( // the static class object and enters them to the constructor
                    id,
                    (String) details.get("Project_Name"),
                    (String) details.get("Category"),
                    details.get("Team_Members").toString(),
                    (String) details.get("Description"),
                    (String) details.get("Country"),
                    details.get("Team_Logo") != null ? details.get("Team_Logo").toString() : ""
            );
            projectDetailsMap.put(id, project);
        }

        viewtable.getItems().addAll(projectDetailsMap.values());
    }

    public void sortProjectIds(ArrayList<String> projectIds) {
        boolean swapped;
        int length = projectIds.size();
        for (int i = 0; i < length - 1; i++) {
            swapped = false;
            for (int j = 0; j < length - 1 - i; j++) {
                if (projectIds.get(j).compareTo(projectIds.get(j + 1)) > 0) {
                    String temp = projectIds.get(j);
                    projectIds.set(j, projectIds.get(j + 1));
                    projectIds.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    @FXML
    public void onClickMainPageButton(ActionEvent actionEvent) throws IOException {
        Stage previousStage = (Stage) gotomainpagebutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("main-navigation.fxml"));
        previousStage.setScene(new Scene(root, 600, 600));
        previousStage.show();
    }

    public static class ProjectDetails {
        private final String projectId;
        private final String projectName;
        private final String category;
        private final String teamMembers;
        private final String description;
        private final String country;
        private final ImageView teamLogo;

        public ProjectDetails(String projectId, String projectName, String category, String teamMembers, String description, String country, String teamLogoPath) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.category = category;
            this.teamMembers = teamMembers;
            this.description = description;
            this.country = country;

            this.teamLogo = new ImageView();
            if (teamLogoPath != null && !teamLogoPath.isEmpty()) {
                try {
                    Image logoImage = new Image(teamLogoPath); // Load image from URI string
                    this.teamLogo.setImage(logoImage);
                    this.teamLogo.setFitHeight(70);
                    this.teamLogo.setFitWidth(50);
                } catch (Exception e) {
                    this.teamLogo.setImage(null); // Handle exception if image cannot be loaded
                }
            }
        }

        public String getProjectId() {
            return projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public String getCategory() {
            return category;
        }

        public String getTeamMembers() {
            return teamMembers;
        }

        public String getDescription() {
            return description;
        }

        public String getCountry() {
            return country;
        }

        public ImageView getTeamLogo() {
            return teamLogo;
        }
    }

}
