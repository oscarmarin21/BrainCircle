package co.edu.uniquindio.braincircle.Services;

import co.edu.uniquindio.braincircle.models.BrainCircle;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Solicitud;
import co.edu.uniquindio.braincircle.models.Usuario;
import co.edu.uniquindio.braincircle.models.enums.NivelPrioridad;

import java.util.List;
import java.util.Set;

public interface ServicioBrainCircle<T extends Comparable<T>> {

    BrainCircle getBrainCircle();
    public boolean autenticar(String correo, String clave);
    public boolean registrar(String id, String nombre, String correo, String telefono, String pass);
    public boolean editarUsuario(String nombre, String correo, String telefono, String pass, Usuario usuarioActual);
    public boolean eliminarUsuario(Usuario usuarioActual);
    public void agregarContenido(Contenido<T> contenido);
    public boolean actualizarContenido(T idContenido, T nuevoTitulo, T nuevoTema, T nuevoTipo,T nuevoAutor,T conte) ;
    public boolean eliminarContenidoPorId(T idContenido);
    public List<Contenido<T>> cargarContenidos();
    public Contenido<T> obtenerContenidoPorId(T idContenido);
    public  Usuario ObtenerUserAutenticado(String correo, String clave) ;
    public boolean darLikeAContenido(Comparable idContenido, String idUsuario) ;
    public boolean comentarContenido(Comparable idContenido, String comentario);
    public void conectarUsuarios(Usuario u1, Usuario u2);
    public List<Usuario> sugerenciasDeAmistad(Usuario estudiante);
    public Usuario obtenerUsuarioPorId(String id);
    public void enviarMensaje(Usuario emisor, Usuario receptor, String contenido);
    public List<String> obtenerConversacion(Usuario u1, Usuario u2);

    // Métodos para gestión de solicitudes
    public Solicitud crearSolicitud(Usuario propietario, NivelPrioridad nivelPrioridad, String titulo, String mensaje);
    public Solicitud verProximaSolicitud();
    public Solicitud obtenerProximaSolicitud();
    public boolean responderSolicitud(Solicitud solicitud, String respuesta);
    public Solicitud responderProximaSolicitud(String respuesta);
    public List<Solicitud> obtenerSolicitudesPendientes();
    public List<Solicitud> obtenerSolicitudesRespondidas();
    public List<Solicitud> obtenerSolicitudesPorUsuario(Usuario usuario);
}
