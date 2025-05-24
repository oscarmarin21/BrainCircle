package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.controlers.ControladorPrincipal;

import java.util.*;

public class Contenido<T extends Comparable<T>> implements Comparable<Contenido<T>> {
    private T idContenido;
    private T titulo;
    private T tema;
    private T tipo;
    private T autor;
    private T conte;
    private int likes = 0;
    private List<String> comentarios = new ArrayList<>();
    private Set<String> usuariosQueDieronLike = new HashSet<>();

    public Contenido(T id, T titulo, T tema, T tipo, T autor, T conte) {
        this.idContenido = id;
        this.titulo = titulo;
        this.tema = tema;
        this.tipo = tipo;
        this.autor = autor;
        this.conte = conte;
    }
    public Contenido(){
    }

    public T getId() {
        return idContenido;
    }

    public void setId(T id) {
        this.idContenido = id;
    }

    public T getTitulo() {
        return titulo;
    }

    public void setTitulo(T titulo) {
        this.titulo = titulo;
    }

    public T getTema() {
        return tema;
    }

    public void setTema(T tema) {
        this.tema = tema;
    }

    public T getTipo() {
        return tipo;
    }

    public void setTipo(T tipo) {
        this.tipo = tipo;
    }

    public T getAutor() {
        return autor;
    }

    public void setAutor(T autor) {
        this.autor = autor;
    }

    public T getConte() {
        return conte;
    }

    public void setConte(T conte) {
        this.conte = conte;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean registrarLike(String idUsuario, ControladorPrincipal controladorPrincipal) {
        System.out.println("Verificando si puede dar like el usuario: 111111111111sdfaser " + idUsuario);

        if (puedeDarLike(idUsuario)) {
            for (String otroUsuario : usuariosQueDieronLike) {
                controladorPrincipal.conectarUsuarios(
                        controladorPrincipal.obtenerUsuarioPorId(idUsuario),
                        controladorPrincipal.obtenerUsuarioPorId(otroUsuario)
                );
            }
            likes++;
            usuariosQueDieronLike.add(idUsuario);
            return true;
        }
        return false;
    }
    public boolean puedeDarLike(String idUsuario) {
        System.out.println("Verificando si puede dar like el usuario: " + idUsuario);
        System.out.println("Usuarios que ya dieron like: " + usuariosQueDieronLike);
        return !usuariosQueDieronLike.contains(idUsuario);
    }
    public Set<String> getUsuariosQueDieronLike() {
        return usuariosQueDieronLike;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public void agregarComentario(String comentario) {
        this.comentarios.add(comentario);
    }
    public T generaridUsuario() {
        this.idContenido = (T) UUID.randomUUID();
        return idContenido;
    }
    @Override
    public int compareTo(Contenido<T> otroContenido) {
        if (this.idContenido == null || otroContenido.idContenido == null) {
            throw new IllegalArgumentException("El idContenido no puede ser null");
        }
        return this.idContenido.compareTo(otroContenido.idContenido);
    }

}
