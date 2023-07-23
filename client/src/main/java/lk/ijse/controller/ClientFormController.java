package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import lk.ijse.emoji.EmojiBox;
import org.json.JSONString;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ClientFormController implements Initializable {
    @FXML
    private VBox vboxForChat;
    @FXML
    private Button emojiBtn;
    @FXML
    private Button imgBtn;
    @FXML
    private TextField msgTxt;
    @FXML
    private ScrollPane scrlPane;
    @FXML
    private Text usrNameTxt;
    @FXML
    private AnchorPane mainPane;
    private String message;
    {
        message = "";
    }
    private String usr;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ArrayList<String> wordList;
    Label label;
    HBox hBox;
    @FXML
    void imgBtnOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("image file","*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imageAbsalutePath = selectedFile.getAbsolutePath();
            dataOutputStream.writeUTF(usrNameTxt.getText()+"!!!!split!!!!"+imageAbsalutePath);
            dataOutputStream.flush();
//            Path imgPath= Paths.get(imageAbsalutePath);
//            byte[] imageData = Files.readAllBytes(imgPath);
//            String base64Img=Base64.getEncoder().encodeToString(imageData);

//            File file = new File(imagePath);
//            FileInputStream imageInFile = new FileInputStream(file);
//            byte imageData[] = new byte[(int) file.length()];
//            imageInFile.read(imageData);
//
//            String imageDataString = Base64.getEncoder().encodeToString(imageData);
//            imageInFile.close();
//
//            JSONObject obj = new JSONObject();
//            obj.put(imageFilter,imageDataString );
//            dataOutputStream.writeBytes(obj.toJSONString());
//            dataOutputStream.flush();
        }
    }
    @FXML
    void msgTxtOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(usrNameTxt.getText() + " " + msgTxt.getText());
        dataOutputStream.flush();
        msgTxt.setText("");
    }
    @FXML
    void clsBtnOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF("/usrLogOut//!-> "+usrNameTxt.getText());
        dataOutputStream.flush();
        socket.close();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
        msg();
        emoji();
    }

    @FXML
    void scrlOnMouseEntered(MouseEvent event) {
        scrlPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrlPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    @FXML
    void scrlOnMouseExited(MouseEvent event) {
        scrlPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrlPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void msg() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3991);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (socket.isConnected()) {
                    hBox = new HBox(12);
                    message = dataInputStream.readUTF();
                    if (message.endsWith(".jpg") || message.endsWith(".jpeg") || message.endsWith(".png") || message.endsWith(".gif")) {
                        Platform.runLater(() -> {
                            String path = splitImg(message);
                            File file = new File(path);
                            Image image = new Image(file.toURI().toString());
                            ImageView img = new ImageView(image);
                            img.setFitWidth(150);
                            img.setFitHeight(150);
                            if (usr.equals(usrNameTxt.getText())) {
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                hBox1.getChildren().add(img);
                                hBox1.setAlignment(Pos.CENTER_RIGHT);

                                vboxForChat.getChildren().add(hBox1);
                            } else {
                                HBox hBox1 = new HBox();
                                hBox1.setAlignment(Pos.CENTER_LEFT);
                                Text text = new Text(usr);
                                hBox1.getChildren().add(text);
                                label = new Label(usr + " :\n\n");
                                label.setGraphic(img);

                                HBox hBox2 = new HBox();
                                hBox2.setAlignment(Pos.CENTER_LEFT);
                                hBox2.setPadding(new Insets(5, 5, 5, 10));
                                hBox2.getChildren().add(img);

                                Platform.runLater(() -> {
                                    vboxForChat.getChildren().add(hBox1);
                                    vboxForChat.getChildren().add(hBox2);
                                });
                            }
                        });
                    } else {
                        splitMsg(message);
                        String preparedMsg = makeMsg();
                        if (usr.equals(usrNameTxt.getText())) {

                            HBox hBox = new HBox();
                            hBox.setAlignment(Pos.CENTER_RIGHT);
                            hBox.setPadding(new Insets(5, 5, 5, 10));

                            Text text = new Text(preparedMsg);
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
                            textFlow.setPadding(new Insets(5, 10, 5, 10));
                            text.setFill(Color.color(0, 0, 0));

                            hBox.getChildren().add(textFlow);

                            Platform.runLater(() -> {
                                vboxForChat.getChildren().add(hBox);
                            });

                        } else {
                            HBox hBox = new HBox();
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5, 5, 0, 10));

                            Text text = new Text(preparedMsg);
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
                            textFlow.setPadding(new Insets(5, 10, 5, 10));
                            text.setFill(Color.color(1, 1, 1));

                            hBox.getChildren().add(textFlow);

                            HBox hBoxName = new HBox();
                            hBoxName.setAlignment(Pos.CENTER_LEFT);
                            Text textName = new Text(usr);
                            TextFlow textFlowName = new TextFlow(textName);

                            hBoxName.getChildren().add(textFlowName);

                            Platform.runLater(() -> {
                                vboxForChat.getChildren().add(hBoxName);
                                vboxForChat.getChildren().add(hBox);
                            });
                        }
                    }
                    scrlPane.vvalueProperty().bind(vboxForChat.heightProperty());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private String splitImg(String message) {
        String[] words = message.split("!!!!split!!!!");
        this.usr = words[0];
        return words[1];
    }

    private void emoji() {
        EmojiBox emojiPicker = new EmojiBox();

        VBox vBox = new VBox(emojiPicker);
        vBox.setPrefSize(90, 110);
        vBox.setLayoutX(390);
        vBox.setLayoutY(538);
        vBox.setStyle("-fx-font-size: 20");

        mainPane.getChildren().add(vBox);

        emojiPicker.setVisible(false);

        emojiBtn.setOnAction(event -> {
            emojiPicker.setVisible(!emojiPicker.isVisible());
        });

        emojiPicker.getEmojiListView().setOnMouseClicked(event -> {
            String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                msgTxt.setText(msgTxt.getText() + selectedEmoji + "  ");
            }
            emojiPicker.setVisible(false);
        });
    }

    private String makeMsg() {
        wordList.remove(0);
        return String.join(" ", wordList);
    }

    private void splitMsg(String message) {
        String[] words = message.split(" ");
        this.usr = words[0];
        wordList = new ArrayList<>(Arrays.asList(words));
    }

    private void setName() {
        usrNameTxt.setText(LogInFormController.usrName);
    }
}
