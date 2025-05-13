package co.edu.uniquindio.braincircle.Services;

import co.edu.uniquindio.braincircle.models.Contenido;

public interface ServicioBrainCircle<T> {
    public boolean autenticar(String correo, String clave);
    public boolean registrar(String id, String nombre, String correo, String telefono, String pass);
//    public void agregarContenido(T contenido);

}
