package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.controller.LogInFormController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent parent= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/logInForm.fxml")));
        stage.setTitle("LogIn");
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                LogInFormController.clsStg();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }
}