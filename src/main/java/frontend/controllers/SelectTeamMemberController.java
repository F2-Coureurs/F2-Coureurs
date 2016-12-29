package frontend.controllers;

import backend.Aerodynamicist;
import backend.Driver;
import backend.GameEngine;
import backend.Mechanic;
import backend.Season;
import backend.Staff;
import backend.Strategist;
import backend.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SelectTeamMemberController {
    @FXML private Label teamMateName;
    @FXML private Label teamMateSalary;
    @FXML private Label teamMateQuality;
    @FXML private Pane playerTable;
    @FXML private Label newPlayerName;
    @FXML private Label newSalary;
    @FXML private Label newQuality;
    @FXML private Label newTeamName;
    @FXML private Label newBuyoutClause;
    @FXML private ScrollBar scrollBar;
    @FXML private Pane tableBox;
    private Season season;
    private Staff newStaffMember;
    private List<Staff> newStaff;

    /**
     * Initialises the controller.
     */
    public void load(String type) {
        season = GameEngine.getInstance().getSeason();
        Team playerTeam = season.getPlayerControlledTeam();
        newStaff = season.getAllNonPlayerControlledStaff();

        if (type.equals("driver1")) {
            teamMateName.setText(playerTeam.getFirstDriver().getName());
            teamMateSalary.setText(playerTeam.getFirstDriver().getSalaryString());
            teamMateQuality.setText(playerTeam.getFirstDriver().getQualityString());
            newStaff = newStaff.stream()
                    .filter((Staff staff) -> staff instanceof Driver)
                    .collect(Collectors.toList());
        } else if (type.equals("driver2")) {
            teamMateName.setText(playerTeam.getSecondDriver().getName());
            teamMateSalary.setText(playerTeam.getSecondDriver().getSalaryString());
            teamMateQuality.setText(playerTeam.getSecondDriver().getQualityString());
            newStaff = newStaff.stream()
                    .filter((Staff staff) -> staff instanceof Driver)
                    .collect(Collectors.toList());
        } else if (type.equals("aerodynamicist")) {
            teamMateName.setText(playerTeam.getAerodynamicist().getName());
            teamMateSalary.setText(playerTeam.getAerodynamicist().getSalaryString());
            teamMateQuality.setText(playerTeam.getAerodynamicist().getQualityString());
            newStaff = newStaff.stream()
                    .filter((Staff staff) -> staff instanceof Aerodynamicist)
                    .collect(Collectors.toList());
        } else if (type.equals("mechanic")) {
            teamMateName.setText(playerTeam.getMechanic().getName());
            teamMateSalary.setText(playerTeam.getMechanic().getSalaryString());
            teamMateQuality.setText(playerTeam.getMechanic().getQualityString());
            newStaff = newStaff.stream()
                    .filter((Staff staff) -> staff instanceof Mechanic)
                    .collect(Collectors.toList());
        } else {
            teamMateName.setText(playerTeam.getStrategist().getName());
            teamMateSalary.setText(playerTeam.getStrategist().getSalaryString());
            teamMateQuality.setText(playerTeam.getStrategist().getQualityString());
            newStaff = newStaff.stream()
                    .filter((Staff staff) -> staff instanceof Strategist)
                    .collect(Collectors.toList());
        }

        // scrollBar seems to misbehave when put inside the fxml file
        // Probably because the listener gets added after initialization?
        scrollBar = new ScrollBar();
        scrollBar.setMin(0);
        scrollBar.setMax(newStaff.size() - 15);
        scrollBar.setUnitIncrement(1);
        scrollBar.setValue(0);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(530);
        scrollBar.setLayoutX(1020);
        scrollBar.setLayoutY(140);
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            setAllPotentialTeamMembers(newValue.intValue());
        });
        tableBox.getChildren().add(scrollBar);
        setAllPotentialTeamMembers(0);
    }

    private void setAllPotentialTeamMembers(int position) {
        playerTable.getChildren().clear();
        int increment = 0;
        int maxSize = position + 15;
        if (newStaff.size() < 15) {
            maxSize = newStaff.size();
        }
        for (Staff staff : newStaff.subList(position, maxSize)) {
            playerTable.getChildren().add(getTeamMemberPane(staff, increment));
            increment++;
        }
    }

    /**
     * Confirm the changes and go to the edit team screen.
     *
     * @param event don't use it
     * @throws IOException throws if the fxml file can not be found
     */
    @FXML
    public void confirm(ActionEvent event) throws IOException {
        season.transfer(newStaffMember, season.getPlayerControlledTeam());

        Parent root = FXMLLoader.load(getClass().getResource("/views/edit-team.fxml"));
        Stage stage = (Stage) teamMateName.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    /**
     * Throws away the changes and goes to the edit-team screen.
     *
     * @param event not using it
     * @throws IOException throws if the fxml file does not exist
     */
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/edit-team.fxml"));
        Stage stage = (Stage) teamMateName.getScene().getWindow();

        stage.getScene().setRoot(root);
    }

    private Pane getTeamMemberPane(Staff staffMember, int position) {
        Pane returnPane = new Pane();
        returnPane.setOnMouseClicked((event) -> {
            newPlayerName.setText(staffMember.getName());
            newQuality.setText(staffMember.getQualityString());
            newSalary.setText(staffMember.getSalaryString());
            newBuyoutClause.setText(staffMember.getBuyoutClauseString(season));
            newTeamName.setText(staffMember.getTeamName(season));
            newStaffMember = staffMember;
        });
        returnPane.setLayoutY(35 * position);

        Label nameLabel = new Label(staffMember.getName());
        nameLabel.getStyleClass().add("table-content");
        returnPane.getChildren().add(nameLabel);

        Label currentTeamLabel = new Label(staffMember.getTeamName(season));
        currentTeamLabel.getStyleClass().add("table-content");
        currentTeamLabel.setLayoutX(220);
        returnPane.getChildren().add(currentTeamLabel);

        Label salaryLabel = new Label(staffMember.getSalaryString());
        salaryLabel.getStyleClass().add("table-content");
        salaryLabel.setLayoutX(440);
        returnPane.getChildren().add(salaryLabel);

        Label buyoutClauseLabel = new Label(staffMember.getBuyoutClauseString(season));
        buyoutClauseLabel.getStyleClass().add("table-content");
        buyoutClauseLabel.setLayoutX(660);
        returnPane.getChildren().add(buyoutClauseLabel);

        Label abilityLabel = new Label(staffMember.getQualityString());
        abilityLabel.getStyleClass().add("tabel-content");
        abilityLabel.setLayoutX(880);
        returnPane.getChildren().add(abilityLabel);

        return returnPane;
    }
}
