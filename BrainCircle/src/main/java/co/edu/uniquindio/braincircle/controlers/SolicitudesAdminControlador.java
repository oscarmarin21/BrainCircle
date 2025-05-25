package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.models.Solicitud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SolicitudesAdminControlador implements Initializable {
    
    // Elementos para mostrar la solicitud actual
    @FXML
    private Label lblTituloActual;
    
    @FXML
    private Label lblPrioridadActual;
    
    @FXML
    private Label lblPropietarioActual;
    
    @FXML
    private TextArea txtMensajeActual;
    
    @FXML
    private TextArea txtRespuesta;
    
    @FXML
    private Button btnResponder;
    
    @FXML
    private Button btnSiguiente;
    
    @FXML
    private Label lblEstadoSolicitud;
    
    // Tabla de solicitudes pendientes
    @FXML
    private TableView<Solicitud> tablaSolicitudesPendientes;
    
    @FXML
    private TableColumn<Solicitud, String> colTitulo;
    
    @FXML
    private TableColumn<Solicitud, String> colPropietario;
    
    @FXML
    private TableColumn<Solicitud, String> colPrioridad;
    
    @FXML
    private Label lblTotalPendientes;

    private ControladorPrincipal controladorPrincipal;
    private Solicitud solicitudActual;

    public SolicitudesAdminControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        cargarSolicitudes();
        cargarSolicitudActual();
    }

    private void configurarTabla() {
        // Configurar las columnas de la tabla
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colPropietario.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPropietario().getNombre()));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("nivelPrioridad"));
        
        // Ajustar el ancho de las columnas
        colTitulo.prefWidthProperty().bind(tablaSolicitudesPendientes.widthProperty().multiply(0.4));
        colPropietario.prefWidthProperty().bind(tablaSolicitudesPendientes.widthProperty().multiply(0.3));
        colPrioridad.prefWidthProperty().bind(tablaSolicitudesPendientes.widthProperty().multiply(0.3));
    }

    private void cargarSolicitudes() {
        try {
            // Obtener todas las solicitudes pendientes
            List<Solicitud> solicitudesPendientes = controladorPrincipal.obtenerSolicitudesPendientes();
            
            // Cargar en la tabla
            ObservableList<Solicitud> datos = FXCollections.observableArrayList(solicitudesPendientes);
            tablaSolicitudesPendientes.setItems(datos);
            
            // Actualizar contador
            lblTotalPendientes.setText("Total pendientes: " + solicitudesPendientes.size());
            
            System.out.println("✅ Solicitudes pendientes cargadas: " + solicitudesPendientes.size());
            
        } catch (Exception e) {
            System.out.println("❌ Error al cargar solicitudes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarSolicitudActual() {
        try {
            // Obtener la próxima solicitud en la cola
            solicitudActual = controladorPrincipal.verProximaSolicitud();
            
            if (solicitudActual != null) {
                // Mostrar los datos de la solicitud actual
                lblTituloActual.setText(solicitudActual.getTitulo());
                lblPrioridadActual.setText("Prioridad: " + solicitudActual.getNivelPrioridad().toString());
                lblPropietarioActual.setText("Solicitante: " + solicitudActual.getPropietario().getNombre());
                txtMensajeActual.setText(solicitudActual.getMensaje());
                
                // Limpiar el campo de respuesta
                txtRespuesta.clear();
                
                // Habilitar controles
                btnResponder.setDisable(false);
                btnSiguiente.setDisable(false);
                txtRespuesta.setDisable(false);
                
                lblEstadoSolicitud.setText("📋 Solicitud cargada - Lista para responder");
                lblEstadoSolicitud.setStyle("-fx-text-fill: #010035; -fx-font-weight: bold;");
                
            } else {
                // No hay solicitudes pendientes
                limpiarVistaActual();
                lblEstadoSolicitud.setText("✅ No hay solicitudes pendientes");
                lblEstadoSolicitud.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error al cargar solicitud actual: " + e.getMessage());
            e.printStackTrace();
            lblEstadoSolicitud.setText("❌ Error al cargar solicitud");
            lblEstadoSolicitud.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
        }
    }

    private void limpiarVistaActual() {
        lblTituloActual.setText("No hay solicitudes");
        lblPrioridadActual.setText("Prioridad: -");
        lblPropietarioActual.setText("Solicitante: -");
        txtMensajeActual.clear();
        txtRespuesta.clear();
        
        // Deshabilitar controles
        btnResponder.setDisable(true);
        btnSiguiente.setDisable(true);
        txtRespuesta.setDisable(true);
    }

    @FXML
    public void responderSolicitud(ActionEvent actionEvent) {
        if (solicitudActual == null) {
            mostrarMensajeError("No hay solicitud para responder");
            return;
        }
        
        String respuesta = txtRespuesta.getText().trim();
        if (respuesta.isEmpty()) {
            mostrarMensajeError("⚠️ Debe escribir una respuesta");
            txtRespuesta.requestFocus();
            return;
        }
        
        if (respuesta.length() < 10) {
            mostrarMensajeError("⚠️ La respuesta debe tener al menos 10 caracteres");
            txtRespuesta.requestFocus();
            return;
        }
        
        try {
            // Responder la solicitud
            boolean exito = controladorPrincipal.responderSolicitud(solicitudActual, respuesta);
            
            if (exito) {
                mostrarMensajeExito("✅ Solicitud respondida exitosamente");
                
                // Recargar datos
                cargarSolicitudes();
                cargarSolicitudActual();
                
            } else {
                mostrarMensajeError("❌ Error al responder la solicitud");
            }
            
        } catch (Exception e) {
            mostrarMensajeError("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void siguienteSolicitud(ActionEvent actionEvent) {
        if (solicitudActual == null) {
            mostrarMensajeError("No hay solicitud actual");
            return;
        }
        
        try {
            // Obtener y remover la solicitud actual de la cola
            controladorPrincipal.obtenerProximaSolicitud();
            
            // Cargar la siguiente solicitud
            cargarSolicitudActual();
            cargarSolicitudes();
            
            lblEstadoSolicitud.setText("⏭️ Pasando a la siguiente solicitud");
            lblEstadoSolicitud.setStyle("-fx-text-fill: #007bff; -fx-font-weight: bold;");
            
        } catch (Exception e) {
            mostrarMensajeError("❌ Error al pasar a la siguiente solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void actualizarSolicitudes(ActionEvent actionEvent) {
        cargarSolicitudes();
        cargarSolicitudActual();
        lblEstadoSolicitud.setText("🔄 Solicitudes actualizadas");
        lblEstadoSolicitud.setStyle("-fx-text-fill: #007bff; -fx-font-weight: bold;");
    }

    @FXML
    public void volverAlPanel(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml", "Panel Administrador", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    private void mostrarMensajeExito(String mensaje) {
        lblEstadoSolicitud.setText(mensaje);
        lblEstadoSolicitud.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
    }

    private void mostrarMensajeError(String mensaje) {
        lblEstadoSolicitud.setText(mensaje);
        lblEstadoSolicitud.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
    }
} 