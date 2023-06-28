package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    @FXML
    private AnchorPane chatAnchrPane;

    @FXML
    private ScrollPane chatScrlPane;

    @FXML
    private VBox chatVbx;

    @FXML
    private Button emojiBtn;

    @FXML
    private Button imgBtn;

    @FXML
    private TextField msgTxt;

    @FXML
    private Text usrNameTxt;

    @FXML
    void emojiBtnOnAction(ActionEvent event) {

    }

    @FXML
    void imgBtnOnAction(ActionEvent event) {

    }

    @FXML
    void msgTxtOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
    }

    private void setName() {
        usrNameTxt.setText(LogInFormController.usrName);
    }
}
