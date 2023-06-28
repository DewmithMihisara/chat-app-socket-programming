package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LogInFormController {
    @FXML
    private Button logInBtn;

    @FXML
    private TextField usrNameTxt;

    @FXML
    void logInBtnOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/clientForm.fxml")));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(usrNameTxt.getText());
        stage.show();
    }

    @FXML
    void usrNameTxtOnAction(ActionEvent event) {
        logInBtn.fire();
    }
}
