package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.ChatListener;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.Services.ServicioBrainCircle;
import co.edu.uniquindio.braincircle.models.BrainCircle;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Solicitud;
import co.edu.uniquindio.braincircle.models.Usuario;
import co.edu.uniquindio.braincircle.models.enums.NivelPrioridad;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ControladorPrincipal<T extends Comparable<T>> implements ServicioBrainCircle<T> {
    private static final ControladorPrincipal INSTANCIA = new ControladorPrincipal();
    private final BrainCircle<T> brainCircle;
    private final List<ChatListener> chatListeners = new ArrayList<>();
    public ControladorPrincipal() {
        brainCircle = new BrainCircle();
        brainCircle.cargarContenidos(); // o cargarUsuarios(), etc.
    }


    public static ControladorPrincipal getInstancia() {
        return INSTANCIA;
    }
    public void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta - BrainCircle" + titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public FXMLLoader navegar(String nombreVista, String titulo, Object... parametros){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreVista));
            Parent root = loader.load();

            Object controller = loader.getController();

//            recibe los parametros para enviar entre controladores
            if (controller instanceof Parametrizable) {
                ((Parametrizable) controller).datosBrainCircle(parametros);
            }

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(titulo);

            // Mostrar la nueva ventana
            stage.show();

            return loader;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // Se crea para pasar controladores entre ventanas, osea carga la vista pero no muestra una nueva ventana
    public FXMLLoader cargarVista(String nombreVista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreVista));
            loader.load();
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public BrainCircle getBrainCircle() {
        return brainCircle.getBrainCircle();
    }

    @Override
    public boolean autenticar(String correo, String contrasena){
        return brainCircle.autenticar(correo, contrasena);
    }

    @Override
    public boolean registrar(String id, String nombre, String correo, String telefono, String pass) {
        return brainCircle.registrar(id, nombre, correo, telefono, pass);
    }

    @Override
    public boolean editarUsuario(String nombre, String correo, String telefono, String pass, Usuario usuarioActual){
        return brainCircle.editarUsuario(nombre, correo, telefono, pass, usuarioActual);
    }

    @Override
    public boolean eliminarUsuario(Usuario usuarioActual){
        return brainCircle.eliminarUsuario(usuarioActual);
    }

    @Override
    public void agregarContenido(Contenido<T> contenido) {
        brainCircle.agregarContenido(contenido);
    }

    @Override
    public boolean actualizarContenido(T idContenido, T nuevoTitulo, T nuevoTema, T nuevoTipo, T nuevoAutor, T conte) {
        return brainCircle.actualizarContenido( idContenido, nuevoTitulo, nuevoTema, nuevoTipo, nuevoAutor,conte);
    }

    @Override
    public boolean eliminarContenidoPorId(T idContenido) {
        return brainCircle.eliminarContenidoPorId(idContenido);
    }

    @Override
    public List<Contenido<T>> cargarContenidos() {
        return brainCircle.cargarContenidos();
    }

    @Override
    public Contenido<T> obtenerContenidoPorId(T idContenido) {
        return brainCircle.obtenerContenidoPorId(idContenido);
    }

    @Override
    public Usuario ObtenerUserAutenticado(String correo, String clave) {
        return brainCircle.ObtenerUserAutenticado(correo,clave);
    }
    @Override
    public boolean darLikeAContenido(Comparable idContenido, String idUsuario) {
        return brainCircle.darLikeAContenido(idContenido,idUsuario);
    }
    @Override
    public boolean comentarContenido(Comparable idContenido, String comentario) {
        return brainCircle.comentarContenido(idContenido, comentario);
    }

    @Override
    public void conectarUsuarios(Usuario u1, Usuario u2) {
       brainCircle.conectarUsuarios(u1,u2);
    }

    @Override
    public Set<Usuario> obtenerConexiones(Usuario u) {
        return brainCircle.obtenerConexiones(u);
    }

    @Override
    public List<Usuario> sugerenciasDeAmistad(Usuario estudiante) {
        return brainCircle.sugerenciasDeAmistad(estudiante);
    }

    @Override
    public Usuario obtenerUsuarioPorId(String id) {
        return brainCircle.obtenerUsuarioPorId(id);
    }

    @Override
    public void enviarMensaje(Usuario emisor, Usuario receptor, String contenido) {
        brainCircle.enviarMensaje(emisor,receptor,contenido);
        notificarMensajeNuevo(emisor.getNombre() + ": " + contenido);
    }

    @Override
    public List<String> obtenerConversacion(Usuario u1, Usuario u2) {
        return brainCircle.obtenerConversacion(u1,u2);
    }

    public void registrarChatListener(ChatListener listener) {
        if (!chatListeners.contains(listener)) {
            chatListeners.add(listener);
        }
    }

    public void notificarMensajeNuevo(String mensaje) {
        for (ChatListener listener : chatListeners) {
            listener.onMensajeNuevo(mensaje);
        }
    }

    // Métodos para gestión de solicitudes
    
    /**
     * Crea una nueva solicitud
     * @param propietario Usuario que crea la solicitud
     * @param nivelPrioridad Nivel de prioridad de la solicitud
     * @param titulo Título de la solicitud
     * @param mensaje Contenido de la solicitud
     * @return La solicitud creada
     */
    public Solicitud crearSolicitud(Usuario propietario, NivelPrioridad nivelPrioridad, String titulo, String mensaje) {
        return brainCircle.crearSolicitud(propietario, nivelPrioridad, titulo, mensaje);
    }
    
    /**
     * Obtiene la próxima solicitud en la cola sin removerla
     * @return La próxima solicitud o null si no hay solicitudes pendientes
     */
    public Solicitud verProximaSolicitud() {
        return brainCircle.verProximaSolicitud();
    }
    
    /**
     * Obtiene y remueve la próxima solicitud en la cola
     * @return La próxima solicitud o null si no hay solicitudes pendientes
     */
    public Solicitud obtenerProximaSolicitud() {
        return brainCircle.obtenerProximaSolicitud();
    }
    
    /**
     * Responde a una solicitud específica
     * @param solicitud Solicitud a responder
     * @param respuesta Respuesta a la solicitud
     * @return true si la solicitud fue respondida correctamente
     */
    public boolean responderSolicitud(Solicitud solicitud, String respuesta) {
        return brainCircle.responderSolicitud(solicitud, respuesta);
    }
    
    /**
     * Responde a la próxima solicitud en la cola
     * @param respuesta Respuesta a la solicitud
     * @return La solicitud respondida o null si no hay solicitudes
     */
    public Solicitud responderProximaSolicitud(String respuesta) {
        return brainCircle.responderProximaSolicitud(respuesta);
    }
    
    /**
     * Obtiene todas las solicitudes pendientes
     * @return Lista de solicitudes pendientes
     */
    public List<Solicitud> obtenerSolicitudesPendientes() {
        return brainCircle.obtenerSolicitudesPendientes();
    }
    
    /**
     * Obtiene todas las solicitudes que ya han sido respondidas
     * @return Lista de solicitudes respondidas
     */
    public List<Solicitud> obtenerSolicitudesRespondidas() {
        return brainCircle.obtenerSolicitudesRespondidas();
    }
    
    /**
     * Obtiene todas las solicitudes de un usuario específico
     * @param usuario Usuario propietario de las solicitudes
     * @return Lista de solicitudes del usuario
     */
    public List<Solicitud> obtenerSolicitudesPorUsuario(Usuario usuario) {
        return brainCircle.obtenerSolicitudesPorUsuario(usuario);
    }
}
