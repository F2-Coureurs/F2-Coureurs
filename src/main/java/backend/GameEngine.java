package backend;

import java.io.FileNotFoundException;

public enum GameEngine {
    INSTANCE;
    private Season season;
    private String saveName;

    private void build(GameEngineBuilder builder) {
        this.saveName = builder.saveName;
        this.season = builder.season;
    }

    /**
     * Gets the GameEngine singleton.
     * Run GameEngine.build() first somewhere in the project.
     *
     * @return the GameEngine singleton
     */
    public static GameEngine getInstance() {
        return INSTANCE;
    }

    public Season getSeason() {
        return season;
    }

    public String getSaveName() {
        return saveName;
    }

    /**
     * Sets the season.
     * @param season the season you want to set
     */
    public void setSeason(Season season) {
        this.season = season;
    }

    /**
     * <p>Builds a new GameEngine with a season and saveName.
     * The saveName argument decides what happens:
     * IF the saveName is the name of a file that exists in tha saves folder,
     * the corresponding save gets loaded.
     * IF the saveName does not exist in the saves folder, a new game gets created.
     * </p>
     *
     * <p>GameEngineBuilder needs to be called before GameEngine can be used.
     * Also, if this method is called more then once, the used Season and saveName
     * will be overwritten.
     * Therefore, GameEngine.build needs to be called on the newGame or LoadGame controller.
     * As long as they do not exist, build gets called on creation of the game, in Manager.
     * </p>
     *
     * <p>Usage:
     * new GameEngine.GameEngineBuilder(save1.json).build();
     * GameEngine.getSeason();</p>
     *
     */
    public static class GameEngineBuilder {
        private final String saveName;
        private Season season;

        /**
         * Constructor. saveName is the filename in saves.
         * A new save will be made if the name does not exist.
         * @param saveName name of the save
         */
        public GameEngineBuilder(String saveName) {
            this.saveName = saveName;
            try {
                season = Season.load(saveName);
            } catch (FileNotFoundException e) {
                season = Season.loadNewGameFromSeasonStart();
                season.save(saveName);
            }
        }

        /**
         * Build the GameEngine. Call this after instantiating the class.
         */
        public void build() {
            GameEngine.INSTANCE.build(this);
        }

    }


}
