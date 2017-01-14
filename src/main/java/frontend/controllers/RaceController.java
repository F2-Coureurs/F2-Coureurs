package frontend.controllers;

import backend.Driver;
import backend.GameEngine;
import backend.Race;
import backend.Season;
import backend.Setup;
import backend.Strategy;
import backend.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.List;

public class RaceController {

    @FXML
    private TableView<Driver> resultsTable;

    private Season season;

    /**
     * Start the race.
     *
     * @param setup the setup of the user
     * @param strategy the strategy of the user
     */
    public void startRace(Setup setup, Strategy strategy) {
        season = GameEngine.getInstance().getSeason();

        // Create new race
        Race race = new Race(
                setup,
                strategy,
                "Monaco",
                1
        );

        // Create list of drivers
        ObservableList<Driver> drivers =
                FXCollections.observableArrayList(race.calculateRaceResult());

        // Process the results of the race
        processResults(drivers);

        // Add drivers to the table
        resultsTable.setItems(drivers);

        // Create position column
        TableColumn<Driver, String> positionColumn = new TableColumn<>("#");
        positionColumn.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Driver, String> driverObject
                ) {
                    return new SimpleStringProperty(
                        (resultsTable.getItems().indexOf(driverObject.getValue()) + 1) + ""
                    );
                }
            }
        );

        // Create name column
        TableColumn<Driver, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Driver, String> driverObject
                ) {
                    return new SimpleStringProperty(driverObject.getValue().getName());
                }
            }
        );

        // Create team column
        TableColumn<Driver, String> teamColumn = new TableColumn<>("Team");
        teamColumn.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Driver, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(
                        TableColumn.CellDataFeatures<Driver, String> driverObject
                ) {
                    return new SimpleStringProperty(driverObject.getValue()
                            .getTeam(season).getName());
                }
            }
        );

        // Create score column
        TableColumn<Driver, String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(
            new Callback<TableColumn.CellDataFeatures<Driver, String>,
                    ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Driver, String> driverObject
                ) {
                    return new SimpleStringProperty("" + driverObject.getValue().getScore());
                }
            }
        );

        // Add columns to the table
        resultsTable.getColumns().addAll(positionColumn, nameColumn, teamColumn, scoreColumn);
    }

    /**
     * Process the race results.
     *
     * @param drivers the ordered driver list of the race
     */
    public void processResults(List<Driver> drivers) {
        int[] points = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};

        for (int i = 0; i < 10; i++) {
            Driver driver = drivers.get(i);
            driver.setPoints(driver.getPoints() + points[i]);

            Team team = driver.getTeam(GameEngine.getInstance().getSeason());
            team.setPointsThisSeason(team.getPointsThisSeason() + points[i]);
        }
    }
}
