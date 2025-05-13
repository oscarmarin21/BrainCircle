package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.models.BrainCircle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class RegistroControlador {

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private PasswordField txtPass;

    private BrainCircle brainCircle = BrainCircle.getInstance();

    @FXML
    private void handleRegistrarse(ActionEvent event) {
        String id = txtIdentificacion.getText();
        String nombreUsuario = txtNombre.getText();
        String correo = txtCorreo.getText();
        String tel = txtTelefono.getText();
        String pass = txtPass.getText();

        // Validación básica de campos
        if (id.isEmpty() || nombreUsuario.isEmpty() || correo.isEmpty() || tel.isEmpty() || pass.isEmpty()) {
            mostrarMensaje("Error","Por favor, completa todos los campos.");
            limpiarCampos();
            return;
        }

        if (brainCircle.registrar(id, nombreUsuario, correo, tel, pass)) {
            mostrarMensaje("Correto","¡Registro exitoso!");
            limpiarCampos();
        } else {
            mostrarMensaje("Error","algo a salido mmal en el registro. intentalo nuevamente.");
            limpiarCampos();
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPass.clear();
    }
}