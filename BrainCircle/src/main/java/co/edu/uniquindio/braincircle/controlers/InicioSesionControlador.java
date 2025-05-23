package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.models.BrainCircle;
import co.edu.uniquindio.braincircle.models.Usuario;
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

    private ControladorPrincipal controladorPrincipal;

    public InicioSesionControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    @FXML
    private void iniciarSesion() {
        String correo = txtCorreo.getText();
        String contrasena = txtPass.getText();
        if (correo == null || correo.trim().isEmpty()) {
            controladorPrincipal.mostrarMensaje("Error", "Ingrese su correo electrónico.",AlertType.ERROR);
            return;
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            controladorPrincipal.mostrarMensaje("Error", "Ingrese su contraseña.",AlertType.ERROR);
            return;
        }
        if(correo.equals("arepitas.com") && contrasena.equals("carnemolida")){
            controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml", "Graficas Admin", null);
        }
        else if (autenticarUsuario(correo, contrasena)) {
            controladorPrincipal.mostrarMensaje("Éxito", "Inicio de sesión exitoso.",AlertType.INFORMATION);
            Usuario user = controladorPrincipal.ObtenerUserAutenticado(txtCorreo.getText(),txtPass.getText());
            if(user != null){
                controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml","Inicio Estudiante",user.getId());
            }
        } else {
            controladorPrincipal.mostrarMensaje("Error", "Correo o contraseña incorrectos.",AlertType.ERROR);
        }
    }

    private boolean autenticarUsuario(String correo, String contrasena) {
        try {
            return controladorPrincipal.autenticar(correo, contrasena);
        } catch (RuntimeException e) {
            controladorPrincipal.mostrarMensaje("Error",  e.getMessage(),AlertType.ERROR);
        }
        return false;
    }


}