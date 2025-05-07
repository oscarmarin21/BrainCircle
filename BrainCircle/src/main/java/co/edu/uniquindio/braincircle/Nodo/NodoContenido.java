package co.edu.uniquindio.braincircle.Nodo;

public class NodoContenido<T> {
    private T dato;
    private NodoContenido<T> izquierda;
    private NodoContenido<T> derecha;

    public NodoContenido(T dato) {
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoContenido<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoContenido<T> izquierda) {
        this.izquierda = izquierda;
    }

    public NodoContenido<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoContenido<T> derecha) {
        this.derecha = derecha;
    }
}
