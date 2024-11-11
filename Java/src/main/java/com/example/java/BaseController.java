package com.example.java;

import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class BaseController {

    BaseController(){}

    private static HashMap<String, HashMap<String, Object>> projectDetails = new HashMap<>();

    public static HashMap<String, HashMap<String, Object>> getProjectDetails() {
        return projectDetails;
    }


    public void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Optional<String> showDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        return dialog.showAndWait();
    }
}

