package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.Grafos.GrafoAfinidadUsuarios;
import co.edu.uniquindio.braincircle.Services.ServicioBrainCircle;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BrainCircle<T extends Comparable<T>>  implements ServicioBrainCircle {
    private List<Usuario> usuarios;
    private List<Contenido> contenido;
    private ArbolBinarioContenido arbolBinarioContenido;
    private GrafoAfinidadUsuarios grafoAfinidadUsuarios;
    // Constructor privado para evitar instanciación externa
    public BrainCircle() {
        this.usuarios = new ArrayList<>();
        this.contenido = new ArrayList<>();
        this.arbolBinarioContenido = new ArbolBinarioContenido();
        this.grafoAfinidadUsuarios = new GrafoAfinidadUsuarios();
    }

    // Método para obtener la instancia única

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
    public Set<Usuario> obtenerConexiones(Usuario u) {
        return grafoAfinidadUsuarios.obtenerConexiones(u);
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
}