package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;
import co.edu.uniquindio.braincircle.Grafos.GrafoAfinidadUsuarios;
import co.edu.uniquindio.braincircle.Services.ServicioBrainCircle;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;


import java.util.*;

public class BrainCircle<T extends Comparable<T>>  implements ServicioBrainCircle {
    private final Map<String, String> mapaContenidoGrupo = new HashMap<>();

    private List<Usuario> usuarios;
    private List<Contenido> contenido;
    private ArbolBinarioContenido arbolBinarioContenido;
    private GrafoAfinidadUsuarios grafoAfinidadUsuarios;
    private Queue<Solicitud> colaSolicitudes;
    private List<Solicitud> solicitudesRespondidas;
    private List<GrupoEstudio> gruposEstudio ;

    // Constructor privado para evitar instanciación externa
    public BrainCircle() {
        this.usuarios = new ArrayList<>();
        this.contenido = new ArrayList<>();
        this.arbolBinarioContenido = new ArbolBinarioContenido();
        this.grafoAfinidadUsuarios = new GrafoAfinidadUsuarios();
        this.gruposEstudio = new ArrayList<>();
        // Comparador para ordenar solicitudes por prioridad (ALTA primero, luego MEDIA, luego BAJA)
        this.colaSolicitudes = new PriorityQueue<>(Comparator.comparing(Solicitud::getNivelPrioridad, 
            (p1, p2) -> {
                if (p1 == NivelPrioridad.ALTA && p2 != NivelPrioridad.ALTA) return -1;
                if (p1 == NivelPrioridad.MEDIA && p2 == NivelPrioridad.BAJA) return -1;
                if (p1 == p2) return 0;
                return 1;
            }));
        this.solicitudesRespondidas = new ArrayList<>();
    }

    // Método para obtener la instancia única

    @Override
    public BrainCircle getBrainCircle() {
        return this;
    }

