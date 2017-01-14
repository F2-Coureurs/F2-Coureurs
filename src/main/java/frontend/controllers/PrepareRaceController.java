package frontend.controllers;

import backend.GameEngine;
import backend.Season;
import backend.Setup;
import backend.Strategy;
import backend.Team;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PrepareRaceController {
    @FXML private Label engine;
    @FXML private Label firstDriver;
    @FXML private Label secondDriver;
    @FXML private Label strategist;
    @FXML private Label aerodynamicist;
    @FXML private Label mechanic;

    @FXML private Button strategyLowRisk;
    @FXML private Button strategyMediumRisk;
    @FXML private Button strategyHighRisk;

    @FXML private Button setupLowRisk;
    @FXML private Button setupMediumRisk;
    @FXML private Button setupHighRisk;

    @FXML private Button startRace;

    private Season season;

    private Setup userSetup;
    private Strategy userStrategy;

    /**
     * Initialize the prepare screen with the correct values.
     */
    @FXML
    public void initialize() {
        season = GameEngine.getInstance().getSeason();

        Team playerTeam = season.getPlayerControlledTeam();
        engine.setText(playerTeam.getEngine().getName());
        firstDriver.setText(playerTeam.getFirstDriver().getName());
        secondDriver.setText(playerTeam.getSecondDriver().getName());
        strategist.setText(playerTeam.getStrategist().getName());
        aerodynamicist.setText(playerTeam.getAerodynamicist().getName());
        mechanic.setText(playerTeam.getMechanic().getName());
    }

    /**
     * Change setup listener.
     *
     * <p>This method is called when an user clicks on one of the setup options.</p>
     *
     * @param event the event that happened
     */
    @FXML
    public void changeSetupAction(ActionEvent event) {

        // Get the clicked button
        Button button = (Button) event.getSource();

        if (button.getId().equals("setupLowRisk")) {
            userSetup = new Setup(Setup.LOW_RISK);

        } else if (button.getId().equals("setupMediumRisk")) {
            userSetup = new Setup(Setup.MEDIUM_RISK);

        } else if (button.getId().equals("setupHighRisk")) {
            userSetup = new Setup(Setup.HIGH_RISK);
        }

        removeSetupStyleClasses();
        button.getStyleClass().add("green");

        if (userSetup != null && userStrategy != null) {
            startRace.getStyleClass().removeAll("start-race-red");
            startRace.getStyleClass().add("start-race");
            return;
        }
    }

    /**
     * Change strategy listener.
     *
     * <p>This method is called when an user clicks on one of the strategy options.</p>
     *
     * @param event the event that happened
     */
    @FXML
    public void changeStrategyAction(ActionEvent event) {
        Button button = (Button) event.getSource();

        if (button.getId().equals("strategyLowRisk")) {
            userStrategy = new Strategy(Strategy.LOW_RISK);

        } else if (button.getId().equals("strategyMediumRisk")) {
            userStrategy = new Strategy(Strategy.MEDIUM_RISK);

        } else if (button.getId().equals("strategyHighRisk")) {
            userStrategy = new Strategy(Strategy.HIGH_RISK);
        }

        removeStrategyStyleClasses();
        button.getStyleClass().add("green");

        if (userSetup != null && userStrategy != null) {
            startRace.getStyleClass().removeAll("start-race-red");
            startRace.getStyleClass().add("start-race");
            return;
        }

    }

    /**
     * Remove all the style classes of all the strategy buttons.
     */
    private void removeStrategyStyleClasses() {
        strategyLowRisk.getStyleClass().removeAll("green");
        strategyMediumRisk.getStyleClass().removeAll("green");
        strategyHighRisk.getStyleClass().removeAll("green");
    }

    /**
     * Remove all the style classes of all the setup buttons.
     */
    private void removeSetupStyleClasses() {
        setupLowRisk.getStyleClass().removeAll("green");
        setupMediumRisk.getStyleClass().removeAll("green");
        setupHighRisk.getStyleClass().removeAll("green");
    }

    /**
     * Start the race.
     *
     * @param event the event that called this method
     * @throws Exception if view is not found
     */
    @FXML
    public void startRace(ActionEvent event) throws Exception {
        if (userSetup == null || userStrategy == null) {
            // TODO Create flashing button.
            Button button = (Button) event.getSource();
            button.getStyleClass().removeAll("start-race");
            button.getStyleClass().add("start-race-red");

            System.out.println("User setup or strategy is empty. The race cannot start without.");
            return;
        }

        Pane pane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/race-result.fxml"));
            pane = loader.load();

            // Get controller
            RaceController raceController = loader.getController();
            raceController.startRace(userSetup, userStrategy);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.getScene().setRoot(pane);
    }
}
