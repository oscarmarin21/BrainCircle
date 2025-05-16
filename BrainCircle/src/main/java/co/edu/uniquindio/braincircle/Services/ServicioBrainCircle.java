package co.edu.uniquindio.braincircle.Services;

import co.edu.uniquindio.braincircle.models.Contenido;

import java.util.List;

public interface ServicioBrainCircle<T extends Comparable<T>> {
    public boolean autenticar(String correo, String clave);
    public boolean registrar(String id, String nombre, String correo, String telefono, String pass);
    public void agregarContenido(Contenido<T> contenido);
    public boolean actualizarContenido(T idContenido, T nuevoTitulo, T nuevoTema, T nuevoTipo,T nuevoAutor) ;
    public boolean eliminarContenidoPorId(T idContenido);
    public List<Contenido<T>> cargarContenidos();
    public Contenido<T> obtenerContenidoPorId(T idContenido);
}
