package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.models.BrainCircle;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    private ControladorPrincipal controladorPrincipal;

    public RegistroControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private void handleRegistrarse(ActionEvent event) {
        String id = txtIdentificacion.getText();
        String nombreUsuario = txtNombre.getText();
        String correo = txtCorreo.getText();
        String tel = txtTelefono.getText();
        String pass = txtPass.getText();

        // Validación básica de campos
        if (id.isEmpty() || nombreUsuario.isEmpty() || correo.isEmpty() || tel.isEmpty() || pass.isEmpty()) {
            controladorPrincipal.mostrarMensaje("Error","Por favor, completa todos los campos.", Alert.AlertType.WARNING);
            limpiarCampos();
            return;
        }

        if (controladorPrincipal.registrar(id, nombreUsuario, correo, tel, pass)) {
            limpiarCampos();
            controladorPrincipal.mostrarMensaje("Correto","¡Registro exitoso!", Alert.AlertType.INFORMATION);
            controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioSesion.fxml","Inica Sesión", (Object) null);
            controladorPrincipal.cerrarVentana((Node) event.getSource());
        } else {
            controladorPrincipal.mostrarMensaje("Error","algo a salido mal en el registro. intentalo nuevamente.", Alert.AlertType.ERROR);
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPass.clear();
    }
}