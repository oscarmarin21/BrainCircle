package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;

public class EditarPerfilControlador implements Parametrizable {
    
    @FXML
    private TextField txtIdentificacion;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnActualizar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnEditar;
    
    private ControladorPrincipal controladorPrincipal;
    private String idUsuario;
    private Usuario usuarioActual;

    public void volver(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml", "Inicio", usuarioActual.getId());
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    
    @FXML
    public void initialize() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
        
        // Inicialmente los campos están deshabilitados hasta que se presione "Editar"
        deshabilitarCampos();
        
        // Configurar acción del botón Editar
        btnEditar.setOnAction(event -> habilitarCampos());
        
        // Configurar acción del botón Actualizar
        btnActualizar.setOnAction(event -> actualizarPerfil());

    }
    
    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            controladorPrincipal.mostrarMensaje("Error", "No se enviaron parámetros al cargar la vista.", Alert.AlertType.ERROR);
            return;
        }

        idUsuario = parametros[0].toString();
        usuarioActual = controladorPrincipal.obtenerUsuarioPorId(idUsuario);
        
        if (usuarioActual != null) {
            cargarDatosUsuario();
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo encontrar la información del usuario.", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDatosUsuario() {
        // Cargar la información del usuario en los campos
        txtIdentificacion.setText(usuarioActual.getId());
        txtNombre.setText(usuarioActual.getNombre());
        txtCorreo.setText(usuarioActual.getCorreo());
        txtTelefono.setText(usuarioActual.getTelefono());
        txtPassword.setText(usuarioActual.getContraseña());
    }
    
    private void deshabilitarCampos() {
        // El ID no se puede editar
        txtIdentificacion.setEditable(false);
        txtNombre.setEditable(false);
        txtCorreo.setEditable(false);
        txtTelefono.setEditable(false);
        txtPassword.setEditable(false);
        
        btnActualizar.setDisable(true);
    }
    
    private void habilitarCampos() {
        // El ID sigue sin poder editarse
        txtIdentificacion.setEditable(false);
        // Habilitar edición de los demás campos
        txtNombre.setEditable(true);
        txtCorreo.setEditable(true);
        txtTelefono.setEditable(true);
        txtPassword.setEditable(true);
        
        btnActualizar.setDisable(false);
    }
    
    private void actualizarPerfil() {
        // Verificar que no haya campos vacíos
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() || 
            txtTelefono.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            controladorPrincipal.mostrarMensaje("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        // Actualizar información del usuario
        if (controladorPrincipal.editarUsuario(txtNombre.getText(), txtCorreo.getText(), txtTelefono.getText(), txtPassword.getText(), usuarioActual)){
            controladorPrincipal.mostrarMensaje("Éxito", "Información actualizada correctamente", Alert.AlertType.INFORMATION);
            controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml","Inicio Estudiante",idUsuario);
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No fue posible editar, Intenta nuevamente", Alert.AlertType.INFORMATION);
        }
        

        
        // Volver a deshabilitar los campos después de actualizar
        deshabilitarCampos();
    }
    
    @FXML
    private void eliminarCuenta(ActionEvent actionEvent) {
        // Redirigir al usuario a la pantalla principal (hello-view.fxml)
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/hello-view.fxml", "BrainCircle", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
        controladorPrincipal.eliminarUsuario(usuarioActual);
    }
}
