package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


import java.io.IOException;

public class MainNavigationController extends BaseController{
    @FXML
    public Button addingmethodbutton;

    @FXML
    public Button deletingmethodbutton;

    @FXML
    public Button updatingmethodbutton;

    @FXML
    public Button viewingmethodbutton;

    @FXML
    public Button randomselectionbutton;


    public void onAddingMethodButton(ActionEvent actionEvent) throws IOException {
        Stage previousStage = (Stage) addingmethodbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("adding-project-details.fxml"));
        previousStage.setScene(new Scene(root, 650, 650));
        previousStage.show();
    }



    public void onDeletingMethodButton(ActionEvent actionEvent) throws IOException {
        Stage previousStage =(Stage) deletingmethodbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("DeletingProjectDetails.fxml"));
        previousStage.setScene(new Scene(root, 600, 250));
        previousStage.show();
    }

    public void onUpdatingMethodButton(ActionEvent actionEvent) throws IOException {
        Stage previousStage =(Stage) updatingmethodbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("updating-project-details.fxml"));
        previousStage.setScene(new Scene(root, 600, 250));
        previousStage.show();

    }

    public void onViewingMethodButton(ActionEvent actionEvent) throws IOException {
        Stage previousStage =(Stage) viewingmethodbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("viewing-project-details.fxml"));
        previousStage.setScene(new Scene(root, 1000, 600));
        previousStage.show();

    }

    HashMap<String, HashMap<String, Object>> projectDetails = getProjectDetails();

    ArrayList<String> categoryList = new ArrayList<>();

    public ArrayList<String> getCategoryList() {
        return categoryList;
    }


    public void onRandomSelectionClickButton(ActionEvent actionEvent) throws IOException {


        String projectCategory;
        for (String key : projectDetails.keySet()) {
            projectCategory = (String) projectDetails.get(key).get("Category");
            if (!categoryList.contains(projectCategory)) {
                categoryList.add(projectCategory);
            }
        }

        if (categoryList.size()<3){
            showAlert("Not Enough Projects", "You have to enter projects in at least 3 different categories");
        }
        else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Random Spotlight Selection");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to proceed? You will not be able to add, update or delete anymore details.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage previousStage = (Stage) randomselectionbutton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("random-spotlight-selection.fxml"));
                previousStage.setScene(new Scene(root, 600, 300));
                previousStage.show();

            }
        }

    }
}
