package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.Services.ServicioBrainCircle;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class BrainCircle<T extends Comparable<T>>  implements ServicioBrainCircle {
    private List<Usuario> usuarios;
    private List<Contenido> contenido;
    private ArbolBinarioContenido arbolBinarioContenido;
    // Constructor privado para evitar instanciación externa
    public BrainCircle() {
        this.usuarios = new ArrayList<>();
        this.contenido = new ArrayList<>();
        this.arbolBinarioContenido = new ArbolBinarioContenido();
    }

    // Método para obtener la instancia única

    public boolean autenticar(String correo, String clave) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getContraseña().equals(clave)){
                return true;
            } else {
                throw new RuntimeException("Correo o contraseña incorrecta");
            }
        }
        return false;
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

}