package co.edu.uniquindio.braincircle.Nodo;

import co.edu.uniquindio.braincircle.models.GrupoEstudio;

public class NodoGrupo {
    public GrupoEstudio grupo;
    public NodoGrupo siguiente;

    public NodoGrupo(GrupoEstudio grupo) {
        this.grupo = grupo;
        this.siguiente = null;
    }

    public GrupoEstudio getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoEstudio grupo) {
        this.grupo = grupo;
    }

    public NodoGrupo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoGrupo siguiente) {
        this.siguiente = siguiente;
    }
}