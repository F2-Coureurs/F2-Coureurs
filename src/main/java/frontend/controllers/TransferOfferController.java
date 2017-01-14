package frontend.controllers;

import backend.Driver;
import backend.GameEngine;
import backend.Season;
import backend.Staff;
import backend.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferOfferController {
    @FXML private Label teamName;
    @FXML private Label personToTransfer;
    @FXML private Label priceLabel;
    private Team team;
    private Staff transferCandidate;
    private Season season;

    /**
     * Initialization. Must be run before rendering the screen.
     *
     * @param team the team making the transfer call
     * @param transferCandidate the candidate for the transfer
     */
    public void load(Team team, Staff transferCandidate) {
        season = GameEngine.getInstance().getSeason();
        this.team = team;
        this.transferCandidate = transferCandidate;

        teamName.setText(team.getName() + " would like to buy");
        personToTransfer.setText(
                transferCandidate.getJobTitle() + " " + transferCandidate.getName());
        priceLabel.setText("for " + transferCandidate.getBuyoutClauseString(season));
    }

    /**
     * Accepts the transfer. Loads the corresponding select team member screen.
     *
     * @param event not using it
     * @throws IOException throws if the fxml file does not exist
     */
    @FXML
    public void accept(ActionEvent event) throws IOException {
        season.transfer(transferCandidate, team);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/select-team-member.fxml"));
        Parent root = (Parent) loader.load();
        SelectTeamMemberController controller = loader.getController();
        if (transferCandidate.getJobTitle().equals("driver")) {
            if (((Driver) transferCandidate).isSecondDriver(season)) {
                controller.load("driver2", false);
            } else {
                controller.load("driver1", false);
            }
        } else {
            controller.load(transferCandidate.getJobTitle(), false);
        }

        Stage stage = (Stage) teamName.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Refuses the transfer. Loads the home screen.
     *
     * @param event not using it
     * @throws IOException throws when the fxml file does not exist
     */
    @FXML
    public void refuse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        Stage stage = (Stage) teamName.getScene().getWindow();

        stage.getScene().setRoot(root);
    }
}
