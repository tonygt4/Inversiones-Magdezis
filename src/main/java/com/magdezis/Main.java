package com.magdezis;

import com.magdezis.config.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.init(stage);
        SceneManager.switchTo("login.fxml", "Inversiones Magdezis - Login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
