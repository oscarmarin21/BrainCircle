package co.edu.uniquindio.braincircle.Grafos;
import co.edu.uniquindio.braincircle.models.Usuario;
import java.util.*;

public class GrafoAfinidadUsuarios {

    private final Map<Usuario, Set<Usuario>> grafo;

    public GrafoAfinidadUsuarios() {
        this.grafo = new HashMap<>();
    }

    public void agregarUsuario(Usuario u) {
        grafo.putIfAbsent(u, new HashSet<>());
    }

    public void conectarUsuarios(Usuario u1, Usuario u2) {
        agregarUsuario(u1);
        agregarUsuario(u2);
        grafo.get(u1).add(u2);
        grafo.get(u2).add(u1);
    }

    public Set<Usuario> obtenerConexiones(Usuario u) {
        return grafo.getOrDefault(u, new HashSet<>());
    }

    public List<Usuario> sugerenciasDeAmistad(Usuario estudiante) {
        Set<Usuario> conexionesDirectas = obtenerConexiones(estudiante);
        Set<Usuario> sugerencias = new HashSet<>();

        for (Usuario amigo : conexionesDirectas) {
            for (Usuario amigoDeAmigo : obtenerConexiones(amigo)) {
                if (!amigoDeAmigo.equals(estudiante) && !conexionesDirectas.contains(amigoDeAmigo)) {
                    sugerencias.add(amigoDeAmigo);
                }
            }
        }
        return new ArrayList<>(sugerencias);
    }

    public List<Usuario> caminoMasCorto(Usuario origen, Usuario destino) {
        Map<Usuario, Usuario> predecesores = new HashMap<>();
        Set<Usuario> visitados = new HashSet<>();
        Queue<Usuario> cola = new LinkedList<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            Usuario actual = cola.poll();

            if (actual.equals(destino)) break;

            for (Usuario vecino : obtenerConexiones(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    predecesores.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        List<Usuario> camino = new ArrayList<>();
        Usuario actual = destino;

        while (actual != null && predecesores.containsKey(actual)) {
            camino.add(actual);
            actual = predecesores.get(actual);
        }

        if (actual != null && actual.equals(origen)) {
            camino.add(origen);
            Collections.reverse(camino);
            return camino;
        }

        return new ArrayList<>();
    }

    public Set<Usuario> obtenerTodosLosUsuarios() {
        return grafo.keySet();
    }

    public int contarConexiones(Usuario u) {
        return obtenerConexiones(u).size();
    }

    public boolean estanConectados(Usuario u1, Usuario u2) {
        return grafo.containsKey(u1) && grafo.get(u1).contains(u2);
    }
}
