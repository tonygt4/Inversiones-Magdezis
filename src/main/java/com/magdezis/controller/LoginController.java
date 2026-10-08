package com.magdezis.controller;

import com.magdezis.config.SceneManager;
import com.magdezis.dto.LoginDTO;
import com.magdezis.model.Usuario;
import com.magdezis.service.AuthException;
import com.magdezis.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;

    private final AuthService authService = new AuthService();

    @FXML
    private void onIngresar(ActionEvent event) {
        try {
            Usuario usuario = authService.login(new LoginDTO(txtCorreo.getText(), txtContrasena.getText()));
            lblMensaje.setText("");
            mostrarAlertaRol(usuario);
            txtContrasena.clear();
        } catch (AuthException e) {
            lblMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void onIrRegistro(ActionEvent event) {
        SceneManager.switchTo("registro.fxml", "Inversiones Magdezis - Registro");
    }

    private void mostrarAlertaRol(Usuario usuario) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inicio de sesión exitoso");
        alert.setHeaderText("Bienvenido, " + usuario.getNombre() + " " + usuario.getApellido());
        alert.setContentText("Rol del usuario que inició sesión: " + usuario.getRol().getNombre());
        alert.showAndWait();
    }
}
