package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class AddingProjectDetailsController extends BaseController {

    @FXML
    private ImageView imageteamlogo;
    @FXML
    private Button uploadteamlogobutton;
    @FXML
    private TextField projectId;
    @FXML
    private TextField projectName;
    @FXML
    private TextField category;
    @FXML
    private TextField teamMemberCount;
    @FXML
    private TextField description;
    @FXML
    private TextField country;
    @FXML
    private Button doneButton;
    @FXML
    private Button addMembersButton;
    @FXML
    private ListView<String> teamMembersList;

    private ArrayList<String> teamMembers = new ArrayList<>();
    private File teamLogoFile;
    private static ArrayList<String> projectIdList = new ArrayList<>();

    String message = null;


    public static ArrayList getProjectIdList() {
        return projectIdList;
    }

    public TextField getProjectId() {
        return projectId;
    }

    public TextField getProjectName() {
        return projectName;
    }

    public TextField getCategory() {
        return category;
    }

    public TextField getTeamMemberCount() {
        return teamMemberCount;
    }

    public TextField getDescription() {
        return description;
    }

    public TextField getCountry() {
        return country;
    }

    public ImageView getImageteamlogo() {
        return imageteamlogo;
    }

    public Button getDoneButton() {
        return doneButton;
    }

    public ListView<String> getTeamMembersList() {
        return teamMembersList;
    }


    public void setProjectId(TextField projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(TextField projectName) {
        this.projectName = projectName;
    }

    public void setCategory(TextField category) {
        this.category = category;
    }

    public void setTeamMemberCount(TextField teamMemberCount) {
        this.teamMemberCount = teamMemberCount;
    }

    public void setDescription(TextField description) {
        this.description = description;
    }

    public void setCountry(TextField country) {
        this.country = country;
    }

    public void setImageteamlogo(ImageView imageteamlogo) {
        this.imageteamlogo = imageteamlogo;
    }

    public void setDoneButton(Button doneButton) {
        this.doneButton = doneButton;
    }

    public void setTeamMembersList(ListView<String> teamMembersList) {
        this.teamMembersList = teamMembersList;
    }

    public String onClickDone(ActionEvent actionEvent) {
        if (teamMembers.isEmpty() || projectId.getText().isEmpty() || projectName.getText().isEmpty() || category.getText().isEmpty() || description.getText().isEmpty() || country.getText().isEmpty() || imageteamlogo.getImage() == null) {
            showAlert("Error", "Please add all the details before proceeding.");
            message = "Please add all the details before proceeding.";
        }

        String id = projectId.getText();
        if (projectIdList.contains(id)) {
            showAlert("Error", "This ID already exists. Please enter a new project ID.");
            message =  "This ID already exists. Please enter a new project ID.";
        } else {
            projectIdList.add(id);
            HashMap<String, Object> details = new HashMap<>();
            details.put("Project_Name", projectName.getText());
            details.put("Category", category.getText());
            details.put("Team_Members", teamMembers);
            details.put("Description", description.getText());
            details.put("Country", country.getText());
            if (teamLogoFile != null) {
                details.put("Team_Logo", teamLogoFile.toURI().toString());
            }
            getProjectDetails().put(id, details);

            SavingProjectDetailstoTextFile savingProjectDetailstoTextFile = new SavingProjectDetailstoTextFile();
            try {
                savingProjectDetailstoTextFile.createProjectDetailstoTextFile();
            } catch (IOException e) {
                showAlert("Error", "Failed to save project details to text file.");
                e.printStackTrace();
            }

            message = "Project details added successfully.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Project details added successfully.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Stage previousStage = (Stage) doneButton.getScene().getWindow();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("main-navigation.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    previousStage.setScene(new Scene(root, 650, 650));
                    previousStage.show();
                }
            });
        }
        return message;
    }

    public String onClickAddMembers(ActionEvent actionEvent) {
        int count = 0;
        try {
            count = Integer.parseInt(teamMemberCount.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for team member count.");
            message = "Please enter a valid number for team member count.";
        }

        if (count <= 0) {
            showAlert("Error", "The team member count should be more than 0");
            message = "The team member count should be more than 0";
        }

        teamMembers.clear();
        teamMembersList.getItems().clear();

        for (int i = 0; i < count; i++) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Team Member Input");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter the name of member " + (i + 1) + ":");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                teamMembers.add(name);
                teamMembersList.getItems().add(name);
            });
        }
        return message;
    }

    public void onUploadTeamLogo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        teamLogoFile = fileChooser.showOpenDialog(uploadteamlogobutton.getScene().getWindow());
        if (teamLogoFile != null) {
            Image teamLogoImage = new Image(teamLogoFile.toURI().toString());
            imageteamlogo.setImage(teamLogoImage);
        } else {
            showAlert("Error", "No file selected. Please select an image file.");
        }
    }
}
