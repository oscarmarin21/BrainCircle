package co.edu.uniquindio.braincircle.Arbol;
import co.edu.uniquindio.braincircle.Nodo.NodoContenido;
import co.edu.uniquindio.braincircle.models.Contenido;

public class ArbolBinarioContenido<T extends Comparable<T>> {
    private NodoContenido<T> raiz;

    public void agregarContenido(Contenido<T> contenido) {
        raiz = agregarArbolContenido(raiz, contenido);
    }

    private NodoContenido<T> agregarArbolContenido(NodoContenido<T> nodo, Contenido<T> contenido) {
        if (nodo == null) return new NodoContenido<>(contenido);
        if (contenido.getTitulo().compareTo(nodo.getDato().getTitulo()) < 0)
            nodo.setIzquierda(agregarArbolContenido(nodo.getIzquierda(), contenido));
        else
            nodo.setDerecha(agregarArbolContenido(nodo.getDerecha(), contenido));
        return nodo;
    }
    public void cargarContenidos() {
        cargarArbolContenidos(raiz);
    }

    private void cargarArbolContenidos(NodoContenido<T> nodo) {
        if (nodo != null) {
            cargarArbolContenidos(nodo.getIzquierda());
            System.out.println(nodo.getDato());
            cargarArbolContenidos(nodo.getDerecha());
        }
    }
}
