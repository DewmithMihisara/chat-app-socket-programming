package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lk.ijse.emoji.EmojiBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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


    @FXML
    void imgBtnOnAction(ActionEvent event) {

    }

    @FXML
    void msgTxtOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(usrNameTxt.getText() +" "+ msgTxt.getText());
        dataOutputStream.flush();
        msgTxt.setText("");
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
                socket = new Socket("localhost", 4001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (socket.isConnected()) {
                    HBox hBox=new HBox(12);
                    message = dataInputStream.readUTF();
                    splitMsg(message);
                    String preparedMsg=makeMsg();
                    String lbl;
                    if (usr.equals(usrNameTxt.getText())){
                        lbl="\n" + "Me : " + preparedMsg;
                    }else {
                        lbl="\n" + usr + " : " + preparedMsg;
                    }
                    Platform.runLater(() -> {
                        label=new Label(lbl);
                        hBox.getChildren().add(label);
                        vboxForChat.getChildren().add(hBox);
                    });
                    scrlPane.vvalueProperty().bind(vboxForChat.heightProperty());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void emoji() {
        EmojiBox emojiPicker = new EmojiBox();

        VBox vBox = new VBox(emojiPicker);
        vBox.setPrefSize(75,110);
        vBox.setLayoutX(43);
        vBox.setLayoutY(538);
        vBox.setStyle("-fx-font-size: 15");

        mainPane.getChildren().add(vBox);

        emojiPicker.setVisible(false);

        emojiBtn.setOnAction(event -> {
            if (emojiPicker.isVisible()){
                emojiPicker.setVisible(false);
            }else {
                emojiPicker.setVisible(true);
            }
        });

        emojiPicker.getEmojiListView().setOnMouseClicked(event -> {
            String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                msgTxt.setText(msgTxt.getText()+selectedEmoji);
            }
            emojiPicker.setVisible(false);
        });
    }

    private String makeMsg() {
        wordList.remove(0);
        String msg=String.join(" ",wordList);
        return msg;
    }

    private void splitMsg(String message) {
        String[] words = message.split(" ");
        this.usr=words[0];
        wordList = new ArrayList<>(Arrays.asList(words));
    }

    private void setName() {
        usrNameTxt.setText(LogInFormController.usrName);
    }
}
