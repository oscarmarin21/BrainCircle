package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Solicitud;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CrearSolicitudControlador implements Parametrizable, Initializable {
    
    @FXML
    private TextField txtTitulo;
    
    @FXML
    private TextArea txtMensaje;
    
    @FXML
    private ComboBox<NivelPrioridad> cmbPrioridad;
    
    @FXML
    private Label lblMensajeEstado;
    
    @FXML
    private Button btnCrear;
    
    @FXML
    private Button btnCancelar;

    private ControladorPrincipal controladorPrincipal;
    private String idUsuario;

    public CrearSolicitudControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarComboBox();
        configurarValidaciones();
    }

    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista de crear solicitud.");
            return;
        }

        idUsuario = parametros[0].toString();
        System.out.println("✅ Creando solicitud para usuario ID: " + idUsuario);
    }

    private void configurarComboBox() {
        // Agregar los niveles de prioridad al ComboBox
        cmbPrioridad.getItems().addAll(NivelPrioridad.values());
        cmbPrioridad.setValue(NivelPrioridad.MEDIA); // Valor por defecto
    }

    private void configurarValidaciones() {
        // Configurar el TextArea para que tenga un límite de caracteres
        txtMensaje.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 500) {
                txtMensaje.setText(oldValue);
            }
        });
        
        // Configurar el TextField del título para que tenga un límite
        txtTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                txtTitulo.setText(oldValue);
            }
        });
    }

    @FXML
    public void crearSolicitud(ActionEvent actionEvent) {
        if (validarCampos()) {
            try {
                Usuario usuarioActual = controladorPrincipal.obtenerUsuarioPorId(idUsuario);
                
                if (usuarioActual != null) {
                    // Crear la solicitud
                    Solicitud nuevaSolicitud = controladorPrincipal.crearSolicitud(
                        usuarioActual,
                        cmbPrioridad.getValue(),
                        txtTitulo.getText().trim(),
                        txtMensaje.getText().trim()
                    );
                    
                    if (nuevaSolicitud != null) {
                        mostrarMensajeExito("✅ Solicitud creada exitosamente");
                        limpiarCampos();
                        
                        // Volver a la vista de solicitudes después de un breve delay
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000); // 1 segundos de delay
                                javafx.application.Platform.runLater(() -> {
                                    volverASolicitudes(actionEvent);
                                });
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }).start();
                        
                    } else {
                        mostrarMensajeError("❌ Error al crear la solicitud");
                    }
                } else {
                    mostrarMensajeError("❌ Usuario no encontrado");
                }
                
            } catch (Exception e) {
                mostrarMensajeError("❌ Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean validarCampos() {
        if (txtTitulo.getText() == null || txtTitulo.getText().trim().isEmpty()) {
            mostrarMensajeError("⚠️ El título es obligatorio");
            txtTitulo.requestFocus();
            return false;
        }
        
        if (txtMensaje.getText() == null || txtMensaje.getText().trim().isEmpty()) {
            mostrarMensajeError("⚠️ El mensaje es obligatorio");
            txtMensaje.requestFocus();
            return false;
        }
        
        if (cmbPrioridad.getValue() == null) {
            mostrarMensajeError("⚠️ Debe seleccionar un nivel de prioridad");
            cmbPrioridad.requestFocus();
            return false;
        }
        
        if (txtTitulo.getText().trim().length() < 5) {
            mostrarMensajeError("⚠️ El título debe tener al menos 5 caracteres");
            txtTitulo.requestFocus();
            return false;
        }
        
        if (txtMensaje.getText().trim().length() < 10) {
            mostrarMensajeError("⚠️ El mensaje debe tener al menos 10 caracteres");
            txtMensaje.requestFocus();
            return false;
        }
        
        return true;
    }

    private void mostrarMensajeExito(String mensaje) {
        lblMensajeEstado.setText(mensaje);
        lblMensajeEstado.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
    }

    private void mostrarMensajeError(String mensaje) {
        lblMensajeEstado.setText(mensaje);
        lblMensajeEstado.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
    }

    private void limpiarCampos() {
        txtTitulo.clear();
        txtMensaje.clear();
        cmbPrioridad.setValue(NivelPrioridad.MEDIA);
        lblMensajeEstado.setText("");
    }

    @FXML
    public void cancelar(ActionEvent actionEvent) {
        volverASolicitudes(actionEvent);
    }

    @FXML
    public void volverASolicitudes(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/SolicitudesEstudiante.fxml", "Mis Solicitudes", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
} 