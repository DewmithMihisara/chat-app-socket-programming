package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LogInFormController {
    @FXML
    private Line line;
    @FXML
    private Button logInBtn;

    @FXML
    private TextField usrNameTxt;
    public static String usrName;

    @FXML
    void logInBtnOnAction(ActionEvent event) throws IOException {
        usrName=usrNameTxt.getText();
        if(usrName.equals("")){
            shakeLine();
        }else {
            defaultLine();

            Parent anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/clientForm.fxml")));
            Scene scene = new Scene(anchorPane);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(usrName);
            stage.show();

            usrNameTxt.setText("");
        }
    }

    @FXML
    void usrNameTxtOnAction(ActionEvent event) {
        logInBtn.fire();
    }
    void shakeLine(){
        this.line.setStroke(Color.RED);
        new animatefx.animation.Shake(this.line).play();
    }
    void defaultLine(){
        this.line.setStroke(Color.BLACK);
    }
}
