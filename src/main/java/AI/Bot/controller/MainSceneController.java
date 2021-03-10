package AI.Bot.controller;

import AI.Bot.model.DBConnection;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.toilelibre.libe.curl.Curl.$;

public class MainSceneController implements Initializable {

    @FXML
    private static AnchorPane ancStart;
    @FXML
    public JFXTextField messageTextField;

    @FXML
    public JFXButton sendButton;

    @FXML
    public VBox messages;

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public ProgressIndicator loadingProgressIndicator;
    @FXML
    public AnchorPane anc2;
    @FXML
    public ImageView myImage;
    @FXML
    public ImageView sendImage;
    @FXML
    public Label lblchat;
    @FXML
    public Label lblStatus;
    @FXML
    public Label lblTyping;
    @FXML
    public JFXButton btnExit;
    @FXML
    private JFXButton btnhelp;
    public boolean isConnected = false;
    @FXML
    private StackPane stackPane;
    String resp = "";

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread thread = new Thread(() -> {
            try {
                lblTyping.setVisible(false);
                messageTextField.setFont(new Font("Arial Bold", 18));
                scrollPane.setStyle("-fx-background-color:transparent; -fx-background-radius: 10 0 0 10; -fx-text-fill: white");
                setLblChat();
                sendButton.setDisable(false);
                sendImage.setImage(new Image(getClass().getClassLoader().getResource("image/send.png").toString()));
                messageTextField.setDisable(true);
                sendImage.setVisible(false);
                sendButton.setVisible(false);
                setMyImage(myImage, "image/chat.png");
                loadingProgressIndicator.setLayoutX(200);
                loadingProgressIndicator.setLayoutY(200);
            } catch (Exception exception) {
                exception.printStackTrace();
                Platform.runLater(() ->
                        this.showErrorAlert(
                                "An error occurred while loading the bot",
                                exception.getClass().getName() + ": " + exception.getMessage()
                        ));
                Platform.exit();
            }
        });
        thread.start();
        Platform.runLater(this::startThread);
        this.scrollPane.vvalueProperty().bind(this.messages.heightProperty());
    }

    private void startThread() {
        loadingProgressIndicator.setVisible(true);
        setLblStatus();
    }

    private void setLblStatus() {
        Thread thread = new Thread(() -> {
            lblStatus.setFont(new Font("System", 16));
            try {
                URL url = new URL("https://www.google.com");
                URLConnection connection = url.openConnection();
                connection.connect();
                Platform.runLater(() -> {
                    this.isConnected = true;
                    lblStatus.setText("  Online");
                    loadingProgressIndicator.setVisible(false);
                    messageTextField.setDisable(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    lblStatus.setStyle("-fx-text-fill:  #d9534f");
                    lblStatus.setText("   DB");
                    this.isConnected = false;
                    loadingProgressIndicator.setVisible(false);
                    messageTextField.setDisable(false);
                });
            }
        });
        thread.start();
    }

    public String response(String text) {
        String dbResponse = DBConnection.resp(text);
        if (dbResponse.equals("")){
            if(isConnected){
            String res = $("curl http://shafiq.codes/simsimi/?text=" + text);
            JSONObject json = new JSONObject(res);
            String code = json.getString("id");
            this.resp = DecryptResponse.getResponse(code);
            if (resp.equals("")) {
                this.resp = "یاری حاجی برار ایر یاد نداروم\nولی میتونی بمه یاد بدی";
            }
            }else
                this.resp = "یاری حاجی برار ایر یاد نداروم\nولی میتونی بمه یاد بدی";
        } else
             this.resp = DBConnection.resp(text);
        return resp;
    }

    private void setLblChat() {
        lblchat.setText("AI Chat Bot");
        lblchat.setFont(new Font("Arial Bold", 16));
        lblchat.setTextFill(Color.WHITE);
    }

    @FXML
    public void onSendButtonPressed() {
        final String text = this.messageTextField.getText();
        scrollPane.requestFocus();
        if (text.startsWith("learnIt")) {
            showQuestionAlert();
            JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
            Text txtH = new Text("Yey! i learned your word!");
            jfxDialogLayout.setHeading(txtH);
            Image image = new Image(getClass().getClassLoader().getResource("image/icons8_checkmark_200px.png").toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            jfxDialogLayout.setBody(imageView);
            JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
            JFXButton jfxButton = new JFXButton("Close");
            jfxButton.setStyle("-fx-background-color: #0097db; -fx-text-fill: white");
            jfxButton.setOnAction(event1 -> jfxDialog.close());
            jfxDialogLayout.setActions(jfxButton);
            jfxDialog.show();
            messageTextField.setText("");
            return;
        }
        if (!text.trim().equals("")) {
            Platform.runLater(() -> {
                this.sendButton.setDisable(true);
                this.messageTextField.setText("");
                this.messageTextField.setDisable(true);
                lblTyping.setVisible(true);
            });
            this.addMessage(
                    new Image(getClass().getClassLoader().getResource("image/boy.png").toString()),
                    text + "\n" + currentTime(),
                    false
            );
            Thread thread2 = new Thread(() -> {
                Platform.runLater(() -> {
                    String answer = response(text) + "\n" + currentTime();
                    this.addMessage(
                            new Image(getClass().getClassLoader().getResource("image/support.png").toString()),
                            answer,
                            true
                    );
                    messageTextField.requestFocus();
                    this.sendButton.setDisable(false);
                    this.messageTextField.setDisable(false);
                    lblTyping.setVisible(false);
                });
            });
            thread2.start();
            Platform.runLater(() -> {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                sendNotification();
                            }
                        },
                        0, 1000 * 60 * 5
                );
            });
        }
    }

    private void sendNotification() {
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            Image whatsAppImg = new Image(getClass().getClassLoader().getResource("image/image_hello.png").toString());
            tray.setTitle("Ai Chat Bot");
            tray.setMessage("بگیریم از گوشتا");
            tray.setRectangleFill(Paint.valueOf("#2A9A84"));
            tray.setAnimation(Animations.POPUP);
            tray.setImage(whatsAppImg);
            tray.showAndWait();
        });
    }

    @FXML
    public void onMessageTextFieldKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.onSendButtonPressed();
        }
    }

    private void addMessage(Image image, String text, boolean second) {
        Message message = new Message(this.scrollPane);
        message.setMessage(text);
        message.setAvatar(image);

        if (second) {
//            message.setStyle("-fx-background-color: #51362A; -fx-background-radius: 20 20 20 20;");
            message.changeOrientation();
        }
        this.messages.getChildren().add(message);
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setMyImage(ImageView imageView, String path) {
//        imageView.setX(10);
//        imageView.setY(-300);

        //setting the fit height and width of the image view
//        imageView.setFitHeight(120);
//        imageView.setFitWidth(120);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getClassLoader().getResource(path).toString()));
        imageView.setVisible(true);
    }

    public String currentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(new Date()).toString();
    }

    public void onMouseOver(MouseEvent mouseEvent) {
    }

    @FXML
    void showHelp(ActionEvent event) {
        simpleAlert();
    }

    private void simpleAlert() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        Text txtH = new Text("How to teach bot");
        txtH.setTextAlignment(TextAlignment.CENTER);
        jfxDialogLayout.setHeading(txtH);
        Text txt = new Text("To teach your bot send a message with learnIt\nWhen you send this message bot send you a dialog box\nImportant : whene you teach your robot its only available on your local machine" +
                "\n\nversion : 0.1 Beta" +
                "\n\nProgrammer : https://t.me/Shafiq");
        txt.setTextAlignment(TextAlignment.CENTER);
        jfxDialogLayout.setBody(txt);
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton jfxButton = new JFXButton("Close");
        jfxButton.setStyle("-fx-background-color: #0097db; -fx-text-fill: white");
        jfxButton.setOnAction(event1 -> jfxDialog.close());
        jfxDialogLayout.setActions(jfxButton);
        jfxDialog.show();
    }

    private void showQuestionAlert() {
        String question = "";
        JFXTextField usernameTextField = new JFXTextField();
        usernameTextField.setLabelFloat(true);
        usernameTextField.setPromptText("Enter Question");
        JFXAlert<String> alert = new JFXAlert<>(btnhelp.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        // Create the content of the JFXAlert with JFXDialogLayout
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Teach Your Bot (Question)"));
        layout.setBody(usernameTextField);
        JFXButton addButton = new JFXButton("Add");
        addButton.setDefaultButton(true);
        addButton.setOnAction(addEvent -> {
            alert.setResult(usernameTextField.getText());
            alert.hideWithAnimation();
        });
        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());
        layout.setActions(addButton, cancelButton);
        alert.setContent(layout);
        Optional<String> result = alert.showAndWait();
        if (result.isPresent()) {
            question = result.get();
            showAnswerAlert(question);
        }
    }

    private void showAnswerAlert(String question) {
        String answer = "";
        JFXTextField usernameTextField2 = new JFXTextField();
        usernameTextField2.setLabelFloat(true);
        usernameTextField2.setPromptText("Enter Answer");
        JFXAlert<String> alert = new JFXAlert<>(btnhelp.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        // Create the content of the JFXAlert with JFXDialogLayout
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Teach Your Bot (Answer)"));
        layout.setBody(usernameTextField2);

        JFXButton addButton = new JFXButton("Add");
        addButton.setDefaultButton(true);
        addButton.setOnAction(addEvent -> {
            alert.setResult(usernameTextField2.getText());
            alert.hideWithAnimation();
        });
        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());
        layout.setActions(addButton, cancelButton);
        alert.setContent(layout);
        Optional<String> result = alert.showAndWait();
        if (result.isPresent()) {
            answer = result.get();
            DBConnection.insertWord(question.toLowerCase(), answer.toLowerCase());
        }
    }

    public void btnExitClicked(ActionEvent event) {
        System.exit(0);
    }
}
