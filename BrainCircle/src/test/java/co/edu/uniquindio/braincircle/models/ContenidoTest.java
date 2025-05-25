package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.controlers.ControladorPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

/**
 * Tests para la clase Contenido
 */
class ContenidoTest {

    private Contenido<String> contenido;
    private ControladorPrincipal controladorPrincipal;

    @BeforeEach
    void setUp() throws Exception {
        contenido = new Contenido<>("1", "Título Test", "Tema Test", "ARCHIVO", "autor123", "contenido.pdf");
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @Test
    @DisplayName("Test de creación de contenido")
    void testCreacionContenido() {
        // Assert
        assertNotNull(contenido, "El contenido no debería ser null");
        assertEquals("1", contenido.getId(), "El ID debería coincidir");
        assertEquals("Título Test", contenido.getTitulo(), "El título debería coincidir");
        assertEquals("Tema Test", contenido.getTema(), "El tema debería coincidir");
        assertEquals("ARCHIVO", contenido.getTipo(), "El tipo debería coincidir");
        assertEquals("autor123", contenido.getAutor(), "El autor debería coincidir");
        assertEquals("contenido.pdf", contenido.getConte(), "El contenido debería coincidir");
        assertEquals(0, contenido.getLikes(), "Los likes deberían ser 0 inicialmente");
        assertNotNull(contenido.getComentarios(), "Los comentarios no deberían ser null");
        assertTrue(contenido.getComentarios().isEmpty(), "Los comentarios deberían estar vacíos inicialmente");
        assertNotNull(contenido.getUsuariosQueDieronLike(), "Los usuarios que dieron like no deberían ser null");
        assertTrue(contenido.getUsuariosQueDieronLike().isEmpty(), "Los usuarios que dieron like deberían estar vacíos");
    }

    @Test
    @DisplayName("Test de constructor vacío")
    void testConstructorVacio() {
        // Act
        Contenido<String> contenidoVacio = new Contenido<>();

        // Assert
        assertNotNull(contenidoVacio, "El contenido vacío no debería ser null");
        assertEquals(0, contenidoVacio.getLikes(), "Los likes deberían ser 0");
        assertNotNull(contenidoVacio.getComentarios(), "Los comentarios no deberían ser null");
        assertNotNull(contenidoVacio.getUsuariosQueDieronLike(), "Los usuarios que dieron like no deberían ser null");
    }

    @Test
    @DisplayName("Test de setters y getters")
    void testSettersYGetters() {
        // Act
        contenido.setId("2");
        contenido.setTitulo("Nuevo Título");
        contenido.setTema("Nuevo Tema");
        contenido.setTipo("IMAGEN");
        contenido.setAutor("nuevoautor456");
        contenido.setConte("imagen.jpg");
        contenido.setLikes(5);

        // Assert
        assertEquals("2", contenido.getId());
        assertEquals("Nuevo Título", contenido.getTitulo());
        assertEquals("Nuevo Tema", contenido.getTema());
        assertEquals("IMAGEN", contenido.getTipo());
        assertEquals("nuevoautor456", contenido.getAutor());
        assertEquals("imagen.jpg", contenido.getConte());
        assertEquals(5, contenido.getLikes());
    }

    @Test
    @DisplayName("Test de gestión de comentarios")
    void testGestionComentarios() {
        // Arrange
        String comentario1 = "Excelente contenido";
        String comentario2 = "Muy útil, gracias";
        String comentario3 = "¿Podrían agregar más ejemplos?";

        // Act
        contenido.agregarComentario(comentario1);
        contenido.agregarComentario(comentario2);
        contenido.agregarComentario(comentario3);

        // Assert
        assertEquals(3, contenido.getComentarios().size(), "Debería tener 3 comentarios");
        assertTrue(contenido.getComentarios().contains(comentario1), "Debería contener el primer comentario");
        assertTrue(contenido.getComentarios().contains(comentario2), "Debería contener el segundo comentario");
        assertTrue(contenido.getComentarios().contains(comentario3), "Debería contener el tercer comentario");
    }

    @Test
    @DisplayName("Test de sistema de likes - primer like")
    void testSistemaLikesPrimerLike() {
        // Arrange
        String usuario1 = "usuario123";
        
        // Act
        boolean resultado = contenido.registrarLike(usuario1, controladorPrincipal);

        // Assert
        assertTrue(resultado, "El primer like debería ser exitoso");
        assertEquals(1, contenido.getLikes(), "Debería tener 1 like");
        assertTrue(contenido.getUsuariosQueDieronLike().contains(usuario1), 
                  "Debería contener al usuario en la lista de likes");
        assertFalse(contenido.puedeDarLike(usuario1), 
                   "El usuario no debería poder dar like nuevamente");
    }

    @Test
    @DisplayName("Test de sistema de likes - like duplicado")
    void testSistemaLikesLikeDuplicado() {
        // Arrange
        String usuario1 = "usuario123";
        contenido.registrarLike(usuario1, controladorPrincipal); // Primer like

        // Act
        boolean resultado = contenido.registrarLike(usuario1, controladorPrincipal); // Segundo intento

        // Assert
        assertFalse(resultado, "El like duplicado no debería ser exitoso");
        assertEquals(1, contenido.getLikes(), "Debería seguir teniendo solo 1 like");
        assertEquals(1, contenido.getUsuariosQueDieronLike().size(), 
                    "Debería tener solo un usuario en la lista");
    }

    @Test
    @DisplayName("Test de sistema de likes - múltiples usuarios")
    void testSistemaLikesMultiplesUsuarios() {
        // Arrange
        String usuario1 = "usuario123";
        String usuario2 = "usuario456";
        String usuario3 = "usuario789";

        // Act
        boolean like1 = contenido.registrarLike(usuario1, controladorPrincipal);
        boolean like2 = contenido.registrarLike(usuario2, controladorPrincipal);
        boolean like3 = contenido.registrarLike(usuario3, controladorPrincipal);

        // Assert
        assertTrue(like1, "El primer like debería ser exitoso");
        assertTrue(like2, "El segundo like debería ser exitoso");
        assertTrue(like3, "El tercer like debería ser exitoso");
        assertEquals(3, contenido.getLikes(), "Debería tener 3 likes");
        assertEquals(3, contenido.getUsuariosQueDieronLike().size(), 
                    "Debería tener 3 usuarios en la lista");
        
        Set<String> usuariosLike = contenido.getUsuariosQueDieronLike();
        assertTrue(usuariosLike.contains(usuario1), "Debería contener usuario1");
        assertTrue(usuariosLike.contains(usuario2), "Debería contener usuario2");
        assertTrue(usuariosLike.contains(usuario3), "Debería contener usuario3");
    }

    @Test
    @DisplayName("Test de método puedeDarLike")
    void testPuedeDarLike() {
        // Arrange
        String usuario1 = "usuario123";
        String usuario2 = "usuario456";

        // Act & Assert - Usuario nuevo puede dar like
        assertTrue(contenido.puedeDarLike(usuario1), "Usuario nuevo debería poder dar like");
        assertTrue(contenido.puedeDarLike(usuario2), "Usuario nuevo debería poder dar like");

        // Dar like con usuario1
        contenido.registrarLike(usuario1, controladorPrincipal);

        // Act & Assert - Usuario que ya dio like no puede dar otro
        assertFalse(contenido.puedeDarLike(usuario1), "Usuario que ya dio like no debería poder dar otro");
        assertTrue(contenido.puedeDarLike(usuario2), "Usuario2 aún debería poder dar like");
    }

    @Test
    @DisplayName("Test de compareTo")
    void testCompareTo() {
        // Arrange
        Contenido<String> contenido1 = new Contenido<>("1", "Título1", "Tema1", "ARCHIVO", "autor1", "contenido1.pdf");
        Contenido<String> contenido2 = new Contenido<>("2", "Título2", "Tema2", "IMAGEN", "autor2", "imagen2.jpg");
        Contenido<String> contenido3 = new Contenido<>("1", "Título3", "Tema3", "VIDEO", "autor3", "video3.mp4");

        // Act & Assert
        assertTrue(contenido1.compareTo(contenido2) < 0, "contenido1 debería ser menor que contenido2");
        assertTrue(contenido2.compareTo(contenido1) > 0, "contenido2 debería ser mayor que contenido1");
        assertEquals(0, contenido1.compareTo(contenido3), "contenidos con mismo ID deberían ser iguales");
    }

    @Test
    @DisplayName("Test de compareTo con ID null")
    void testCompareToConIdNull() {
        // Arrange
        Contenido<String> contenidoSinId = new Contenido<>();
        Contenido<String> contenidoConId = new Contenido<>("1", "Título", "Tema", "ARCHIVO", "autor", "contenido.pdf");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoSinId.compareTo(contenidoConId);
        }, "Debería lanzar IllegalArgumentException cuando ID es null");

        assertThrows(IllegalArgumentException.class, () -> {
            contenidoConId.compareTo(contenidoSinId);
        }, "Debería lanzar IllegalArgumentException cuando el otro ID es null");
    }

    @Test
    @DisplayName("Test de setComentarios")
    void testSetComentarios() {
        // Arrange
        List<String> nuevosComentarios = List.of("Comentario 1", "Comentario 2", "Comentario 3");

        // Act
        contenido.setComentarios(nuevosComentarios);

        // Assert
        assertEquals(nuevosComentarios, contenido.getComentarios(), "Los comentarios deberían coincidir");
        assertEquals(3, contenido.getComentarios().size(), "Debería tener 3 comentarios");
    }
} 