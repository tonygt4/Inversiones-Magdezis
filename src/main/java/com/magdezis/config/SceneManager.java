package com.magdezis.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class SceneManager {

    private static Stage stage;

    private SceneManager() {
    }

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void switchTo(String fxml, String titulo) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource("/view/" + fxml));
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo cargar la vista " + fxml, e);
        }
    }
}
