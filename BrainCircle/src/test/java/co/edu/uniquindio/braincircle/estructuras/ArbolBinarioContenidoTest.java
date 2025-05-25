package co.edu.uniquindio.braincircle.estructuras;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.models.Contenido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests para la clase ArbolBinarioContenido
 */
class ArbolBinarioContenidoTest {

    private ArbolBinarioContenido<String> arbol;
    private Contenido<String> contenido1;
    private Contenido<String> contenido2;
    private Contenido<String> contenido3;

    @BeforeEach
    void setUp() {
        arbol = new ArbolBinarioContenido<>();
        
        // Crear contenidos de prueba con IDs que permitan probar el árbol binario
        contenido1 = new Contenido<>("2", "Título 2", "Tema 2", "ARCHIVO", "autor1", "archivo2.pdf");
        contenido2 = new Contenido<>("1", "Título 1", "Tema 1", "IMAGEN", "autor2", "imagen1.jpg");
        contenido3 = new Contenido<>("3", "Título 3", "Tema 3", "VIDEO", "autor3", "video3.mp4");
    }

    // ========== TESTS DE AGREGAR CONTENIDO ==========

    @Test
    @DisplayName("Test de agregar contenido al árbol vacío")
    void testAgregarContenidoArbolVacio() {
        // Act
        arbol.agregarContenido(contenido1);

        // Assert
        Contenido<String> contenidoObtenido = arbol.obtenerContenidoPorId("2");
        assertNotNull(contenidoObtenido, "El contenido debería existir en el árbol");
        assertEquals("Título 2", contenidoObtenido.getTitulo(), "El título debería coincidir");
    }

    @Test
    @DisplayName("Test de agregar múltiples contenidos")
    void testAgregarMultiplesContenidos() {
        // Act
        arbol.agregarContenido(contenido1); // ID: 2 (raíz)
        arbol.agregarContenido(contenido2); // ID: 1 (izquierda)
        arbol.agregarContenido(contenido3); // ID: 3 (derecha)

        // Assert
        assertNotNull(arbol.obtenerContenidoPorId("1"), "Contenido 1 debería existir");
        assertNotNull(arbol.obtenerContenidoPorId("2"), "Contenido 2 debería existir");
        assertNotNull(arbol.obtenerContenidoPorId("3"), "Contenido 3 debería existir");
    }

    @Test
    @DisplayName("Test de estructura del árbol binario")
    void testEstructuraArbolBinario() {
        // Arrange - Agregar contenidos en orden específico
        arbol.agregarContenido(contenido1); // ID: 2 (raíz)
        arbol.agregarContenido(contenido2); // ID: 1 (izquierda de 2)
        arbol.agregarContenido(contenido3); // ID: 3 (derecha de 2)

        // Act - Cargar contenidos (debería estar en orden in-order: 1, 2, 3)
        List<Contenido<String>> contenidos = arbol.cargarContenidos();

        // Assert
        assertEquals(3, contenidos.size(), "Debería tener 3 contenidos");
        assertEquals("1", contenidos.get(0).getId(), "Primer contenido debería ser ID 1");
        assertEquals("2", contenidos.get(1).getId(), "Segundo contenido debería ser ID 2");
        assertEquals("3", contenidos.get(2).getId(), "Tercer contenido debería ser ID 3");
    }

    // ========== TESTS DE BÚSQUEDA ==========

    @Test
    @DisplayName("Test de obtener contenido por ID existente")
    void testObtenerContenidoPorIdExistente() {
        // Arrange
        arbol.agregarContenido(contenido1);
        arbol.agregarContenido(contenido2);
        arbol.agregarContenido(contenido3);

        // Act
        Contenido<String> contenidoEncontrado = arbol.obtenerContenidoPorId("2");

        // Assert
        assertNotNull(contenidoEncontrado, "Debería encontrar el contenido");
        assertEquals("2", contenidoEncontrado.getId(), "El ID debería coincidir");
        assertEquals("Título 2", contenidoEncontrado.getTitulo(), "El título debería coincidir");
    }

    @Test
    @DisplayName("Test de obtener contenido por ID inexistente")
    void testObtenerContenidoPorIdInexistente() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        Contenido<String> contenidoNoEncontrado = arbol.obtenerContenidoPorId("999");

