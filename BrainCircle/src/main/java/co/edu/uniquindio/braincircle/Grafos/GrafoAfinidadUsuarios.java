package co.edu.uniquindio.braincircle.Grafos;
import co.edu.uniquindio.braincircle.models.Usuario;
import java.util.*;

public class GrafoAfinidadUsuarios {

    private final Map<Usuario, Set<Usuario>> grafo;
    private final Map<String, List<String>> conversaciones;
    public GrafoAfinidadUsuarios() {
        this.grafo = new HashMap<>();
        this.conversaciones = new HashMap<>();
    }

    public void agregarUsuario(Usuario u) {
        grafo.putIfAbsent(u, new HashSet<>());
    }

    public void conectarUsuarios(Usuario u1, Usuario u2) {
        agregarUsuario(u1);
        agregarUsuario(u2);
        grafo.get(u1).add(u2);
        grafo.get(u2).add(u1);
        System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIII"+u1+ ""+ u2+"");
    }

    public List<Usuario> obtenerConexiones(Usuario u) {
        return u.getConexiones();
    }

    public List<Usuario> sugerenciasDeAmistad(Usuario estudiante) {
        List<Usuario> conexionesDirectas = obtenerConexiones(estudiante);
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
    private String getChatKey(String id1, String id2) {
        return id1.compareTo(id2) < 0 ? id1 + "|" + id2 : id2 + "|" + id1;
    }

    public void enviarMensaje(Usuario emisor, Usuario receptor, String contenido) {
        if (!estanConectados(emisor, receptor)) {
            throw new IllegalArgumentException("Los usuarios no están conectados.");
        }
        String clave = getChatKey(emisor.getId(), receptor.getId());
        conversaciones.putIfAbsent(clave, new ArrayList<>());
        conversaciones.get(clave).add(emisor.getNombre() + ": " + contenido);
    }

    public List<String> obtenerConversacion(Usuario u1, Usuario u2) {
        return conversaciones.getOrDefault(getChatKey(u1.getId(), u2.getId()), new ArrayList<>());
    }
}
