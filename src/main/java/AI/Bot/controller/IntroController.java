package AI.Bot.controller;

import AI.Bot.model.DBConnection;
import animatefx.animation.SlideInLeft;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class IntroController implements Initializable {
    @FXML
    private ImageView robotImage;

    @FXML
    private Label lblload;
    @FXML
    private AnchorPane ancStart;
    @FXML
    private MediaView introVideo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media(Objects.requireNonNull(getClass().getClassLoader().getResource("image/Loading_Screen_HD_with_Sound.137.mp4")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        introVideo.setMediaPlayer(mediaPlayer);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                AnchorPane anc1;
                if(isFirstTime()){
                    anc1 = FXMLLoader.load(getClass().getClassLoader().getResource("SliderScene.fxml"));
                    DBConnection.createDB();
                }else{
                    anc1 = FXMLLoader.load(getClass().getClassLoader().getResource("MainScene.fxml"));
                }
                ancStart.getChildren().setAll(anc1);
                new SlideInLeft(anc1).play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        FadeTransition fade = new FadeTransition();
//        //setting the duration for the Fade transition
//        fade.setDuration(Duration.millis(2000));
//        //setting the initial and the target opacity value for the transition
//        fade.setFromValue(0);
//        fade.setToValue(10);
//        //setting cycle count for the Fade transition
//        fade.setCycleCount(1);
//        //the transition will set to be auto reversed by setting this to true
//        fade.setAutoReverse(true);
//        //setting Circle as the node onto which the transition will be applied
//        fade.setNode(ancStart);
//        //playing the transition
//        fade.play();
//        fade.setOnFinished(event -> {
//            if (isFirstTime){
////                Main.sceneName = "SliderScene";
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    public boolean isFirstTime() {
        File f = new File("./AiBot.ShafiqSadat");
        if (f.exists() && !f.isDirectory()) {
            return false;
        }else{
            try {
                f.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

