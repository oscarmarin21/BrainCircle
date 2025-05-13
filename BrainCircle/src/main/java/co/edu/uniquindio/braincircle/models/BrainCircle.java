package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class BrainCircle {
    private static BrainCircle instance;

    private List<Usuario> usuarios;

    // Constructor privado para evitar instanciación externa
    private BrainCircle() {
        this.usuarios = new ArrayList<>();
    }

    // Método para obtener la instancia única
    public static synchronized BrainCircle getInstance() {
        if (instance == null) {
            instance = new BrainCircle();
        }
        return instance;
    }

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
}