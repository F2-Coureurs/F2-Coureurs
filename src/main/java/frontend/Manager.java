package frontend;

import backend.GameEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Manager extends Application {
    public static final String TITLE = "F1 Manager";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/splash.fxml"));
        Scene scene = new Scene(root, 1200, 800);

        /*
        This line decides the save that the game will be using.
        When the pregame controllers get added, this line should be moved into the appropriate
        pregame controllers, EG: NewGame and LoadGame.
        @TODO Move to pregame controller
         */
        new GameEngine.GameEngineBuilder("save1.json").build();

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        Media media = new Media("http://www.klavier.nl/alex/backgroundmusic.mp3");
        MediaPlayer mp = new MediaPlayer(media);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();

        primaryStage.show();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                    Stage stage = (Stage) scene.getWindow();
                    stage.getScene().setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
