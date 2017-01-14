package frontend.controllers;

import backend.GameEngine;
import backend.Season;
import backend.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

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
    @FXML private MediaView mediaView;
    @FXML private MediaPlayer mediaPlayer;
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
        mediaView.setFitHeight(1080);
        mediaView.setFitWidth(1920);
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);




        // REMOVE NEXT LINE IF NOT IN DEBUG MODE
        transferButtonInit();
    }

    private void transferButtonInit() {
        Button transferButton = new Button();
        transferButton.setLayoutX(1500);
        transferButton.setLayoutY(500);
        transferButton.setText("TransferTest");
        transferButton.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/views/message.fxml"));
                Parent root;
                root = loader.load();
                frontend.controllers.TransferOfferController controller = loader.getController();
                controller.load(season.getTeams().get(1),
                        season.getPlayerControlledTeam().getFirstDriver());
                System.out.println("handlnig event");
                Stage stage = (Stage) teamName.getScene().getWindow();
                stage.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Pane homePane = (Pane) teamName.getParent().getParent();
        homePane.getChildren().add(transferButton);
    }
}
