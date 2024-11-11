package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizingAwardWinningProjectsController implements Initializable {

    @FXML
    public Button exitbutton;

    @FXML
    private BarChart<String, Number> resultsChart;

    private AwardWinningProjectsData awardWinningProjectsData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsChart.setTitle("Points");

        CategoryAxis xAxis = (CategoryAxis) resultsChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) resultsChart.getYAxis();

        xAxis.setLabel("Projects");
        yAxis.setLabel("Points");
    }

    public void setAwardWinningProjectsData(AwardWinningProjectsData awardWinningProjectsData) {
        this.awardWinningProjectsData = awardWinningProjectsData;

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Points");

        series.getData().add(new XYChart.Data<>(awardWinningProjectsData.getFirstPlaceID() + ", " + awardWinningProjectsData.getFirstPlaceCountry(), awardWinningProjectsData.getFirstPlace()));
        series.getData().add(new XYChart.Data<>(awardWinningProjectsData.getSecondPlaceID() + ", " + awardWinningProjectsData.getSecondPlaceCountry(), awardWinningProjectsData.getSecondPlace()));
        series.getData().add(new XYChart.Data<>(awardWinningProjectsData.getThirdPlaceID() + ", " + awardWinningProjectsData.getThirdPlaceCountry(), awardWinningProjectsData.getThirdPlace()));

        resultsChart.getData().add(series);
    }

    public void onclickExitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}