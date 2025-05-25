package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests para la clase Usuario
 */
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("123", "Juan Pérez", "juan@test.com", "3001234567", "password123", TipoUsuario.ESTUDIANTE);
    }

    @Test
    @DisplayName("Test de creación de usuario")
    void testCreacionUsuario() {
        // Assert
        assertNotNull(usuario, "El usuario no debería ser null");
        assertEquals("123", usuario.getId(), "El ID debería coincidir");
        assertEquals("Juan Pérez", usuario.getNombre(), "El nombre debería coincidir");
        assertEquals("juan@test.com", usuario.getCorreo(), "El correo debería coincidir");
        assertEquals("3001234567", usuario.getTelefono(), "El teléfono debería coincidir");
        assertEquals("password123", usuario.getContraseña(), "La contraseña debería coincidir");
        assertEquals(TipoUsuario.ESTUDIANTE, usuario.getTipoUsuario(), "El tipo de usuario debería coincidir");
        assertNotNull(usuario.getConexiones(), "Las conexiones no deberían ser null");
        assertTrue(usuario.getConexiones().isEmpty(), "Las conexiones deberían estar vacías inicialmente");
    }

    @Test
    @DisplayName("Test de setters y getters")
    void testSettersYGetters() {
        // Act
        usuario.setId("456");
        usuario.setNombre("María García");
        usuario.setCorreo("maria@test.com");
        usuario.setTelefono("3009876543");
        usuario.setContraseña("newpassword");
        usuario.setTipoUsuario(TipoUsuario.ADMIN);

        // Assert
        assertEquals("456", usuario.getId());
        assertEquals("María García", usuario.getNombre());
        assertEquals("maria@test.com", usuario.getCorreo());
        assertEquals("3009876543", usuario.getTelefono());
        assertEquals("newpassword", usuario.getContraseña());
        assertEquals(TipoUsuario.ADMIN, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("Test de gestión de conexiones")
    void testGestionConexiones() {
        // Arrange
        Usuario usuario2 = new Usuario("456", "María", "maria@test.com", "3009876543", "pass", TipoUsuario.ESTUDIANTE);
        Usuario usuario3 = new Usuario("789", "Carlos", "carlos@test.com", "3001111111", "pass", TipoUsuario.ESTUDIANTE);
        
        List<Usuario> conexiones = new ArrayList<>();
        conexiones.add(usuario2);
        conexiones.add(usuario3);

        // Act
        usuario.setConexiones(conexiones);

        // Assert
        assertEquals(2, usuario.getConexiones().size(), "Debería tener 2 conexiones");
        assertTrue(usuario.getConexiones().contains(usuario2), "Debería contener a usuario2");
        assertTrue(usuario.getConexiones().contains(usuario3), "Debería contener a usuario3");
    }

    @Test
    @DisplayName("Test de métodos de comportamiento")
    void testMetodosComportamiento() {
        // Estos métodos solo imprimen, verificamos que no lancen excepciones
        assertDoesNotThrow(() -> usuario.buscat(), "buscat() no debería lanzar excepción");
        assertDoesNotThrow(() -> usuario.editLast(), "editLast() no debería lanzar excepción");
        assertDoesNotThrow(() -> usuario.edit("test"), "edit(String) no debería lanzar excepción");
        assertDoesNotThrow(() -> usuario.agregar(), "agregar() no debería lanzar excepción");
    }

    @Test
    @DisplayName("Test de toString")
    void testToString() {
        // Act
        String resultado = usuario.toString();

        // Assert
        assertNotNull(resultado, "toString() no debería retornar null");
        assertTrue(resultado.contains("123"), "Debería contener el ID");
        assertTrue(resultado.contains("Juan Pérez"), "Debería contener el nombre");
        assertTrue(resultado.contains("juan@test.com"), "Debería contener el correo");
        assertTrue(resultado.contains("ESTUDIANTE"), "Debería contener el tipo de usuario");
    }

    @Test
    @DisplayName("Test de validación de campos obligatorios")
    void testCamposObligatorios() {
        // Verificar que los campos no sean null después de la construcción
        assertNotNull(usuario.getId(), "ID no debería ser null");
        assertNotNull(usuario.getNombre(), "Nombre no debería ser null");
        assertNotNull(usuario.getCorreo(), "Correo no debería ser null");
        assertNotNull(usuario.getTelefono(), "Teléfono no debería ser null");
        assertNotNull(usuario.getContraseña(), "Contraseña no debería ser null");
        assertNotNull(usuario.getTipoUsuario(), "Tipo de usuario no debería ser null");
    }
} 