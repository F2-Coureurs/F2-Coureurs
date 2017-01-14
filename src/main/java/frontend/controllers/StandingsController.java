package frontend.controllers;

import backend.GameEngine;
import backend.Season;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StandingsController {
    private Season season;
    @FXML private Pane teamTable;
    @FXML private Pane driverTable;

    /**
     * Show the champion standings.
     */
    public void initialize() {
        season = GameEngine.getInstance().getSeason();

        teamTable.getChildren().add(setTeamPane());
        driverTable.getChildren().add(setDriverPane());
    }

    private Pane setTeamPane() {
        Pane returnPane = new Pane();
        for (int i = 1; i <= 11; i++) {
            Label teamNLabel = new Label(season.getTeam(i).getName());
            teamNLabel.setLayoutY(30 * i);

            Label teamSLabel = new Label(String.valueOf(season.getTeam(i).getPointsThisSeason()));
            teamSLabel.setLayoutX(580);
            teamSLabel.setLayoutY(30 * i);

            if (teamNLabel.getText().equals(season.getPlayerControlledTeam().getName())) {
                teamNLabel.setStyle("-fx-text-fill: red");
                teamSLabel.setStyle("-fx-text-fill: red");
            }
            returnPane.getChildren().add(teamNLabel);
            returnPane.getChildren().add(teamSLabel);
        }
        return returnPane;
    }

    private Pane setDriverPane() {
        Pane returnPane = new Pane();
        for (int i = 1; i <= 22; i++) {
            Label driverNLabel = new Label(season.getDriver(i).getName());
            driverNLabel.setLayoutY(30 * i);

            Label driverTLabel = new Label(season.getDriver(i).getTeamName(season));
            driverTLabel.setLayoutX(250);
            driverTLabel.setLayoutY(30 * i);

            Label driverPLabel = new Label(String.valueOf(season.getDriver(i).getPoints()));
            driverPLabel.setLayoutX(580);
            driverPLabel.setLayoutY(30 * i);

            if (driverNLabel.getText().equals(
                    season.getPlayerControlledTeam().getFirstDriver().getName())
                    || driverNLabel.getText().equals(
                            season.getPlayerControlledTeam().getSecondDriver().getName())) {
                driverNLabel.setStyle("-fx-text-fill: red");
                driverTLabel.setStyle("-fx-text-fill: red");
                driverPLabel.setStyle("-fx-text-fill: red");
            }

            returnPane.getChildren().add(driverNLabel);
            returnPane.getChildren().add(driverTLabel);
            returnPane.getChildren().add(driverPLabel);
        }
        return returnPane;
    }
}

