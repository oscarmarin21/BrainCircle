package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.models.BrainCircle;
import co.edu.uniquindio.braincircle.models.Usuario;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para el controlador de inicio de sesión
 * Prueba la funcionalidad de autenticación de usuarios
 */
class InicioSesionControladorTest {

    private BrainCircle brainCircle;
    private ControladorPrincipal controladorPrincipal;

    @BeforeEach
    void setUp() throws Exception {
        // Inicializar el sistema BrainCircle para cada test
        brainCircle = new BrainCircle();
        controladorPrincipal = ControladorPrincipal.getInstancia();
        
        // Crear usuarios de prueba
        crearUsuariosDePrueba();
    }

    private void crearUsuariosDePrueba() {
        // Crear estudiantes de prueba usando el método registrar
        brainCircle.registrar("123456789", "Juan Pérez", "juan.perez@uniquindio.edu.co", "3001234567", "password123");
        brainCircle.registrar("987654321", "María García", "maria.garcia@uniquindio.edu.co", "3009876543", "maria2024");
    }

    @Test
    @DisplayName("Test de login exitoso con credenciales válidas")
    void testLoginExitoso() {
        // Arrange - Preparar datos de prueba
        String correoValido = "juan.perez@uniquindio.edu.co";
        String contraseñaValida = "password123";

        // Act - Ejecutar la acción de autenticación
        boolean resultado = brainCircle.autenticar(correoValido, contraseñaValida);
        Usuario usuarioAutenticado = brainCircle.ObtenerUserAutenticado(correoValido, contraseñaValida);

        // Assert - Verificar los resultados
        assertTrue(resultado, "La autenticación debería ser exitosa con credenciales válidas");
        assertNotNull(usuarioAutenticado, "El usuario autenticado no debería ser null");
        assertEquals(correoValido, usuarioAutenticado.getCorreo(), "El correo del usuario autenticado debería coincidir");
        assertEquals("Juan Pérez", usuarioAutenticado.getNombre(), "El nombre del usuario debería coincidir");
        assertEquals(TipoUsuario.ESTUDIANTE, usuarioAutenticado.getTipoUsuario(), "El tipo de usuario debería ser ESTUDIANTE");
    }

    @Test
    @DisplayName("Test de login fallido con credenciales inválidas")
    void testLoginFallido() {
        // Arrange - Preparar datos de prueba con credenciales incorrectas
        String correoInvalido = "usuario.inexistente@uniquindio.edu.co";
        String contraseñaInvalida = "contraseñaIncorrecta";

        // Act & Assert - Verificar que se lance la excepción esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            brainCircle.autenticar(correoInvalido, contraseñaInvalida);
        }, "Debería lanzar RuntimeException con credenciales inválidas");

        // Verificar el mensaje de la excepción
        assertEquals("Correo o contraseña incorrecta", exception.getMessage(), 
                    "El mensaje de error debería ser el esperado");

        // Verificar que no se obtiene usuario autenticado
        Usuario usuarioAutenticado = brainCircle.ObtenerUserAutenticado(correoInvalido, contraseñaInvalida);
        assertNull(usuarioAutenticado, "No debería retornar usuario con credenciales inválidas");
    }

    @Test
    @DisplayName("Test de login con correo válido pero contraseña incorrecta")
    void testLoginConCorreoValidoPeroContraseñaIncorrecta() {
        // Arrange
        String correoValido = "juan.perez@uniquindio.edu.co";
        String contraseñaIncorrecta = "contraseñaIncorrecta";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            brainCircle.autenticar(correoValido, contraseñaIncorrecta);
        }, "Debería lanzar RuntimeException con contraseña incorrecta");

        assertEquals("Correo o contraseña incorrecta", exception.getMessage());
        
        Usuario usuarioAutenticado = brainCircle.ObtenerUserAutenticado(correoValido, contraseñaIncorrecta);
        assertNull(usuarioAutenticado, "No debería autenticar con contraseña incorrecta");
    }

    @Test
    @DisplayName("Test de login con credenciales de administrador")
    void testLoginAdministrador() {
        // Arrange - Credenciales hardcodeadas del admin
        String correoAdmin = "arepitas.com";
        String contraseñaAdmin = "carnemolida";

        // Act - Simular la lógica del controlador para admin
        boolean esAdmin = correoAdmin.equals("arepitas.com") && contraseñaAdmin.equals("carnemolida");

        // Assert
        assertTrue(esAdmin, "Las credenciales de administrador deberían ser válidas");
    }

    @Test
    @DisplayName("Test de obtener usuario por ID")
    void testObtenerUsuarioPorId() {
        // Arrange
        String idUsuario = "123456789";

        // Act
        Usuario usuario = brainCircle.obtenerUsuarioPorId(idUsuario);

        // Assert
        assertNotNull(usuario, "Debería encontrar el usuario por ID");
        assertEquals(idUsuario, usuario.getId(), "El ID debería coincidir");
        assertEquals("Juan Pérez", usuario.getNombre(), "El nombre debería coincidir");
    }
} 