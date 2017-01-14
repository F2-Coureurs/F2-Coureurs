package frontend.controllers;

import backend.GameEngine;
import backend.Season;
import backend.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.stage.Stage;

import java.io.IOException;

public class EditTeamController {
    @FXML private Label budget;
    @FXML private Label firstDriver;
    @FXML private Label secondDriver;
    @FXML private Label strategist;
    @FXML private Label aerodynamicist;
    @FXML private Label mechanic;
    @FXML private Label engine;

    private Season season;
    private String saveName;

    /**
     * Sets the budget for the team to the right value.
     */
    @FXML
    public void initialize() {
        season = GameEngine.getInstance().getSeason();
        Team playerTeam = season.getPlayerControlledTeam();
        saveName = GameEngine.getInstance().getSaveName();

        budget.setText(playerTeam.getBudgetString());
        engine.setText(playerTeam.getEngine().getName());
        firstDriver.setText(playerTeam.getFirstDriver().getName());
        secondDriver.setText(playerTeam.getSecondDriver().getName());
        strategist.setText(playerTeam.getStrategist().getName());
        aerodynamicist.setText(playerTeam.getAerodynamicist().getName());
        mechanic.setText(playerTeam.getMechanic().getName());
    }

    /**
     * Cancels all the changes by loading the json file from save.
     * Returns to the home screen after clicking.
     *
     * @param event not using it
     * @throws IOException throws if the home screen fxml doesn't exist
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        GameEngine.getInstance().setSeason(Season.load(saveName));
        season = GameEngine.getInstance().getSeason();

        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        Stage stage = (Stage) budget.getScene().getWindow();

        stage.getScene().setRoot(root);
    }

    /**
     * Saves the changes to the json file and return to the home screen.
     *
     * @param event not using it
     * @throws IOException throws if the home screen fxml doesn't exist
     */
    @FXML
    public void save(ActionEvent event) throws IOException {
        season.save(saveName);

        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        Stage stage = (Stage) budget.getScene().getWindow();


        stage.getScene().setRoot(root);
    }

    /**
     * Button handler goes to the select team member.
     *
     * @param event don't use it
     * @throws IOException throws if the fxml file can not be found
     */
    @FXML
    public void editDriver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/select-team-member.fxml"));
        Parent root = (Parent) loader.load();
        SelectTeamMemberController controller = loader.getController();
        controller.load("driver1");

        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Button handler goes to the select team member.
     *
     * @param event don't use it
     * @throws IOException throws if the fxml file can not be found
     */
    @FXML
    public void editAerodynamiscist(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/select-team-member.fxml"));
        Parent root = (Parent) loader.load();
        SelectTeamMemberController controller = loader.getController();
        controller.load("aerodynamicist");

        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Button handler goes to the select team member.
     *
     * @param event don't use it
     * @throws IOException throws if the fxml file can not be found
     */
    @FXML
    public void editMechanic(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/select-team-member.fxml"));
        Parent root = (Parent) loader.load();
        SelectTeamMemberController controller = loader.getController();
        controller.load("mechanic");

        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Button handler goes to the select team member.
     *
     * @param event don't use it
     * @throws IOException throws if the fxml file can not be found
     */
    @FXML
    public void editStrategist(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/select-team-member.fxml"));
        Parent root = (Parent) loader.load();
        SelectTeamMemberController controller = loader.getController();
        controller.load("strategist");

        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Button handler goes to the select team member.
     *
     * @param event don't use it
     * @throws IOException throws if the fxml file can not be found
     */
    @FXML
    public void editDriver2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/select-team-member.fxml"));
        Parent root = (Parent) loader.load();
        SelectTeamMemberController controller = loader.getController();
        controller.load("driver2");

        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * loads the edit engine screen.
     *
     * @param event not using it
     * @throws IOException not throwing it
     */
    @FXML
    public void editEngine(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/select-engine.fxml"));
        Stage stage = (Stage) budget.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
