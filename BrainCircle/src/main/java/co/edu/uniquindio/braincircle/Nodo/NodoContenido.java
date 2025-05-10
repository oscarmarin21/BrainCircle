package co.edu.uniquindio.braincircle.Nodo;

import co.edu.uniquindio.braincircle.models.Contenido;

public class NodoContenido<T extends Comparable<T>> {
    private Contenido<T> dato;
    private NodoContenido<T> izquierda;
    private NodoContenido<T> derecha;

    public NodoContenido(Contenido<T> dato) {
        this.dato = dato;
    }

    public Contenido<T> getDato() {
        return dato;
    }

    public void setDato(Contenido<T> dato) {
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
