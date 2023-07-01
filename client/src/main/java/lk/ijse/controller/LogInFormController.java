package lk.ijse.controller;

import animatefx.animation.AnimateFXInterpolator;
import animatefx.animation.AnimationFX;
import animatefx.animation.Shake;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class LogInFormController {
    @FXML
    private Line line;
    @FXML
    private Button logInBtn;
    @FXML
    private TextField usrNameTxt;
    public static String usrName;
    Shake shake;
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
            stage.setResizable(false);
            stage.show();

            usrNameTxt.setText("");
        }
    }

    @FXML
    void typingValidationOnKeyTyped(KeyEvent event) {
        spaceBlockOnKeyTyped(event);
        handleKeyTyped();
    }
    @FXML
    void mouseClickOnAction(MouseEvent event) {
        defaultLine();
        shake.stop();
    }
    @FXML
    void usrNameTxtOnAction(ActionEvent event) {
        logInBtn.fire();
    }
    void shakeLine(){
        this.line.setStroke(Color.RED);
        shake=new Shake(line);
        shake.setOnFinished(actionEvent -> {
            defaultLine();
        });
        shake.play();
    }
    void defaultLine(){
        this.line.setStroke(Color.BLACK);
    }
    void spaceBlockOnKeyTyped(KeyEvent event) {
        if (" ".equals(event.getCharacter())) {
            String trimmedText = usrNameTxt.getText().trim();
            usrNameTxt.setText(trimmedText);
            usrNameTxt.positionCaret(trimmedText.length());
        }
    }
    private void handleKeyTyped() {
        String inputText = usrNameTxt.getText();
        if (inputText.length() > 15) {
            String limitedText = inputText.substring(0, 15);
            usrNameTxt.setText(limitedText);
            usrNameTxt.positionCaret(limitedText.length());
        }
    }
}
