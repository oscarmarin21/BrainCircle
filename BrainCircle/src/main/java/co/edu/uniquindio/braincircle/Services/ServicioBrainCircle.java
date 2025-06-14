package co.edu.uniquindio.braincircle.Services;

import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.models.*;
import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;

import java.util.List;
import java.util.Set;

public interface ServicioBrainCircle<T extends Comparable<T>> {

    BrainCircle getBrainCircle();

    public boolean autenticar(String correo, String clave);

    public boolean registrar(String id, String nombre, String correo, String telefono, String pass);

    public boolean editarUsuario(String nombre, String correo, String telefono, String pass, Usuario usuarioActual);

    public boolean eliminarUsuario(Usuario usuarioActual);

    public void agregarContenido(Contenido<T> contenido);

    public boolean actualizarContenido(T idContenido, T nuevoTitulo, T nuevoTema, T nuevoTipo, T nuevoAutor, T conte);

    public boolean eliminarContenidoPorId(T idContenido);

    public List<Contenido<T>> cargarContenidos();

    public Contenido<T> obtenerContenidoPorId(T idContenido);

    public Usuario ObtenerUserAutenticado(String correo, String clave);

    public boolean darLikeAContenido(Comparable idContenido, String idUsuario);

    public boolean comentarContenido(Comparable idContenido, String comentario);

    public void conectarUsuarios(Usuario u1, Usuario u2);

    public Set<Usuario> obtenerConexiones(Usuario u);

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

    public void crearGrupoEstudio(String id, String nombreGrupo, String descripcion, Materia materia);

    public List<GrupoEstudio> cargarGrupos();

    public boolean actualizarGrupo(String id, String nuevoNombre, String nuevaDescripcion, Materia nuevaMateria);
    public boolean eliminarGrupo(String idGrupo);
    public boolean agregarMiembro(String idGrupo, Estudiante estudiante);
    public boolean enviarMensajeAGrupo(String idGrupo, String idEstudiante, String contenido);
    public void crearGruposPorAfinidadEnLikes();
}
