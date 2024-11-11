package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UpdatingProjectDetailsController extends BaseController {

    @FXML
    public TextField projectId;

    @FXML
    public Button enterbutton;

    @FXML
    public Button gotomainpagebutton;

    @FXML
    public Button addtotextfilebutton;

    HashMap<String, HashMap<String, Object>> projectDetails = getProjectDetails();

    // Getters
    public TextField getProjectId() {
        return projectId;
    }

    public Button getEnterButton() {
        return enterbutton;
    }

    // Setters
    public void setProjectDetails(HashMap<String, HashMap<String, Object>> projectDetails) {
        this.projectDetails = projectDetails;
    }

    public void setProjectId(TextField projectId) {
        this.projectId = projectId;
    }

    public void setEnterButton(Button enterbutton) {
        this.enterbutton = enterbutton;
    }


    String message = null;
    int answer = 0;

    public String onClickEnterButton(ActionEvent actionEvent) {

        String projectId = this.projectId.getText();
        if (projectDetails.containsKey(projectId)) {
            Optional<String> result = showDialog("Update project detail", "Which detail do you want to update? If Project_Name enter 1. If Category enter 2. If Team_Members enter 3. If Description enter 4. If Country enter 5. If Team Logo enter 6.");

            if (result.isPresent()) {
                int detailToUpdate = Integer.parseInt(result.get());
                HashMap<String, Object> projectDetailTemp = projectDetails.get(projectId);

                switch (detailToUpdate) {
                    case 1:
                        answer = 1;
                        Optional<String> nameResult = showDialog("Update Project Name", "Enter the new project name:");
                        nameResult.ifPresent(newName -> {
                            projectDetailTemp.put("Project_Name", newName);
                            showAlert("Success", "Project name has been updated.");
                            message = "Project name has been updated";
                        });
                        break;

                    case 2:
                        answer = 2;
                        Optional<String> categoryResult = showDialog("Update Category", "Enter the new category:");
                        categoryResult.ifPresent(newCategory -> {
                            projectDetailTemp.put("Category", newCategory);
                            showAlert("Success", "Category has been updated.");
                            message = "Category has been updated";
                        });
                        break;

                    case 3:
                        answer = 3;
                        Optional<String> teamMemberResult = showDialog("Update Team Member", "Enter the full name of the team member you want to update:");
                        teamMemberResult.ifPresent(oldName -> {
                            ArrayList<String> teamMembers = (ArrayList<String>) projectDetailTemp.get("Team_Members");
                            if (teamMembers.contains(oldName)) {
                                Optional<String> newTeamMemberResult = showDialog("Update Team Member", "Enter the new name for this team member:");
                                newTeamMemberResult.ifPresent(newName -> {
                                    teamMembers.set(teamMembers.indexOf(oldName), newName);
                                    showAlert("Success", "Team member has been updated.");
                                    message = "Team member has been updated";
                                });
                            } else {
                                showAlert("Fail", "Team member not found.");
                            }
                        });
                        break;

                    case 4:
                        answer = 4;
                        Optional<String> descriptionResult = showDialog("Update Description", "Enter the new description:");
                        descriptionResult.ifPresent(newDescription -> {
                            projectDetailTemp.put("Description", newDescription);
                            showAlert("Success", "Description has been updated.");
                            message = "Description has been updated";
                        });
                        break;

                    case 5:
                        answer = 5;
                        Optional<String> countryResult = showDialog("Update Country", "Enter the new country:");
                        countryResult.ifPresent(newCountry -> {
                            projectDetailTemp.put("Country", newCountry);
                            showAlert("Success", "Country has been updated.");
                            message = "Country has been updated";
                        });
                        break;

                    case 6:
                        answer = 6;
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Choose new team logo image");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
                        );
                        Stage stage = (Stage) enterbutton.getScene().getWindow();
                        File selectedFile = fileChooser.showOpenDialog(stage);

                        if (selectedFile != null) {
                            try {
                                // Store the path as a URI string
                                String newLogoPath = selectedFile.toURI().toString();
                                projectDetailTemp.put("Team_Logo", newLogoPath);
                                showAlert("Success", "Team logo has been updated.");
                                message = "Team logo has been updated";
                            } catch (Exception e) {
                                showAlert("Error", "Failed to update team logo.");
                                e.printStackTrace();
                            }
                        } else {
                            showAlert("Error", "No file selected.");
                        }
                        break;



                    default:
                        showAlert("Fail", "You have entered an invalid number.");
                        answer = -1;
                        message = "You have entered an invalid number.";
                        break;
                }


                try {
                    SavingProjectDetailstoTextFile savingProjectDetailstoTextFile = new SavingProjectDetailstoTextFile();
                    savingProjectDetailstoTextFile.createProjectDetailstoTextFile();
                } catch (IOException e) {
                    showAlert("Error", "Failed to save project details to text file.");
                    e.printStackTrace();
                }


            }
        } else {
            showAlert("Fail", "You have entered an invalid project ID.");
            message = "You have entered an invalid project ID.";
        }
        return message;
    }

    public void onClickMainPageButton(ActionEvent actionEvent) throws IOException {
        Stage previousStage = (Stage) gotomainpagebutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("main-navigation.fxml"));
        previousStage.setScene(new Scene(root, 600, 600));
        previousStage.show();
    }
}
