package co.edu.uniquindio.braincircle.models;

public class Contenido<T>{
    private T id;
    private T titulo;
    private T tema;
    private T tipo;
    private T autor;

    public Contenido(T id, T titulo, T tema, T tipo, T autor) {
        this.id = id;
        this.titulo = titulo;
        this.tema = tema;
        this.tipo = tipo;
        this.autor = autor;
    }
    public Contenido(){
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
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

}
