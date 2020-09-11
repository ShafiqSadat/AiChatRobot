package AI.Bot.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

@SuppressWarnings("unused")
public class Message extends AnchorPane{

    private StringProperty message = new SimpleStringProperty();
    private ObjectProperty<Image> avatar = new SimpleObjectProperty<>();

    private AnchorPane content;
    private Circle avatarImage;
    private Label messageLabel;

    public Message(ScrollPane pane){
        this.setStyle("-fx-background-color: transparent;");

        pane.widthProperty().addListener(((observable, oldValue, newValue) -> this.setPrefWidth(newValue.doubleValue() - 15)));
        this.setPrefWidth(pane.getWidth() - 15);

        AnchorPane content = new AnchorPane();
//        content.setStyle("-fx-background-color: transparent;");

        Circle avatar = new Circle(18);
        avatar.setFill(Color.DODGERBLUE);
        avatar.setLayoutX(4);
        avatar.setLayoutY(20);
        setLeftAnchor(avatar, 4d);
        Label message = new Label();
        message.setMinHeight(36);
        message.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        message.setFont(new Font("System", 16));
        message.setTextFill(Color.BLACK);
        message.setLayoutY(2);
        message.setLayoutX(45);
        message.setPadding(new Insets(6));
        message.setWrapText(true);
        message.setStyle("-fx-background-color: #78e08f; -fx-background-radius: 20 20 20 20;");
        setLeftAnchor(message, 45d);
        setRightAnchor(message, 0d);
        content.getChildren().addAll(avatar, message);
        this.getChildren().add(content);

        this.content = content;
        this.avatarImage = avatar;
        this.messageLabel = message;
    }

//    public final StringProperty messageProperty(){
//        return this.message;
//    }

//    public String getMessage(){
//        return message.get();
//    }

    public void setMessage(String message){
        this.message.set(message);

        this.messageLabel.setText(message);
    }

    public void setAvatar(Image avatar){
        this.avatar.set(avatar);

        this.avatarImage.setFill(new ImagePattern(avatar));
    }

    public void changeOrientation(){
        if(this.getScaleX() == 1){
            this.setScaleX(-1);

            this.messageLabel.setScaleX(-1);
            this.messageLabel.setAlignment(Pos.BASELINE_RIGHT);
            this.messageLabel.setPadding(new Insets(6));
            this.messageLabel.setStyle("-fx-background-color: #82ccdd; -fx-background-radius: 20;");
            setLeftAnchor(this.content, 0d);
        }else{
            this.setScaleX(1);
            this.messageLabel.setScaleX(1);
        }
    }
}