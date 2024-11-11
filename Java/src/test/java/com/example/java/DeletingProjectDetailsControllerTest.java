package com.example.java;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeletingProjectDetailsControllerTest extends ApplicationTest {

    DeletingProjectDetailsController controller = new DeletingProjectDetailsController();;
    private HashMap<String, HashMap<String, Object>> projectDetails;

    @BeforeEach
    public void setUp() {

        TextField projectId = new TextField();
        Button enterbutton = new Button();

        controller.setProjectId(projectId);
        controller.setEnterButton(enterbutton);

        projectDetails = new HashMap<>();
        HashMap<String, Object> details = new HashMap<>();
        details.put("Project_Name", "Image Hub");
        details.put("Category", "Image Processing");
        details.put("Team_Members", new ArrayList<>());
        details.put("Description", "Standardizing the evaluation of conditional image generation models");
        details.put("Team_Logo", "Test Logo");


        projectDetails.put("001", details);
        controller.setProjectDetails(projectDetails);
    }

    @Test
    public void InvalidProjectIdTest() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("002"); // does not exist in project details

            String result = controller.onClickEnterButton(null);
            assertEquals("You have entered an invalid project ID.", result);
        });
    }

    @Test
    public void DeleteProjectDetailPromptTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");

            if (controller.answer == "no"){

                String result = controller.onClickEnterButton(null);
                assertEquals("Which detail do you want to delete? If Project_Name enter 1. If Team_Members enter 3. If Description enter 4. If Team logo enter 6. You cannot delete the category or the country."
                        , result);
            }
        });
    }

    @Test
    public void DeleteProjectDetailsTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");
            if (controller.answer == "no"){
                if (controller.delDetail == 1){
                    String result = controller.onClickEnterButton(null);
                    assertEquals("Project Name has been deleted.", result);
                }
                else if (controller.delDetail == 3){
                    String result = controller.onClickEnterButton(null);
                    assertEquals("Team member has been deleted.", result);
                }
                else if (controller.delDetail == 4){
                    String result = controller.onClickEnterButton(null);
                    assertEquals("Description has been deleted.", result);
                }
                else if (controller.delDetail == 6){
                    String result = controller.onClickEnterButton(null);
                    assertEquals("Team logo has been deleted.", result);
                }
                else {
                    String result = controller.onClickEnterButton(null);
                    assertEquals("You can enter 1 for project name or 3 for team members or 4 for description or 6 for team logo only", result);
                }
            }
        });
    }

    @Test
    public void NotEitherYesorNoTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");
            if (controller.answer == "invalid"){
                String result = controller.onClickEnterButton(null);
                assertEquals("You have to enter 'yes' or 'no'.", result);
            }
        });
    }


    @Test
    public void DeleteWholeProjectTest() {
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");

            if (controller.answer == "yes") {

                String result = controller.onClickEnterButton(null);
                assertEquals("The project 001 has been deleted.", result);
            }
        });
    }

}