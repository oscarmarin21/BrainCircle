package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    // Atributos
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contraseña;
    private TipoUsuario tipoUsuario;
    private List<Usuario> conexiones;

    // Constructor
    public Usuario(String id, String nombre, String correo, String telefono, String contraseña, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.conexiones= new ArrayList<Usuario>();
    }

    // Métodos concretos
    public void buscat() {
        System.out.println("Ejecutando método buscat()");
    }
    
    public void editLast() {
        System.out.println("Ejecutando método edit(last())");
    }
    
    public void edit(String s) {
        System.out.println("Ejecutando método edit(" + s + ")");
    }
    
    public void agregar() {
        System.out.println("Ejecutando método aqreger()");
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() { return telefono; }

    public List<Usuario> getConexiones() {
        return conexiones;
    }

    public void setConexiones(List<Usuario> conexiones) {
        this.conexiones = conexiones;
    }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "id='" + id + '\'' +
               ", nombre='" + nombre + '\'' +
               ", correo='" + correo + '\'' +
               ", contraseña='" + contraseña + '\'' +
               ", tipoUsuario='" + tipoUsuario + '\'' +
               '}';
    }
}