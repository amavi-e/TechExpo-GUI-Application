package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class AwardWinningProjectsController extends BaseController {

    @FXML
    public Button beginenterscoresbutton;
    @FXML
    public Button viewresultsbutton;

    private HashMap<String, Integer> Marks = new HashMap<>();
    private boolean marksEntered = false;
    private boolean projectsRanked = false;

    private AwardWinningProjectsData awardWinningProjectsData = new AwardWinningProjectsData();

    @FXML
    public void onBeginEnteringScores(ActionEvent actionEvent) {
        startAwardWinningProcess();
    }

    public void startAwardWinningProcess() {
        gatherMarksFromJudges();
        rankProjects();
        showAlert("Award Winning Process", "The judges have entered the scores.");
        marksEntered = true;
        projectsRanked = true;
    }

    public void gatherMarksFromJudges() {
        RandomSpotlightSelectionController randomSpotlightSelectionController = new RandomSpotlightSelectionController();
        HashMap<String, String> selectedRandomProjects = randomSpotlightSelectionController.getSelectedRandomProjects();

        for (String projectId : selectedRandomProjects.keySet()) {
            int totalMarks = 0;
            for (int judge = 1; judge <= 4; judge++) {
                String judgeMark = promptJudgeForMarks(judge, projectId);
                totalMarks += judgeMark.length();
            }
            Marks.put(projectId, totalMarks);
        }
    }

    public String promptJudgeForMarks(int judge, String projectId) {
        while (true) {
            Optional<String> result = showDialog("Judge Input", "Judge " + judge + " - Project ID: " + projectId + "\nPlease enter your score in asterisks. The maximum is 5");
            if (result.isPresent()) {
                String input = result.get();
                if (input.matches("\\*{1,5}")) {
                    return input;
                } else {
                    showAlert("Invalid Input", "The marks should be entered in asterisks. Minimum 1 and Maximum 5");
                }
            } else {
                showAlert("Invalid Input", "The marks should be entered in asterisks. Minimum 1 and Maximum 5");
            }
        }
    }

    public void rankProjects() {
        String[] projectIds = new String[Marks.size()];
        int[] totalMarks = new int[Marks.size()];

        int index = 0;
        for (String key : Marks.keySet()) {
            projectIds[index] = key;
            totalMarks[index] = Marks.get(key);
            index++;
        }

        for (int i = 0; i < totalMarks.length - 1; i++) {
            for (int j = 0; j < totalMarks.length - i - 1; j++) {
                if (totalMarks[j] < totalMarks[j + 1]) {
                    int tempMark = totalMarks[j];
                    totalMarks[j] = totalMarks[j + 1];
                    totalMarks[j + 1] = tempMark;

                    String tempId = projectIds[j];
                    projectIds[j] = projectIds[j + 1];
                    projectIds[j + 1] = tempId;
                }
            }
        }

        awardWinningProjectsData.setFirstPlaceID(projectIds[0]);
        awardWinningProjectsData.setFirstPlaceCountry((String) getProjectDetails().get(projectIds[0]).get("Country"));
        awardWinningProjectsData.setFirstPlace(totalMarks[0]);

        awardWinningProjectsData.setSecondPlaceID(projectIds[1]);
        awardWinningProjectsData.setSecondPlaceCountry((String) getProjectDetails().get(projectIds[1]).get("Country"));
        awardWinningProjectsData.setSecondPlace(totalMarks[1]);

        awardWinningProjectsData.setThirdPlaceID(projectIds[2]);
        awardWinningProjectsData.setThirdPlaceCountry((String) getProjectDetails().get(projectIds[2]).get("Country"));
        awardWinningProjectsData.setThirdPlace(totalMarks[2]);
    }

    @FXML
    public void onClickViewResults(ActionEvent actionEvent) {
        if (!marksEntered || !projectsRanked) {
            showAlert("Action Required", "Please enter marks and rank projects before viewing results.");
            return;
        }

        try {
            Stage previousStage = (Stage) viewresultsbutton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("visualizing-award-winning-projects.fxml"));
            Parent root = loader.load();

            VisualizingAwardWinningProjectsController controller = loader.getController();
            controller.setAwardWinningProjectsData(awardWinningProjectsData);

            previousStage.setScene(new Scene(root, 600, 600));
            previousStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
