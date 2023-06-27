package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LogInFormController {
    @FXML
    private Button logInBtn;

    @FXML
    private TextField usrNameTxt;

    @FXML
    void logInBtnOnAction(ActionEvent event) {
        logInBtn.fire();
    }

    @FXML
    void usrNameTxtOnAction(ActionEvent event) {

    }
}
