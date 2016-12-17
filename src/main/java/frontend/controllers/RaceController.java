package frontend.controllers;

import backend.Driver;
import backend.GameEngine;
import backend.Season;
import backend.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Random;

public class RaceController {

    @FXML
    private Button startRace;

    @FXML
    private Label winningDriverLabel;

    private Season season;

    @FXML
    public void initialize() {
        season = GameEngine.getInstance().getSeason();
    }

    /**
     * Start the race.
     *
     * @param event the event that called this method
     * @throws Exception if view is not found
     */
    @FXML
    public void startRace(ActionEvent event) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream seasonStart = loader.getResourceAsStream("seasonStart.json");
        Season season = Season.readFromJsonFile(seasonStart);

        // Select random team
        int random = new Random().nextInt(season.getTeams().size());
        Team winningTeam = season.getTeams().get(random);

        // Select first driver from the winning team
        Driver winningDriver = winningTeam.getSecondDriver();

        Parent root = FXMLLoader.load(getClass().getResource("/views/race-result.fxml"));
        Stage stage = (Stage)startRace.getScene().getWindow();

        stage.getScene().setRoot(root);

        // Get label and set it to the winning driver
        winningDriverLabel = (Label) stage.getScene().lookup("#winningDriverLabel");
        winningDriverLabel.setText("Race winner: " + winningDriver.getName());
        winningDriverLabel.setTextFill(Color.BLACK);
    }
}
