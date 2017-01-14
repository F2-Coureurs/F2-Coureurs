package frontend.controllers;

import backend.Engine;
import backend.GameEngine;
import backend.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectEngineController {
    private Season season;
    private Engine newEngine;
    @FXML private Label engineName;
    @FXML private Label engineQuality;
    @FXML private Label newEngineName;
    @FXML private Label newQuality;
    @FXML private Label newPrice;
    @FXML private Label budget;
    @FXML private Pane engineTable;


    /**
     * Initializes the engine controller.
     */
    @FXML
    public void initialize() {
        season = GameEngine.getInstance().getSeason();
        engineName.setText(season.getPlayerControlledTeam().getEngine().getName());
        engineQuality.setText(season.getPlayerControlledTeam().getEngine().getQualityString());

        engineTable.getChildren().clear();
        int inc = 0;
        for (Engine engine : season.getNonPlayerEngines()) {
            engineTable.getChildren().add(getEnginePane(engine, inc));
            inc++;
        }
    }

    @FXML
    private Pane getEnginePane(Engine engine, int position) {
        Pane returnPane = new Pane();
        returnPane.setOnMouseClicked((event) -> {
            newEngineName.setText(engine.getName());
            newPrice.setText("$ 2,000,000.00");
            newQuality.setText(engine.getQualityString());
            budget.setText(season.getPlayerControlledTeam().getBudgetString(2000000));
            newEngine = engine;
        });
        returnPane.setLayoutY(35 * position);

        Label nameLabel = new Label(engine.getName());
        nameLabel.getStyleClass().add("table-content");
        returnPane.getChildren().add(nameLabel);

        Label priceLabel = new Label("$ 2,000,000.00");
        priceLabel.getStyleClass().add("table-content");
        priceLabel.setLayoutX(220);
        returnPane.getChildren().add(priceLabel);

        Label qualityLabel = new Label(engine.getQualityString());
        qualityLabel.getStyleClass().add("table-content");
        qualityLabel.setLayoutX(440);
        returnPane.getChildren().add(qualityLabel);

        return returnPane;
    }

    /**
     * Saves the engine and deducts the money from the budget.
     * After that, it loads the edit team screen.
     * @param event not using it
     * @throws IOException not throwing it
     */
    @FXML
    public void confirm(ActionEvent event) throws IOException {
        if (newEngine != null) {
            season.getPlayerControlledTeam().changeEngine(newEngine);
            Parent root = FXMLLoader.load(getClass().getResource("/views/edit-team.fxml"));
            Stage stage = (Stage) budget.getScene().getWindow();
            stage.getScene().setRoot(root);
        }
    }

    /**
     * Cancels the transaction and loads the previous screen.
     *
     * @param event not using it
     * @throws IOException not throwing it
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/edit-team.fxml"));
        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
