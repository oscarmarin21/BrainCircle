package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.Services.ServicioBrainCircle;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class BrainCircle<T extends Comparable<T>> implements ServicioBrainCircle {
    private List<Usuario> usuarios;
    private T contenido;
    private ArbolBinarioContenido arbolBinarioContenido;
    // Constructor privado para evitar instanciación externa
    public BrainCircle() {
        this.usuarios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
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
//    public void agregarContenido(Contenido contenido) {
//        arbolBinarioContenido.agregarContenido(contenido);
//    }
//
//    public boolean actualizarContenido( String idContenido, String nuevoTitulo, nuevoTema, T nuevoTipo, T nuevoAutor) {
//        return arbolBinarioContenido.actualizarContenido(idContenido,nuevoTitulo,nuevoTema,nuevoTipo,nuevoAutor);
//    }

}