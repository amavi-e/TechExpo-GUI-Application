package com.example.java;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddingProjectDetailsControllerTest extends ApplicationTest {

    AddingProjectDetailsController controller = new AddingProjectDetailsController();;

    @BeforeEach
    public void setUp() {

        TextField projectId = new TextField();
        TextField projectName = new TextField();
        TextField category = new TextField();
        TextField teamMemberCount = new TextField();
        TextField description = new TextField();
        TextField country = new TextField();
        ImageView imageteamlogo = new ImageView();
        Button doneButton = new Button();
        ListView<String> teamMembersList = new ListView<>();

        controller.setProjectId(projectId);
        controller.setProjectName(projectName);
        controller.setCategory(category);
        controller.setTeamMemberCount(teamMemberCount);
        controller.setDescription(description);
        controller.setCountry(country);
        controller.setImageteamlogo(imageteamlogo);
        controller.setDoneButton(doneButton);
        controller.setTeamMembersList(teamMembersList);
    }

    @Test
    public void EmptyFieldsDoneTest() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("");
            controller.getProjectName().setText("");
            controller.getCategory().setText("");
            controller.getTeamMemberCount().setText("");
            controller.getDescription().setText("");
            controller.getCountry().setText("");
            controller.getImageteamlogo().setImage(null);

            String result = controller.onClickDone(null);
            assertEquals("Please add all the details before proceeding.", result);
        });
    }


    @Test
    public void OneEmptyFieldDoneTest() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("002");
            controller.getProjectName().setText("Image Hub");
            controller.getCategory().setText("Image Processing");
            controller.getTeamMemberCount().setText("3");
            controller.getDescription().setText("Standardizing the evaluation of conditional image generation models");
            controller.getCountry().setText("Sri Lanka");
            controller.getImageteamlogo().setImage(null);

            String result = controller.onClickDone(null);
            assertEquals("Please add all the details before proceeding.", result);
        });
    }

    @Test
    public void ExistingProjectIdDoneTest() {
        Platform.runLater(() -> {

            controller.getProjectId().setText("002");
            controller.getProjectName().setText("Image Hub");
            controller.getCategory().setText("Image Processing");
            controller.getTeamMemberCount().setText("3");
            controller.getDescription().setText("Standardizing the evaluation of conditional image generation models");
            controller.getCountry().setText("Sri Lanka");
            controller.getImageteamlogo().setImage(new Image("file:image.jpg"));


            controller.getProjectIdList().add("002");

            String result = controller.onClickDone(null);
            assertEquals("This ID already exists. Please enter a new project ID.", result);
        });
    }

    @Test
    public void InvalidTeamMemberCountDoneTest1() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("002");
            controller.getProjectName().setText("Image Hub");
            controller.getCategory().setText("Image Processing");
            controller.getTeamMemberCount().setText("twothree"); // Invalid team member count


            String result = controller.onClickDone(null);
            assertEquals("Please enter a valid number for team member count.", result);
        });
    }

    @Test
    public void InvalidTeamMemberCountDoneTest2() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("002");
            controller.getProjectName().setText("Image Hub");
            controller.getCategory().setText("Image Processing");
            controller.getTeamMemberCount().setText("-1"); // Invalid team member count


            String result = controller.onClickDone(null);
            assertEquals("The team member count should be more than 0", result);
        });
    }

    @Test
    public void testOnClickDoneSuccessful() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("002");
            controller.getProjectName().setText("Image Hub");
            controller.getCategory().setText("Image Processing");
            controller.getTeamMemberCount().setText("3");
            controller.getDescription().setText("Standardizing the evaluation of conditional image generation models");
            controller.getCountry().setText("Sri Lanka");
            controller.getImageteamlogo().setImage(new Image("file:image.jpeg"));

            // to ensure the id is new
            controller.getProjectIdList().clear();

            String result = controller.onClickDone(null);
            assertEquals("Project details added successfully.", result);
        });
    }
}
