package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private TextArea chatTxtArea;

    @FXML
    private Button emojiBtn;

    @FXML
    private Button imgBtn;

    @FXML
    private TextField msgTxt;

    @FXML
    private Text usrNameTxt;
    String []msg;
    String message;

    {
        message = "";
    }
    String usr;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ArrayList<String> wordList;
    @FXML
    void emojiBtnOnAction(ActionEvent event) {

    }

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
    }

    private void msg() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 4001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (socket.isConnected()) {
                    message = dataInputStream.readUTF();
                    splitMsg(message);
                    String preparedMsg=makeMsg();
                    if (usr.equals(usrNameTxt.getText())){
                        chatTxtArea.appendText("\n" + "Me : " + preparedMsg);
                    }else {
                        chatTxtArea.appendText("\n" + usr + " : " + preparedMsg);
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
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
