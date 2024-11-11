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

class UpdatingProjectDetailsControllerTest extends ApplicationTest {

    UpdatingProjectDetailsController controller = new UpdatingProjectDetailsController();
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
    public void InvalidProjectIdTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("002");

            String result = controller.onClickEnterButton(null);
            assertEquals("You have entered an invalid project ID.", result);
        });
    }

    @Test
    public void InvalidUpdateDetailTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");

            if (controller.answer == -1){
                String result = controller.onClickEnterButton(null);
                assertEquals("You have entered an invalid number.", result);
            }
        });
    }

    @Test
    public void ValidUpdateDetailNameTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");

            if (controller.answer == 1){
                String result = controller.onClickEnterButton(null);
                assertEquals("Project name has been updated", result);
            }
        });
    }

    @Test
    public void ValidUpdateDetailTeamMemberTest(){
        Platform.runLater(() -> {
            controller.getProjectId().setText("001");

            if (controller.answer == 3){
                String result = controller.onClickEnterButton(null);
                assertEquals("Team member has been updated", result);
            }
        });
    }

}