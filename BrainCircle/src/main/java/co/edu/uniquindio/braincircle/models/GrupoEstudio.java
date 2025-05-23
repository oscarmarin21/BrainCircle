package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Nodo.NodoGrupo;

import java.util.ArrayList;
import java.util.List;

public class GrupoEstudio {
    private String idGrupo;
    private String nombre;
    private List<Estudiante> miembros;
    private String descripcion;
    private Materia materia;
    private List<String> mensajesChat;
    private List<String> contenidos;
    private static NodoGrupo cabeza = null;

    public GrupoEstudio(String id,String nombre, String descripcion, Materia materia) {
        this.idGrupo = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.materia = materia;
        this.miembros = new ArrayList<>();
        this.mensajesChat = new ArrayList<>();
        this.contenidos = new ArrayList<>();
    }

    public GrupoEstudio() {

    }

    public static void crearGrupo(String id,String nombreGrupo, String descripcion, Materia materia) {
        GrupoEstudio nuevoGrupo = new GrupoEstudio(id,nombreGrupo, descripcion, materia);
        NodoGrupo nuevoNodo = new NodoGrupo(nuevoGrupo);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;

        System.out.println("Grupo '" + nombreGrupo + "' creado con descripción: " + descripcion);
    }
    public static List<GrupoEstudio> cargarGrupos() {
        List<GrupoEstudio> listaGrupos = new ArrayList<>();
        NodoGrupo actual = GrupoEstudio.getCabeza();

        while (actual != null) {
            listaGrupos.add(actual.getGrupo());
            actual = actual.getSiguiente();
        }

        return listaGrupos;
    }
    public static GrupoEstudio buscarPorId(String id) {
        NodoGrupo actual = getCabeza();
        while (actual != null) {
            if (actual.getGrupo().getIdGrupo().equals(id)) {
                return actual.getGrupo();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public static boolean actualizarGrupo(String id, String nuevoNombre, String nuevaDescripcion, Materia nuevaMateria) {
        GrupoEstudio grupo = GrupoEstudio.buscarPorId(id);
        if (grupo != null) {
            grupo.setNombre(nuevoNombre);
            grupo.setDescripcion(nuevaDescripcion);
            grupo.setMateria(nuevaMateria);
            return true;
        }
        return false;
    }

    public static boolean eliminarGrupo(String idGrupo) {
        NodoGrupo actual = cabeza;
        NodoGrupo anterior = null;

        while (actual != null) {
            if (actual.getGrupo().getIdGrupo().equals(idGrupo)) {
                if (anterior == null) {
                    cabeza = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                System.out.println("Grupo eliminado: " + actual.getGrupo().getNombre());
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Estudiante> getMiembros() {
        return miembros;
    }

    public List<String> getMensajesChat() {
        return mensajesChat;
    }

    public List<String> getContenidos() {
        return contenidos;
    }

    public static NodoGrupo getCabeza() {
        return cabeza;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMiembros(List<Estudiante> miembros) {
        this.miembros = miembros;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public void setMensajesChat(List<String> mensajesChat) {
        this.mensajesChat = mensajesChat;
    }

    public void setContenidos(List<String> contenidos) {
        this.contenidos = contenidos;
    }

    public static void setCabeza(NodoGrupo cabeza) {
        GrupoEstudio.cabeza = cabeza;
    }

    @Override
    public String toString() {
        return "GrupoEstudio{" +
                "nombre='" + nombre + '\'' +
                ", miembros=" + miembros.size() +
                ", mensajesChat=" + mensajesChat.size() +
                ", contenidos=" + contenidos.size() +
                '}';
    }
}