    public boolean autenticar(String correo, String clave) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getContraseña().equals(clave)) {
                return true; 
            }
        }
        throw new RuntimeException("Correo o contraseña incorrecta");
    }


    public boolean registrar(String id, String nombre, String correo, String telefono, String pass) {
        Estudiante estudiante = new Estudiante.Builder()
                .setId(id)
                .setNombre(nombre)
                .setCorreo(correo)
                .setTelefono(telefono)
                .setContraseña(pass)
                .setTipoUsuario(TipoUsuario.ESTUDIANTE)
                .build();
        usuarios.add(estudiante);
        return true;
    }

    public Usuario ObtenerUserAutenticado(String correo, String clave) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getContraseña().equals(clave)) {
                return u;
            }
        }
        return null;
    }
    public Usuario obtenerUsuarioPorId(String id){
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> cargarUsuarios(){
        return usuarios;
    }
    public boolean editarUsuario(String nombre, String correo, String telefono, String pass, Usuario usuarioActual){
        usuarioActual.setNombre(nombre);
        usuarioActual.setCorreo(correo);
        usuarioActual.setTelefono(telefono);
        usuarioActual.setContraseña(pass);
        return true;
    }
    public boolean eliminarUsuario(Usuario usuarioActual){
        List<Usuario> before = usuarios;
        usuarios.remove(usuarioActual);
        List<Usuario> after = usuarios;
        return true;
    }
    public void agregarContenido(Contenido contenido) {
        arbolBinarioContenido.agregarContenido(contenido);
    }

    public boolean actualizarContenido(Comparable idContenido, Comparable nuevoTitulo, Comparable nuevoTema, Comparable nuevoTipo, Comparable nuevoAutor, Comparable conte) {
        return arbolBinarioContenido.actualizarContenido(idContenido,nuevoTitulo,nuevoTema,nuevoTipo,nuevoAutor, conte);
    }

    public boolean eliminarContenidoPorId(Comparable idContenido){
        return arbolBinarioContenido.eliminarContenidoPorId(idContenido);
    }
    public List<Contenido<T>> cargarContenidos(){
        return arbolBinarioContenido.cargarContenidos();
    }
    public Contenido<T> obtenerContenidoPorId(Comparable idContenido) {
        return arbolBinarioContenido.obtenerContenidoPorId(idContenido);
    }
    public boolean darLikeAContenido(Comparable idContenido, String idUsuario) {
        return arbolBinarioContenido.incrementarLikePorId((T) idContenido, idUsuario);
    }

    public boolean comentarContenido(Comparable idContenido, String comentario) {
        return arbolBinarioContenido.agregarComentarioPorId(idContenido, comentario);
    }

    public void conectarUsuarios(Usuario u1, Usuario u2) {
        grafoAfinidadUsuarios.conectarUsuarios(u1, u2);
    }

    @Override
    public Set<Usuario> obtenerConexiones(Usuario u) {
        return null;
    }

    public List<Usuario> sugerenciasDeAmistad(Usuario estudiante) {
        return grafoAfinidadUsuarios.sugerenciasDeAmistad(estudiante);
    }
    public void enviarMensaje(Usuario emisor, Usuario receptor, String contenido){
        grafoAfinidadUsuarios.enviarMensaje(emisor,receptor,contenido);
    }
    public List<String> obtenerConversacion(Usuario u1, Usuario u2){
        return grafoAfinidadUsuarios.obtenerConversacion(u1,u2);
    }

    // Métodos para gestión de solicitudes
    
    /**
     * Crea y añade una nueva solicitud a la cola de prioridad
     * @param propietario Usuario que crea la solicitud
     * @param nivelPrioridad Nivel de prioridad de la solicitud
     * @param titulo Título de la solicitud
     * @param mensaje Contenido de la solicitud
     * @return La solicitud creada
     */
    public Solicitud crearSolicitud(Usuario propietario, NivelPrioridad nivelPrioridad, String titulo, String mensaje) {
        String id = UUID.randomUUID().toString().substring(0, 8); // Generar ID único
        Solicitud solicitud = new Solicitud.Builder()
                .id(id)
                .propietario(propietario)
                .nivelPrioridad(nivelPrioridad)
                .titulo(titulo)
                .mensaje(mensaje)
                .build();
        
        colaSolicitudes.add(solicitud);
        return solicitud;
    }
    
    /**
     * Obtiene la siguiente solicitud pendiente de mayor prioridad sin removerla de la cola
     * @return La solicitud de mayor prioridad o null si no hay solicitudes
     */
    public Solicitud verProximaSolicitud() {
        return colaSolicitudes.peek();
    }
    
    /**
     * Obtiene y remueve la siguiente solicitud pendiente de mayor prioridad
     * @return La solicitud de mayor prioridad o null si no hay solicitudes
     */
    public Solicitud obtenerProximaSolicitud() {
        return colaSolicitudes.poll();
    }
    
    /**
     * Responde a una solicitud específica
     * @param solicitud Solicitud a responder
     * @param respuesta Respuesta a la solicitud
     * @return true si la respuesta se realizó correctamente
     */
    public boolean responderSolicitud(Solicitud solicitud, String respuesta) {
        // Verificar si la solicitud está en la cola
        if (colaSolicitudes.remove(solicitud)) {
            solicitud.setRespuesta(respuesta);
            solicitudesRespondidas.add(solicitud);
            return true;
        }
        return false;
    }
    
    /**
     * Responde a la siguiente solicitud de mayor prioridad
     * @param respuesta Respuesta a la solicitud
     * @return La solicitud respondida o null si no hay solicitudes
     */
    public Solicitud responderProximaSolicitud(String respuesta) {
        Solicitud solicitud = colaSolicitudes.poll();
        if (solicitud != null) {
            solicitud.setRespuesta(respuesta);
            solicitudesRespondidas.add(solicitud);
        }
        return solicitud;
    }
    
    /**
     * Obtiene todas las solicitudes pendientes
     * @return Lista de solicitudes pendientes
     */
    public List<Solicitud> obtenerSolicitudesPendientes() {
        return new ArrayList<>(colaSolicitudes);
    }
    
    /**
     * Obtiene todas las solicitudes que ya han sido respondidas
     * @return Lista de solicitudes respondidas
     */
    public List<Solicitud> obtenerSolicitudesRespondidas() {
        return new ArrayList<>(solicitudesRespondidas);
    }
    
    /**
     * Obtiene todas las solicitudes de un usuario específico
     * @param usuario Usuario propietario de las solicitudes
     * @return Lista de solicitudes del usuario
     */
    public List<Solicitud> obtenerSolicitudesPorUsuario(Usuario usuario) {
        List<Solicitud> solicitudesUsuario = new ArrayList<>();
        
        // Revisar en solicitudes pendientes
        for (Solicitud s : colaSolicitudes) {
            if (s.getPropietario().getId().equals(usuario.getId())) {
                solicitudesUsuario.add(s);
            }
        }
        
        // Revisar en solicitudes respondidas
        for (Solicitud s : solicitudesRespondidas) {
            if (s.getPropietario().getId().equals(usuario.getId())) {
                solicitudesUsuario.add(s);
            }
        }
        
        return solicitudesUsuario;
    }

    //Grupos de estudio
    public void crearGrupoEstudio(String id,String nombreGrupo, String descripcion, Materia materia) {
        GrupoEstudio.crearGrupo(id,nombreGrupo, descripcion, materia);
    }
    public List<GrupoEstudio> cargarGrupos() {
        List<GrupoEstudio> listaGrupos = GrupoEstudio.cargarGrupos();
        return listaGrupos;
    }
    public boolean actualizarGrupo(String id, String nuevoNombre, String nuevaDescripcion, Materia nuevaMateria) {
        boolean actualizar = GrupoEstudio.actualizarGrupo(id,nuevoNombre,nuevaDescripcion,nuevaMateria);
        return actualizar;
    }
    public boolean eliminarGrupo(String idGrupo) {
        return GrupoEstudio.eliminarGrupo(idGrupo);
    }
    public boolean agregarMiembro(String idGrupo, Estudiante estudiante) {
        GrupoEstudio grupo = GrupoEstudio.buscarPorId(idGrupo);
        if (grupo != null) {
            return grupo.agregarMiembro(estudiante);
        }
        return false;
    }
    public boolean enviarMensajeAGrupo(String idGrupo, String idEstudiante, String contenido) {
        GrupoEstudio grupo = GrupoEstudio.buscarPorId(idGrupo);
        Usuario usuario = obtenerUsuarioPorId(idEstudiante);

        if (grupo != null && usuario instanceof Estudiante estudiante) {
            String mensajeFormateado = estudiante.getNombre() + ": " + contenido;
            grupo.enviarMensaje(mensajeFormateado);
            return true;
        }
        return false;
    }
    public void crearGruposPorAfinidadEnLikes() {
        List<Contenido<T>> contenidos = cargarContenidos();

        for (Contenido<T> contenido : contenidos) {
            Set<String> usuarios = contenido.getUsuariosQueDieronLike();

            if (usuarios.size() >= 2) {
                String idContenido = contenido.getId().toString();

                String idGrupoExistente = mapaContenidoGrupo.get(idContenido);
                GrupoEstudio grupo;

                if (idGrupoExistente != null && (grupo = GrupoEstudio.buscarPorId(idGrupoExistente)) != null) {
                    for (String idUsuario : usuarios) {
                        Usuario user = obtenerUsuarioPorId(idUsuario);
                        if (user instanceof Estudiante estudiante) {
                            grupo.agregarMiembro(estudiante);
                        }
                    }
                } else {
                    String idGrupoNuevo = UUID.randomUUID().toString().substring(0, 6);
                    String nombre = "Grupo afinidad: " + contenido.getTitulo();
                    String descripcion = "Creado por afinidad de likes en el tema: " + contenido.getTema();
                    Materia materia = Materia.valueOf(contenido.getTema().toString().toUpperCase());

                    GrupoEstudio.crearGrupo(idGrupoNuevo, nombre, descripcion, materia);
                    GrupoEstudio nuevoGrupo = GrupoEstudio.buscarPorId(idGrupoNuevo);

                    for (String idUsuario : usuarios) {
                        Usuario user = obtenerUsuarioPorId(idUsuario);
                        if (user instanceof Estudiante estudiante) {
                            nuevoGrupo.agregarMiembro(estudiante);
                        }
                    }
                    mapaContenidoGrupo.put(idContenido, idGrupoNuevo);
                }
            }
        }
    }
}

