package frontend.controllers;

import backend.GameEngine;
import backend.Season;
import backend.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    @FXML private Label teamName;
    @FXML private Label engineName;
    @FXML private Label firstDriverName;
    @FXML private Label secondDriverName;
    @FXML private Label strategistName;
    @FXML private Label aerodynamicistName;
    @FXML private Label mechanicName;
    @FXML private Label nextCircuit;
    @FXML private Label currentBudget;
    private Season season;

    /**
     * Initialize the home screen with the correct values.
     */
    @FXML
    public void initialize() {
        season = GameEngine.getInstance().getSeason();

        Team playerTeam = season.getPlayerControlledTeam();
        teamName.setText(playerTeam.getName());
        engineName.setText(playerTeam.getEngine().getName());
        firstDriverName.setText(playerTeam.getFirstDriver().getName());
        secondDriverName.setText(playerTeam.getSecondDriver().getName());
        strategistName.setText(playerTeam.getStrategist().getName());
        aerodynamicistName.setText(playerTeam.getAerodynamicist().getName());
        mechanicName.setText(playerTeam.getMechanic().getName());
        nextCircuit.setText(season.getCurrentRound().getTrackName());
        currentBudget.setText(playerTeam.getBudgetString());

    }

}
