package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public void save(String saveName) throws IOException {
        File dir = new File("saves");
        dir.mkdir();
        writeToJsonFile("saves/" + saveName);
    }

    private boolean equalsRounds(Season that) {
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

    /**
     * Temporary main. Use this to test the Json write and read functionality.
     *
     * @param args Nothing
     * @throws IOException If the file or directory does not exist
     */
    public static void main(String[] args) throws IOException {
        Season season = Season.loadNewGameFromSeasonStart();
        season.save("save1.json");
    }
}
