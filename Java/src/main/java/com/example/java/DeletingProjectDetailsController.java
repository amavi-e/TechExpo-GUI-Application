package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DeletingProjectDetailsController extends BaseController {

    @FXML
    public TextField projectId;
    @FXML
    public Button enterbutton;
    @FXML
    public Button gotomainpagebutton;

    SavingProjectDetailstoTextFile savingProjectDetailstoTextFile = new SavingProjectDetailstoTextFile();

    HashMap<String, HashMap<String, Object>> projectDetails = getProjectDetails();

    String message = null;
    String answer = null;
    int delDetail = 0;


    public TextField getProjectId() {
        return projectId;
    }

    public Button getEnterButton() {
        return enterbutton;
    }


    public void setProjectDetails(HashMap<String, HashMap<String, Object>> projectDetails) {
        this.projectDetails = projectDetails;
    }

    public void setProjectId(TextField projectId) {
        this.projectId = projectId;
    }

    public void setEnterButton(Button enterbutton) {
        this.enterbutton = enterbutton;
    }

    public String onClickEnterButton(ActionEvent actionEvent) {
        String projectId = this.projectId.getText();

        if (projectDetails.containsKey(projectId)) {
            Optional<String> result = showDialog("Delete whole project or not", "Do you want to delete the whole project or not? Please enter 'yes' or 'no'");

            if (result.isPresent()) {
                if (result.get().equals("yes")) {
                    answer = "yes";
                    projectDetails.remove(projectId);
                    showAlert("Success", "The project " + projectId + " has been deleted.");
                    message = "The project " + projectId + " has been deleted.";
                    try {
                        savingProjectDetailstoTextFile.createProjectDetailstoTextFile();
                    } catch (IOException e) {
                        showAlert("Error", "Failed to save project details to text file.");
                        e.printStackTrace();
                    }
                } else if (result.get().equals("no")) {
                    answer = "no";
                    Optional<String> result1 = showDialog("Delete project detail", "Which detail do you want to delete? If Project_Name enter 1. If Team_Members enter 3. If Description enter 4. If Team logo enter 6. You cannot delete the category or the country.");
                    message = "Which detail do you want to delete? If Project_Name enter 1. If Team_Members enter 3. If Description enter 4. If Team logo enter 6. You cannot delete the category or the country.";


                    if (result1.isPresent()) {
                        int detailToDelete = Integer.parseInt(result1.get());
                        HashMap<String, Object> DelProjctDetailTemp = projectDetails.get(projectId);

                        switch (detailToDelete) {
                            case 1:
                                delDetail = 1;
                                DelProjctDetailTemp.remove("Project_Name");
                                showAlert("Success", "Project Name has been deleted.");
                                message = "Project Name has been deleted.";
                                break;

                            case 3:
                                delDetail = 3;
                                Optional<String> result2 = showDialog("Team Member Deleting", "Enter the full name that you want to delete. Please ensure that the name is exactly the same as the one you entered");

                                if (result2.isPresent()) {
                                    String memberToDelete = result2.get();
                                    ArrayList<String> Team_Members = (ArrayList<String>) DelProjctDetailTemp.get("Team_Members");

                                    if (Team_Members.remove(memberToDelete)) {
                                        showAlert("Success", "Team member has been deleted.");
                                        message = "Team member has been deleted.";
                                    } else {
                                        showAlert("Error", "Team member not found.");
                                        message = "Team member not found.";
                                    }
                                }
                                break;

                            case 4:
                                delDetail = 4;
                                DelProjctDetailTemp.remove("Description");
                                showAlert("Success", "Description has been deleted.");
                                message = "Description has been deleted.";
                                break;

                            case 6:
                                delDetail = 6;
                                DelProjctDetailTemp.remove("Team_Logo");
                                showAlert("Success", "Team logo has been deleted.");
                                message = "Team logo has been deleted.";
                                break;

                            default:
                                showAlert("Error", "You can enter 1 for project name or 3 for team members or 4 for description or 6 for team logo only");
                                message = "You can enter 1 for project name or 3 for team members or 4 for description or 6 for team logo only";
                                break;
                        }

                        try {
                            savingProjectDetailstoTextFile.createProjectDetailstoTextFile();
                        } catch (IOException e) {
                            showAlert("Error", "Failed to save project details to text file.");
                            e.printStackTrace();
                        }
                    }
                } else {
                    showAlert("Error", "You have to enter 'yes' or 'no'.");
                    answer = "invalid";
                    message = "You have to enter 'yes' or 'no'.";
                }
            }
        } else {
            showAlert("Error", "You have entered an invalid project ID");
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
