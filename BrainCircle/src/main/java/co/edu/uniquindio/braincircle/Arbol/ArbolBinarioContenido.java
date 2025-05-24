package co.edu.uniquindio.braincircle.Arbol;
import co.edu.uniquindio.braincircle.Nodo.NodoContenido;
import co.edu.uniquindio.braincircle.controlers.ControladorPrincipal;
import co.edu.uniquindio.braincircle.models.Contenido;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioContenido<T extends Comparable<T>> {
    private NodoContenido<T> raiz;

    //AGREGAR CONTENIDO
    public void agregarContenido(Contenido<T> contenido) {
        raiz = agregarArbolContenido(raiz, contenido);
    }

    private NodoContenido<T> agregarArbolContenido(NodoContenido<T> nodo, Contenido<T> contenido) {
        if (nodo == null) return new NodoContenido<>(contenido);
        if (contenido.getId().compareTo(nodo.getDato().getId()) < 0) {
            nodo.setIzquierda(agregarArbolContenido(nodo.getIzquierda(), contenido));
        } else {
            nodo.setDerecha(agregarArbolContenido(nodo.getDerecha(), contenido));
        }
        return nodo;
    }


    //ACTUALIZAR CONTENIDO
    public boolean actualizarContenido(T idContenido, T nuevoTitulo, T nuevoTema, T nuevoTipo, T nuevoAutor, T conte) {
        return actualizarNodoPorIdArbol(raiz, idContenido, nuevoTitulo, nuevoTema, nuevoTipo, nuevoAutor, conte);
    }
    private boolean actualizarNodoPorIdArbol(NodoContenido<T> nodo, T idContenido, T nuevoTitulo, T nuevoTema, T nuevoTipo, T nuevoAutor, T conte) {
        if (nodo == null) return false;
        int comparacion = idContenido.compareTo(nodo.getDato().getId());

        if (comparacion == 0) {
            nodo.getDato().setTitulo(nuevoTitulo);
            nodo.getDato().setTema(nuevoTema);
            nodo.getDato().setTipo(nuevoTipo);
            nodo.getDato().setAutor(nuevoAutor);
            nodo.getDato().setConte(conte);
            return true;
        } else if (comparacion < 0) {
            return actualizarNodoPorIdArbol(nodo.getIzquierda(), idContenido, nuevoTitulo, nuevoTema, nuevoTipo, nuevoAutor, conte);
        } else {
            return actualizarNodoPorIdArbol(nodo.getDerecha(), idContenido, nuevoTitulo, nuevoTema, nuevoTipo, nuevoAutor,conte);
        }
    }

    //ELIMINAR CONTENIDO SEGUN LOS HIJOS
    public boolean eliminarContenidoPorId(T idContenido) {
        if (raiz == null) return false;
        boolean[] eliminado = {false};
        raiz = eliminarNodoPorIdArbol(raiz, idContenido, eliminado);
        return eliminado[0];
    }
    private NodoContenido<T> eliminarNodoPorIdArbol(NodoContenido<T> nodo, T idContenido, boolean[] eliminado) {
        if (nodo == null) return null;

        int comparacion = idContenido.compareTo(nodo.getDato().getId());

        if (comparacion < 0) {
            nodo.setIzquierda(eliminarNodoPorIdArbol(nodo.getIzquierda(), idContenido, eliminado));
        } else if (comparacion > 0) {
            nodo.setDerecha(eliminarNodoPorIdArbol(nodo.getDerecha(), idContenido, eliminado));
        } else {
            // Nodo encontrado
            eliminado[0] = true;

            // Caso 1: sin hijos
            if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
                return null;
            }
            // Caso 2: un solo hijo
            else if (nodo.getIzquierda() == null) {
                return nodo.getDerecha();
            } else if (nodo.getDerecha() == null) {
                return nodo.getIzquierda();
            }
            // Caso 3: dos hijos
            else {
                NodoContenido<T> sucesor = encontrarMinimo(nodo.getDerecha());
                nodo.setDato(sucesor.getDato());
                nodo.setDerecha(eliminarNodoPorIdArbol(nodo.getDerecha(), sucesor.getDato().getId(), eliminado));
            }
        }
        return nodo;
    }
    private NodoContenido<T> encontrarMinimo(NodoContenido<T> nodo) {
        while (nodo.getIzquierda() != null) {
            nodo = nodo.getIzquierda();
        }
        return nodo;
    }

    //CARGAR CONTENIDO GENERAL
    public List<Contenido<T>> cargarContenidos() {
        List<Contenido<T>> lista = new ArrayList<>();
        cargarArbolContenidos(raiz, lista);
        return lista;
    }

    private void cargarArbolContenidos(NodoContenido<T> nodo, List<Contenido<T>> lista) {
        if (nodo != null) {
            cargarArbolContenidos(nodo.getIzquierda(), lista);
            lista.add(nodo.getDato());
            cargarArbolContenidos(nodo.getDerecha(), lista);
        }
    }

    //CARGAR CONTENIDO POR ID
    public Contenido<T> obtenerContenidoPorId(T idContenido) {
        return buscarContenidoPorIdArbol(raiz, idContenido);
    }
    private Contenido<T> buscarContenidoPorIdArbol(NodoContenido<T> nodo, T idContenido) {
        if (nodo == null) return null;

        int comparacion = idContenido.compareTo(nodo.getDato().getId());

        if (comparacion == 0) {
            return nodo.getDato();
        } else if (comparacion < 0) {
            return buscarContenidoPorIdArbol(nodo.getIzquierda(), idContenido);
        } else {
            return buscarContenidoPorIdArbol(nodo.getDerecha(), idContenido);
        }
    }

    public boolean incrementarLikePorId(T idContenido, String idUsuario) {
        Contenido<T> contenido = buscarContenidoPorIdArbol(raiz, idContenido);
        if (contenido != null) {
            boolean exito = contenido.registrarLike(idUsuario, ControladorPrincipal.getInstancia());
            if (exito) {
                return true;
            }
        }
        return false;
    }

    public boolean agregarComentarioPorId(T idContenido, String comentario) {
        Contenido<T> contenido = obtenerContenidoPorId(idContenido);
        if (contenido != null) {
            contenido.agregarComentario(comentario);
            return true;
        }
        return false;
    }
}
