package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Season {
    private int currentRound;
    private List<Race> rounds;
    private List<Team> teams;
    private List<Aerodynamicist> contractAerodynamicists;
    private List<Driver> contractDrivers;
    private List<Mechanic> contractMechanics;
    private List<Strategist> contractStrategists;

    /**
     * Create a new season.
     */
    public Season() {
        this.currentRound = 0;
        this.rounds = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.contractAerodynamicists = new ArrayList<>();
        this.contractDrivers = new ArrayList<>();
        this.contractMechanics = new ArrayList<>();
        this.contractStrategists = new ArrayList<>();
    }

    /**
     * Gets the current round.
     *
     * @return current round
     */
    public int getRoundInt() {
        return currentRound;
    }

    /**
     * Gets the current round.
     *
     * @return current race
     */
    public Race getCurrentRound() {
        if (this.currentRound >= this.rounds.size()) {
            throw new IllegalArgumentException("Current round does not exist in this.rounds");
        }
        return this.rounds.get(this.currentRound);
    }

    /**
     * Sets the current round.
     *
     * @param currentRound the round to be set to current Round
     */
    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    /**
     * Gets the rounds as a list.
     *
     * @return rounds
     */
    public List<Race> getRounds() {
        return rounds;
    }

    /**
     * Sets the rounds.
     *
     * @param rounds new rounds
     */
    public void setRounds(List<Race> rounds) {
        this.rounds = rounds;
    }

    /**
     * Adds a team to the season.
     *
     * @param team the team to add
     */
    public void addTeam(Team team) {
        this.teams.add(team);
    }

    /**
     * Get all the teams of the season.
     *
     * @return all the teams
     */
    public List<Team> getTeams() {
        return this.teams;
    }


    /**
     * Gets all the engines in the season.
     *
     * @return the engines
     */
    public List<Engine> getNonPlayerEngines() {
        return teams.stream()
                .map(Team::getEngine)
                .filter((Engine en) -> !getPlayerControlledTeam().getEngine().equals(en))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Gets the desired team.
     *
     * @param rank the rank of the team
     * @return the team
     *
     */
    public Team getTeamByRank(int rank) {
        List<Team> teamCopy = new ArrayList<>(teams);
        Collections.sort(teamCopy);

        return teamCopy.get(rank);
    }

    /**
     * Gets the team that the player controls.
     *
     * @return one of the teams
     */
    public Team getPlayerControlledTeam() {
        return this.teams.get(0);
    }

    /**
     * Adds a race to the rounds.
     *
     * @param race the race to add
     */
    public void addRace(Race race) {
        this.rounds.add(race);
    }

    /**
     * Gets the list of all contract Aerodynamicists.
     * @return ArrayList
     */
    public List<Aerodynamicist> getContractAerodynamicists() {
        return this.contractAerodynamicists;
    }

    /**
     * Gets the list of all contract Drivers.
     * @return ArrayList
     */
    public List<Driver> getContractDrivers() {
        return this.contractDrivers;
    }

    /**
     * Gets the desired driver.
     *
     * @param rank the rank of the driver
     * @return the driver
     */
    public Driver getDriver(int rank) {
        List<Driver> driver = new ArrayList<>();
        for (Team items : teams) {
            driver.add(items.getFirstDriver());
            driver.add(items.getSecondDriver());
        }
        Collections.sort(driver);

        return driver.get(rank);
    }

    /**
     * Gets the list of all contract Drivers.
     * @return ArrayList
     */
    public List<Mechanic> getContractMechanics() {
        return this.contractMechanics;
    }

    /**
     * Gets the list of all contract Strategists.
     * @return ArrayList
     */
    public List<Strategist> getContractStrategists() {
        return this.contractStrategists;
    }

    /**
     * Sets a staff member as a staff member that is open to a contract.
     *
     * @param staffMember the staff member to add to a contract list.
     */
    public void addContractStaffMember(Staff staffMember) {
        if (staffMember instanceof Aerodynamicist) {
            contractAerodynamicists.add((Aerodynamicist) staffMember);
        } else if (staffMember instanceof Driver) {
            contractDrivers.add((Driver) staffMember);
        } else if (staffMember instanceof Mechanic) {
            contractMechanics.add((Mechanic) staffMember);
        } else {
            contractStrategists.add((Strategist) staffMember);
        }
    }

    /**
     * Gets all the staff that is not in the players team.
     *
     * @return a list of staff
     */
    public List<Staff> getAllNonPlayerControlledStaff() {
        List<Staff> returnList = new ArrayList<>();
        returnList.addAll(contractDrivers);
        returnList.addAll(contractAerodynamicists);
        returnList.addAll(contractMechanics);
        returnList.addAll(contractStrategists);
        for (Team team : teams) {
            if (!team.equals(getPlayerControlledTeam())) {
                returnList.add(team.getFirstDriver());
                returnList.add(team.getSecondDriver());
                returnList.add(team.getAerodynamicist());
                returnList.add(team.getMechanic());
                returnList.add(team.getStrategist());
            }
        }
        return returnList;
    }

    /**
     * Transfers a staff member from one team to another.
     * staffMember first gets removed from the team it is in.
     * If the staffMember is not in a team, it gets removed from the contract lists.
     * Now the staffMember gets add to the new team.
     * The old staffMember from that team gets added to the contract list.
     * Money gets deducted from the new team and added to the old team,
     * equal to the buyout clause of the staff member.
     *
     * @param staffMember The new staff member
     * @param team The team to transfer the staff member to
     * @param replaceSecondDriver true if the second driver needs to be replaced
     * @param replaceWithNull if replaceWithNull is true, the staffMember gets replaced with a
     *                        temporary staffMember, that is recognizable as a non-existing
     *                        staffMember. This flag should be set if the oldTeam is the team
     *                        of the player, so the player can choose a new staff member.
     *                        In the frontend, this means that the transfer method should be
     *                        followed by the select team member screen. If this is not done, the
     *                        application will fail.
     *                        If replaceWithNull is false, the staffMember gets replaced by a
     *                        random contract worker with the same function.
     */
    public void transfer(Staff staffMember, Team team, boolean replaceSecondDriver, boolean replaceWithNull) {
        Team oldTeam = staffMember.getTeam(this);
        int buyoutClause = staffMember.getBuyoutClause(this);
        // subtract the buyout clause from the budget
        team.setBudget(team.getBudget() - buyoutClause);
        // add the buyout clause to the budget of the old team
        if (oldTeam != null) {
            oldTeam.setBudget(oldTeam.getBudget() + buyoutClause);
        }

        // Remove the new staff member from its old team
        if (oldTeam != null) {
            replaceOldTeamStaffMember(staffMember, oldTeam, replaceWithNull);
        } else {
            // The new staff member was not in a team
            // Make sure the new staff member is not still in a contract list
            contractAerodynamicists.remove(staffMember);
            contractDrivers.remove(staffMember);
            contractMechanics.remove(staffMember);
            contractStrategists.remove(staffMember);
        }
        // add the new staff member to the team
        Staff oldStaffMember;
        if (replaceSecondDriver) {
            oldStaffMember = team.swapSecondDriver((Driver) staffMember);
        } else {
            oldStaffMember = team.swapStaffMember(staffMember);
        }
        // add the old staff member to the contract list
        // if the old staff member is a "null" staff member, delete it from the game
        if (!oldStaffMember.getName().equals("")) {
            addContractStaffMember(oldStaffMember);
        }
    }


    /**
     * Transfers a staff member from one team to another.
     * staffMember first gets removed from the team it is in.
     * If the staffMember is not in a team, it gets removed from the contract lists.
     * Now the staffMember gets add to the new team.
     * The old staffMember from that team gets added to the contract list.
     * Money gets deducted from the new team and added to the old team,
     * equal to the buyout clause of the staff member.
     *
     * @param staffMember The new staff member
     * @param team The team to transfer the staff member to
     * @param replaceWithNull if replaceWithNull is true, the staffMember gets replaced with a
     *                        temporary staffMember, that is recognizable as a non-existing
     *                        staffMember. This flag should be set if the oldTeam is the team
     *                        of the player, so the player can choose a new staff member.
     *                        In the frontend, this means that the transfer method should be
     *                        followed by the select team member screen. If this is not done, the
     *                        application will fail.
     *                        If replaceWithNull is false, the staffMember gets replaced by a
     *                        random contract worker with the same function.
     */
    public void transfer(Staff staffMember, Team team, boolean replaceWithNull) {
        transfer(staffMember, team, false, replaceWithNull);
    }

    public void transfer(Staff staffMember, Team team) {
        transfer(staffMember, team, false, false);
    }

    /**
     * This method performs a critical part of the transfer process.
     *
     * @param staffMember this staffMember gets removed from oldTeam
     * @param oldTeam the Team the staffMember gets removed from
     * @param replaceWithNull if replaceWithNull is true, the staffMember gets replaced with a
     *                        temporary staffMember, that is recognizable as a non-existing
     *                        staffMember. This flag should be set if the oldTeam is the team
     *                        of the player, so the player can choose a new staff member.
     *                        In the frontend, this means that the transfer method should be
     *                        followed by the select team member screen. If this is not done, the
     *                        application will fail.
     *                        If replaceWithNull is false, the staffMember gets replaced by a
     *                        random contract worker with the same function.
     */
    private void replaceOldTeamStaffMember(Staff staffMember, Team oldTeam, boolean replaceWithNull) {
        // If the new staff member was in a team,
        // add a new contract worker to the team
        if (staffMember instanceof Aerodynamicist) {
            if (!replaceWithNull) {
                if (contractAerodynamicists.size() != 0) {
                    oldTeam.swapStaffMember(contractAerodynamicists.remove(0));
                } else {
                    oldTeam.swapStaffMember(new Aerodynamicist());
                }
            } else {
                oldTeam.swapStaffMember(new Aerodynamicist());
            }
        } else if (staffMember instanceof Driver) {
            if (((Driver) staffMember).isSecondDriver(this)) {
                if (!replaceWithNull) {
                    if (contractDrivers.size() != 0) {
                        oldTeam.swapSecondDriver(contractDrivers.remove(0));
                    } else {
                        oldTeam.swapSecondDriver(new Driver());
                    }
                } else {
                    oldTeam.swapSecondDriver(new Driver());
                }
            } else {
                if (!replaceWithNull) {
                    if (contractDrivers.size() != 0) {
                        oldTeam.swapSecondDriver(contractDrivers.remove(0));
                    } else {
                        oldTeam.swapSecondDriver(new Driver());
                    }
                } else {
                    oldTeam.swapStaffMember(new Driver());
                }
            }
        } else if (staffMember instanceof Mechanic) {
            if (!replaceWithNull) {
                if (contractMechanics.size() != 0) {
                    oldTeam.swapStaffMember(contractMechanics.remove(0));
                } else {
                    oldTeam.swapStaffMember(new Mechanic());
                }
            } else {
                oldTeam.swapStaffMember(new Mechanic());
            }
        } else {
            if (!replaceWithNull) {
                if (contractStrategists.size() != 0) {
                    oldTeam.swapStaffMember(contractStrategists.remove(0));
                } else {
                    oldTeam.swapStaffMember(new Strategist());
                }
            } else {
                oldTeam.swapStaffMember(new Strategist());
            }
        }

    }

    /**
     * Converts the current Season class to a json object.
     *
     * @return string containing a json representation of the current season
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Converts the current Season class to a human-readable json String.
     * This method should for example be used for SeasonStart.json,
     * because that file needs to be edited by humans frequently.
     * However, if we are getting ready for production, it would be better
     * to minimize SeasonStart.json.
     *
     * @return String containing a json representation of the Season
     */
    public String toJsonPretty() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    /**
     * Converts the jsonString to a Season class.
     *
     * @param jsonString a json String representing a Season class
     * @return a season class
     */
    public static Season fromJson(String jsonString) throws JsonSyntaxException {
        Gson gson = new Gson();

        return gson.fromJson(jsonString, Season.class);
    }

    /**
     * Reads a json file representing a season and stores it as a Season object.
     * Currently, it fails fast, instead of catching errors. But this can be changed
     * depending on the project requirements.
     *
     * @param inputFile a scanner of a json file containing a Season
     * @return a season
     */
    public static Season readFromJsonFile(InputStream inputFile) {
        Scanner sc = new Scanner(inputFile);
        sc.useDelimiter("//Z");
        String fileString = sc.next();
        return fromJson(fileString);
    }

    /**
     * Loads the data from seasonStart.json into the project.
     * Uses a static way of loading resources via a ClassLoader.
     * http://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder#15749281
     * the resource folder in main MUST be marked as a resource folder.
     *
     * @return a new Season with the information of seasonStart.json
     */
    public static Season loadNewGameFromSeasonStart() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream seasonStart = loader.getResourceAsStream("seasonStart.json");
        return readFromJsonFile(seasonStart);
    }

    /**
     * Loads a file from the save directory.
     *
     * @param saveName the name of the save
     * @return the season equal to the file
     * @throws IllegalArgumentException throws if the filename does not exist
     */
    public static Season load(String saveName) throws FileNotFoundException {
        FileInputStream loadFile = new FileInputStream("saves/" + saveName);
        return readFromJsonFile(loadFile);
    }

    /**
     * Writes json to a file.
     * Use the following article for reference:
     * http://www.oracle.com/technetwork/articles/java/trywithresources-401775.html
     *
     * @param filename file to write to
     * @throws IOException throws in rare cases (I'm not sure when)
     */
    private void writeToJsonFile(String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            // Change next line to toJsonPretty() if you want readable output.
            String jsonString = toJson();
            out.write(jsonString.getBytes());
        }
    }

    /**
     * Saves Season to saveName in json format.
     * The file gets saved to the saves directory, in the same folder as the executable.
     *
     * @param saveName the name the fie gets saved to
     * @throws IOException throws in special cases,
     *      like if the executable is in a admin only directory.
     */
    public void save(String saveName) {
        File dir = new File("saves");
        dir.mkdir();
        try {
            writeToJsonFile("saves/" + saveName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check whether two season classes have the same rounds.
     *
     * @param that other season
     * @return true if the rounds are the same
     */
    public boolean equalsRounds(Season that) {
        if (this.rounds.size() != that.rounds.size()) {
            return false;
        }
        for (Race round : this.rounds) {
            if (!(that.rounds.contains(round))) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsTeams(Season that) {
        if (this.teams.size() != that.teams.size()) {
            return false;
        }
        for (Team team : this.teams) {
            if (!(that.teams.contains(team))) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsAero(Season that) {
        if (this.contractAerodynamicists.size() != that.contractAerodynamicists.size()) {
            return false;
        }
        for (Aerodynamicist aerodynamicist : this.contractAerodynamicists) {
            if (!(that.contractAerodynamicists.contains(aerodynamicist))) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsDrivers(Season that) {
        if (this.contractDrivers.size() != that.contractDrivers.size()) {
            return false;
        }
        for (Driver driver : this.contractDrivers) {
            if (!(that.contractDrivers.contains(driver))) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsMechanics(Season that) {
        if (this.contractMechanics.size() != that.contractMechanics.size()) {
            return false;
        }
        for (Mechanic mechanic : this.contractMechanics) {
            if (!(that.contractMechanics.contains(mechanic))) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsStrategists(Season that) {
        if (this.contractStrategists.size() != that.contractStrategists.size()) {
            return false;
        }
        for (Strategist strategist : this.contractStrategists) {
            if (!(that.contractStrategists.contains(strategist))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two season classes are the same.
     *
     * @param other the class you want to test equality for.
     * @return true if other is the same as the current class, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Season) {
            Season that = (Season) other;
            return this.currentRound == that.currentRound
                    && this.equalsRounds(that)
                    && this.equalsTeams(that)
                    && this.equalsAero(that)
                    && this.equalsDrivers(that)
                    && this.equalsMechanics(that)
                    && this.equalsStrategists(that);
        }
        return false;
    }
}
