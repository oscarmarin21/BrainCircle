package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.models.BrainCircle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InicioSesionControlador {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPass;

    private BrainCircle brainCircle = BrainCircle.getInstance();


    @FXML
    private void iniciarSesion() {
        String correo = txtCorreo.getText();
        String contrasena = txtPass.getText();

        if (correo == null || correo.trim().isEmpty()) {
            mostrarAlerta("Error", "Ingrese su correo electrónico.");
            return;
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            mostrarAlerta("Error", "Ingrese su contraseña.");
            return;
        }

        if (autenticarUsuario(correo, contrasena)) {
            mostrarAlerta("Éxito", "Inicio de sesión exitoso.");
            // Aquí puedes navegar a otra pantalla o cerrar la ventana de inicio de sesión.
        } else {
            mostrarAlerta("Error", "Correo o contraseña incorrectos.");
        }
    }

    private boolean autenticarUsuario(String correo, String contrasena) {
        try {
            return brainCircle.autenticar(correo, contrasena);
        } catch (RuntimeException e) {
            mostrarAlerta("error", e.getMessage());
        }
        return false;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}