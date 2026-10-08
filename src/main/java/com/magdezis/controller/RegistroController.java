package com.magdezis.controller;

import com.magdezis.config.SceneManager;
import com.magdezis.dto.RegistroDTO;
import com.magdezis.service.AuthException;
import com.magdezis.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;

    private final AuthService authService = new AuthService();

    @FXML
    private void onRegistrar(ActionEvent event) {
        try {
            authService.registrar(new RegistroDTO(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtCorreo.getText(),
                    txtContrasena.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("El usuario fue registrado con el rol USER.");
            alert.showAndWait();
            SceneManager.switchTo("login.fxml", "Inversiones Magdezis - Login");
        } catch (AuthException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void onVolver(ActionEvent event) {
        SceneManager.switchTo("login.fxml", "Inversiones Magdezis - Login");
    }
}