        // Assert
        assertNull(contenidoNoEncontrado, "No debería encontrar contenido inexistente");
    }

    @Test
    @DisplayName("Test de búsqueda en árbol vacío")
    void testBusquedaArbolVacio() {
        // Act
        Contenido<String> contenido = arbol.obtenerContenidoPorId("1");

        // Assert
        assertNull(contenido, "No debería encontrar contenido en árbol vacío");
    }

    // ========== TESTS DE ACTUALIZACIÓN ==========

    @Test
    @DisplayName("Test de actualizar contenido existente")
    void testActualizarContenidoExistente() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        boolean resultado = arbol.actualizarContenido("2", "Título Actualizado", "Tema Actualizado", 
                                                     "IMAGEN", "autor_actualizado", "imagen_actualizada.jpg");

        // Assert
        assertTrue(resultado, "La actualización debería ser exitosa");
        
        Contenido<String> contenidoActualizado = arbol.obtenerContenidoPorId("2");
        assertEquals("Título Actualizado", contenidoActualizado.getTitulo(), "El título debería haberse actualizado");
        assertEquals("Tema Actualizado", contenidoActualizado.getTema(), "El tema debería haberse actualizado");
        assertEquals("IMAGEN", contenidoActualizado.getTipo(), "El tipo debería haberse actualizado");
        assertEquals("autor_actualizado", contenidoActualizado.getAutor(), "El autor debería haberse actualizado");
        assertEquals("imagen_actualizada.jpg", contenidoActualizado.getConte(), "El contenido debería haberse actualizado");
    }

    @Test
    @DisplayName("Test de actualizar contenido inexistente")
    void testActualizarContenidoInexistente() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        boolean resultado = arbol.actualizarContenido("999", "Título", "Tema", "ARCHIVO", "autor", "archivo.pdf");

        // Assert
        assertFalse(resultado, "La actualización de contenido inexistente debería fallar");
    }

    @Test
    @DisplayName("Test de actualizar en árbol vacío")
    void testActualizarArbolVacio() {
        // Act
        boolean resultado = arbol.actualizarContenido("1", "Título", "Tema", "ARCHIVO", "autor", "archivo.pdf");

        // Assert
        assertFalse(resultado, "La actualización en árbol vacío debería fallar");
    }

    // ========== TESTS DE ELIMINACIÓN ==========

    @Test
    @DisplayName("Test de eliminar contenido sin hijos")
    void testEliminarContenidoSinHijos() {
        // Arrange
        arbol.agregarContenido(contenido1); // ID: 2 (raíz)
        arbol.agregarContenido(contenido2); // ID: 1 (hoja izquierda)

        // Act
        boolean resultado = arbol.eliminarContenidoPorId("1");

        // Assert
        assertTrue(resultado, "La eliminación debería ser exitosa");
        assertNull(arbol.obtenerContenidoPorId("1"), "El contenido eliminado no debería existir");
        assertNotNull(arbol.obtenerContenidoPorId("2"), "El contenido no eliminado debería seguir existiendo");
    }

    @Test
    @DisplayName("Test de eliminar contenido con un hijo")
    void testEliminarContenidoConUnHijo() {
        // Arrange
        arbol.agregarContenido(contenido1); // ID: 2 (raíz)
        arbol.agregarContenido(contenido2); // ID: 1 (izquierda)
        
        Contenido<String> contenido0_5 = new Contenido<>("0.5", "Título 0.5", "Tema 0.5", "ARCHIVO", "autor", "archivo.pdf");
        arbol.agregarContenido(contenido0_5); // ID: 0.5 (hijo izquierdo de 1)

        // Act - Eliminar nodo con un hijo
        boolean resultado = arbol.eliminarContenidoPorId("1");

        // Assert
        assertTrue(resultado, "La eliminación debería ser exitosa");
        assertNull(arbol.obtenerContenidoPorId("1"), "El contenido eliminado no debería existir");
        assertNotNull(arbol.obtenerContenidoPorId("0.5"), "El hijo del nodo eliminado debería seguir existiendo");
        assertNotNull(arbol.obtenerContenidoPorId("2"), "La raíz debería seguir existiendo");
    }

    @Test
    @DisplayName("Test de eliminar contenido con dos hijos")
    void testEliminarContenidoConDosHijos() {
        // Arrange
        arbol.agregarContenido(contenido1); // ID: 2 (raíz)
        arbol.agregarContenido(contenido2); // ID: 1 (izquierda)
        arbol.agregarContenido(contenido3); // ID: 3 (derecha)

        // Act - Eliminar la raíz que tiene dos hijos
        boolean resultado = arbol.eliminarContenidoPorId("2");

        // Assert
        assertTrue(resultado, "La eliminación debería ser exitosa");
        assertNull(arbol.obtenerContenidoPorId("2"), "El contenido eliminado no debería existir");
        assertNotNull(arbol.obtenerContenidoPorId("1"), "El hijo izquierdo debería seguir existiendo");
        assertNotNull(arbol.obtenerContenidoPorId("3"), "El hijo derecho debería seguir existiendo");
        
        // Verificar que la estructura del árbol sigue siendo válida
        List<Contenido<String>> contenidos = arbol.cargarContenidos();
        assertEquals(2, contenidos.size(), "Debería tener 2 contenidos después de la eliminación");
    }

    @Test
    @DisplayName("Test de eliminar contenido inexistente")
    void testEliminarContenidoInexistente() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        boolean resultado = arbol.eliminarContenidoPorId("999");

        // Assert
        assertFalse(resultado, "La eliminación de contenido inexistente debería fallar");
        assertNotNull(arbol.obtenerContenidoPorId("2"), "El contenido existente no debería verse afectado");
    }

    @Test
    @DisplayName("Test de eliminar de árbol vacío")
    void testEliminarArbolVacio() {
        // Act
        boolean resultado = arbol.eliminarContenidoPorId("1");

        // Assert
        assertFalse(resultado, "La eliminación en árbol vacío debería fallar");
    }

    // ========== TESTS DE CARGAR CONTENIDOS ==========

    @Test
    @DisplayName("Test de cargar contenidos en orden")
    void testCargarContenidosEnOrden() {
        // Arrange - Agregar contenidos en orden no secuencial
        arbol.agregarContenido(contenido3); // ID: 3
        arbol.agregarContenido(contenido1); // ID: 2
        arbol.agregarContenido(contenido2); // ID: 1

        // Act
        List<Contenido<String>> contenidos = arbol.cargarContenidos();

        // Assert - Deberían estar ordenados por ID (in-order traversal)
        assertEquals(3, contenidos.size(), "Debería tener 3 contenidos");
        assertEquals("1", contenidos.get(0).getId(), "Primer contenido debería ser ID 1");
        assertEquals("2", contenidos.get(1).getId(), "Segundo contenido debería ser ID 2");
        assertEquals("3", contenidos.get(2).getId(), "Tercer contenido debería ser ID 3");
    }

    @Test
    @DisplayName("Test de cargar contenidos de árbol vacío")
    void testCargarContenidosArbolVacio() {
        // Act
        List<Contenido<String>> contenidos = arbol.cargarContenidos();

        // Assert
        assertNotNull(contenidos, "La lista no debería ser null");
        assertTrue(contenidos.isEmpty(), "La lista debería estar vacía");
    }

    // ========== TESTS DE LIKES Y COMENTARIOS ==========

    @Test
    @DisplayName("Test de incrementar like por ID")
    void testIncrementarLikePorId() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        boolean resultado = arbol.incrementarLikePorId("2", "usuario123");

        // Assert
        assertTrue(resultado, "Incrementar like debería ser exitoso");
        
        Contenido<String> contenido = arbol.obtenerContenidoPorId("2");
        assertEquals(1, contenido.getLikes(), "Debería tener 1 like");
        assertTrue(contenido.getUsuariosQueDieronLike().contains("usuario123"), 
                  "Debería contener al usuario en la lista de likes");
    }

    @Test
    @DisplayName("Test de incrementar like en contenido inexistente")
    void testIncrementarLikeContenidoInexistente() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        boolean resultado = arbol.incrementarLikePorId("999", "usuario123");

        // Assert
        assertFalse(resultado, "Incrementar like en contenido inexistente debería fallar");
    }

    @Test
    @DisplayName("Test de agregar comentario por ID")
    void testAgregarComentarioPorId() {
        // Arrange
        arbol.agregarContenido(contenido1);
        String comentario = "Excelente contenido";

        // Act
        boolean resultado = arbol.agregarComentarioPorId("2", comentario);

        // Assert
        assertTrue(resultado, "Agregar comentario debería ser exitoso");
        
        Contenido<String> contenido = arbol.obtenerContenidoPorId("2");
        assertEquals(1, contenido.getComentarios().size(), "Debería tener 1 comentario");
        assertTrue(contenido.getComentarios().contains(comentario), "Debería contener el comentario");
    }

    @Test
    @DisplayName("Test de agregar comentario en contenido inexistente")
    void testAgregarComentarioContenidoInexistente() {
        // Arrange
        arbol.agregarContenido(contenido1);

        // Act
        boolean resultado = arbol.agregarComentarioPorId("999", "Comentario");

        // Assert
        assertFalse(resultado, "Agregar comentario en contenido inexistente debería fallar");
    }

    // ========== TESTS DE INTEGRIDAD ==========

    @Test
    @DisplayName("Test de integridad después de múltiples operaciones")
    void testIntegridadMultiplesOperaciones() {
        // Arrange & Act - Realizar múltiples operaciones
        arbol.agregarContenido(contenido1);
        arbol.agregarContenido(contenido2);
        arbol.agregarContenido(contenido3);
        
        arbol.actualizarContenido("2", "Título Nuevo", "Tema Nuevo", "IMAGEN", "autor_nuevo", "imagen.jpg");
        arbol.incrementarLikePorId("1", "usuario1");
        arbol.agregarComentarioPorId("3", "Comentario de prueba");
        arbol.eliminarContenidoPorId("2");

        // Assert - Verificar estado final
        List<Contenido<String>> contenidos = arbol.cargarContenidos();
        assertEquals(2, contenidos.size(), "Debería tener 2 contenidos después de todas las operaciones");
        
        assertNull(arbol.obtenerContenidoPorId("2"), "Contenido eliminado no debería existir");
        assertNotNull(arbol.obtenerContenidoPorId("1"), "Contenido 1 debería seguir existiendo");
        assertNotNull(arbol.obtenerContenidoPorId("3"), "Contenido 3 debería seguir existiendo");
        
        // Verificar que las modificaciones se mantuvieron
        Contenido<String> contenido1Final = arbol.obtenerContenidoPorId("1");
        assertEquals(1, contenido1Final.getLikes(), "Los likes deberían mantenerse");
        
        Contenido<String> contenido3Final = arbol.obtenerContenidoPorId("3");
        assertEquals(1, contenido3Final.getComentarios().size(), "Los comentarios deberían mantenerse");
    }
} 