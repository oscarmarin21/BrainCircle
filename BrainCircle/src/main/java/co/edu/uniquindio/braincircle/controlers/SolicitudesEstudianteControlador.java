package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Solicitud;
import co.edu.uniquindio.braincircle.models.Usuario;
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

public class SolicitudesEstudianteControlador implements Parametrizable, Initializable {
    
    @FXML
    private TableView<Solicitud> tablaSolicitudesPendientes;
    
    @FXML
    private TableColumn<Solicitud, String> colTituloPendiente;
    
    @FXML
    private TableColumn<Solicitud, String> colNivelPrioridadPendiente;
    
    @FXML
    private TableColumn<Solicitud, String> colMensajePendiente;
    
    @FXML
    private TableView<Solicitud> tablaSolicitudesRespondidas;
    
    @FXML
    private TableColumn<Solicitud, String> colTituloRespondida;
    
    @FXML
    private TableColumn<Solicitud, String> colNivelPrioridadRespondida;
    
    @FXML
    private TableColumn<Solicitud, String> colMensajeRespondida;
    
    @FXML
    private TableColumn<Solicitud, String> colRespuesta;
    
    @FXML
    private Label lblTotalPendientes;
    
    @FXML
    private Label lblTotalRespondidas;

    private ControladorPrincipal controladorPrincipal;
    private String idUsuario;

    public SolicitudesEstudianteControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTablas();
    }

    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista de solicitudes.");
            return;
        }

        idUsuario = parametros[0].toString();
        System.out.println("✅ Cargando solicitudes para usuario ID: " + idUsuario);
        
        cargarSolicitudesDelUsuario();
    }

    private void configurarTablas() {
        // Configurar tabla de solicitudes pendientes
        colTituloPendiente.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colNivelPrioridadPendiente.setCellValueFactory(new PropertyValueFactory<>("nivelPrioridad"));
        colMensajePendiente.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        
        // Configurar tabla de solicitudes respondidas
        colTituloRespondida.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colNivelPrioridadRespondida.setCellValueFactory(new PropertyValueFactory<>("nivelPrioridad"));
        colMensajeRespondida.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        colRespuesta.setCellValueFactory(new PropertyValueFactory<>("respuesta"));
        
        // Hacer que las columnas de mensaje se ajusten al contenido
        colMensajePendiente.prefWidthProperty().bind(tablaSolicitudesPendientes.widthProperty().multiply(0.4));
        colMensajeRespondida.prefWidthProperty().bind(tablaSolicitudesRespondidas.widthProperty().multiply(0.3));
        colRespuesta.prefWidthProperty().bind(tablaSolicitudesRespondidas.widthProperty().multiply(0.3));
    }

    private void cargarSolicitudesDelUsuario() {
        try {
            Usuario usuarioActual = controladorPrincipal.obtenerUsuarioPorId(idUsuario);
            
            if (usuarioActual != null) {
                // Obtener todas las solicitudes del usuario
                List<Solicitud> todasLasSolicitudes = controladorPrincipal.obtenerSolicitudesPorUsuario(usuarioActual);
                
                // Filtrar solicitudes pendientes (sin respuesta)
                List<Solicitud> solicitudesPendientes = todasLasSolicitudes.stream()
                    .filter(solicitud -> solicitud.getRespuesta() == null || solicitud.getRespuesta().isEmpty())
                    .toList();
                
                // Filtrar solicitudes respondidas (con respuesta)
                List<Solicitud> solicitudesRespondidas = todasLasSolicitudes.stream()
                    .filter(solicitud -> solicitud.getRespuesta() != null && !solicitud.getRespuesta().isEmpty())
                    .toList();
                
                // Cargar datos en las tablas
                ObservableList<Solicitud> datosPendientes = FXCollections.observableArrayList(solicitudesPendientes);
                ObservableList<Solicitud> datosRespondidas = FXCollections.observableArrayList(solicitudesRespondidas);
                
                tablaSolicitudesPendientes.setItems(datosPendientes);
                tablaSolicitudesRespondidas.setItems(datosRespondidas);
                
                // Actualizar etiquetas de conteo
                lblTotalPendientes.setText("Total: " + solicitudesPendientes.size());
                lblTotalRespondidas.setText("Total: " + solicitudesRespondidas.size());
                
                System.out.println("✅ Solicitudes cargadas: " + solicitudesPendientes.size() + " pendientes, " + 
                                 solicitudesRespondidas.size() + " respondidas");
                
            } else {
                System.out.println("⚠️ No se encontró el usuario con ID: " + idUsuario);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error al cargar las solicitudes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void actualizarSolicitudes(ActionEvent actionEvent) {
        cargarSolicitudesDelUsuario();
    }

    @FXML
    public void abrirCrearSolicitud(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/CrearSolicitud.fxml", "Nueva Solicitud", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    @FXML
    public void volverAlInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml", "Inicio - Estudiante", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
} 