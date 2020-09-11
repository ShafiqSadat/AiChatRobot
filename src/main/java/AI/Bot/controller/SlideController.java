package AI.Bot.controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SlideController implements Initializable {

    @FXML
    private JFXButton prewSlide;

    @FXML
    private JFXButton nextSlide;
    @FXML
    private Label slideNumber;
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane pane3;
    @FXML
    private JFXButton okClose;
    @FXML
    private JFXButton okLetsGo;
    @FXML
    public static AnchorPane sliderPane;
    @FXML
    public Label lblwelcome;

    @FXML
    private Label an2;

    @FXML
    private Label an1;

    @FXML
    private Label an4;

    @FXML
    private Label an3;

    @FXML
    private Label an5;

    @FXML
    private Label an6;

    @FXML
    private Label an7;
    @FXML
    private Label slide2welcome;
    @FXML
    private Label slide2intro;
    @FXML
    private Label slide2clickmore;
    @FXML
    private Pane paneImageSlide1;
    @FXML
    private Label slide3q1;

    @FXML
    private Label slide3howit;

    @FXML
    private Label slide3q3;

    @FXML
    private Label slide3q2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblwelcome.setText("Hi "+System.getProperty("user.name"));
        animateSlide2Texts();
        Animate.translateAnimation(0.5,pane2,1040);
        Animate.translateAnimation(0.5,pane3,1040);
    }

    private void animateSlide2Texts() {
        Thread thread = new Thread(()->{
            Label[] lbls = {slide2welcome,slide2intro,lblwelcome};
            try{
                for (Label lbl : lbls) {
                    animateTexts2(lbl);
                    Thread.sleep(300);
                }
                paneImageSlide1.setVisible(true);
                new Swing(paneImageSlide1).play();
                Thread.sleep(300);
                slide2clickmore.setVisible(true);
                new Swing(slide2clickmore).play();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.start();
    }

    int showSlide = 0;
    @FXML
    void nextSlide(ActionEvent event) {
        if (showSlide==0){
            Animate.translateAnimation(0.5,pane2,-1040);
            slideNumber.setText("2/3");
            showSlide++;
            ShowTextsAnc2();
        }else if(showSlide==1){
            Animate.translateAnimation(0.5,pane3,-1040);
            slideNumber.setText("3/3");
            showSlide++;
            nextSlide.setVisible(false);
            okClose.setVisible(true);
            Animate.scaleAnimation(okClose,1000,0.5,0.5);
            Animate.scaleAnimation(okLetsGo,1000,0.4,0.4);
            ShowTextsAnc3();
        }
    }

    @FXML
    void prewSlide(ActionEvent event) {
        nextSlide.setVisible(true);
        okClose.setVisible(false);
        if (showSlide==0){

        }else if(showSlide==1){
            Animate.translateAnimation(0.5,pane2,1040);
            slideNumber.setText("1/3");
            showSlide--;
            animateSlide2Texts();
        }
        else if(showSlide==2){
            Animate.translateAnimation(0.5,pane3,1040);
            slideNumber.setText("2/3");
            showSlide--;
            ShowTextsAnc2();
        }
    }

    private void ShowTextsAnc2() {
        Thread thread = new Thread(()->{
                Label[] lbls = {an1,an2,an2,an3,an4,an5,an6,an7};
                try{
                    for (Label lbl : lbls) {
                        animateTexts(lbl);
                        Thread.sleep(300);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
        });
        thread.start();
    }
    private void ShowTextsAnc3() {
        Thread thread = new Thread(()->{
                Label[] lbls = {slide3howit,slide3q1,slide3q2,slide3q3};
                try{
                    for (Label lbl : lbls) {
                        animateTexts3(lbl);
                        Thread.sleep(300);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
        });
        thread.start();
    }


    @FXML
    void okClose(ActionEvent event) {
        try {
            AnchorPane anc1 = FXMLLoader.load(getClass().getClassLoader().getResource("MainScene.fxml"));
            pane3.getChildren().setAll(anc1);
            nextSlide.setVisible(false);
            prewSlide.setVisible(false);
            slideNumber.setVisible(false);
            okClose.setVisible(false);
            new LightSpeedIn(anc1).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void animateTexts(Label lbl){
        lbl.setVisible(true);
        new BounceInDown(lbl).play();
    }
    public void animateTexts3(Label lbl){
        lbl.setVisible(true);
        new RubberBand(lbl).play();
    }
    public void animateTexts2(Label lbl){
        lbl.setVisible(true);
        new Swing(lbl).play();
    }
}
