package AI.Bot.controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animate {
        public static void scaleAnimation(Node node,double duration,double byY,double byX){
            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setDuration(Duration.millis(duration));
            scaleTransition.setNode(node);
            scaleTransition.setByY(byY);
            scaleTransition.setByX(byX);
            scaleTransition.setCycleCount(5000);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();
        }
    public static void translateAnimation(double duration, Node node, double byX){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setByX(byX);
        translateTransition.setDuration(Duration.seconds(duration));
        translateTransition.setNode(node);
        translateTransition.play();
    }

}