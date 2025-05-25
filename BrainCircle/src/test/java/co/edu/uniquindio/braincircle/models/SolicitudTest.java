package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la clase Solicitud
 */
class SolicitudTest {

    private Usuario propietario;
    private Solicitud solicitud;

    @BeforeEach
    void setUp() {
        propietario = new Usuario("123", "Juan Pérez", "juan@test.com", "3001234567", "password123", TipoUsuario.ESTUDIANTE);
        
        solicitud = new Solicitud.Builder()
                .id("SOL001")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.ALTA)
                .titulo("Problema con el sistema")
                .mensaje("No puedo acceder a mi cuenta")
                .build();
    }

    @Test
    @DisplayName("Test de creación de solicitud con Builder")
    void testCreacionSolicitudConBuilder() {
        // Assert
        assertNotNull(solicitud, "La solicitud no debería ser null");
        assertEquals("SOL001", solicitud.getId(), "El ID debería coincidir");
        assertEquals(propietario, solicitud.getPropietario(), "El propietario debería coincidir");
        assertEquals(NivelPrioridad.ALTA, solicitud.getNivelPrioridad(), "La prioridad debería coincidir");
        assertEquals("Problema con el sistema", solicitud.getTitulo(), "El título debería coincidir");
        assertEquals("No puedo acceder a mi cuenta", solicitud.getMensaje(), "El mensaje debería coincidir");
        assertNull(solicitud.getRespuesta(), "La respuesta debería ser null inicialmente");
    }

    @Test
    @DisplayName("Test de constructor tradicional")
    void testConstructorTradicional() {
        // Act
        Solicitud solicitudTradicional = new Solicitud("SOL002", propietario, NivelPrioridad.MEDIA, 
                                                       "Consulta general", "¿Cómo funciona el sistema?");

        // Assert
        assertNotNull(solicitudTradicional, "La solicitud no debería ser null");
        assertEquals("SOL002", solicitudTradicional.getId());
        assertEquals(propietario, solicitudTradicional.getPropietario());
        assertEquals(NivelPrioridad.MEDIA, solicitudTradicional.getNivelPrioridad());
        assertEquals("Consulta general", solicitudTradicional.getTitulo());
        assertEquals("¿Cómo funciona el sistema?", solicitudTradicional.getMensaje());
        assertNull(solicitudTradicional.getRespuesta(), "La respuesta debería ser null inicialmente");
    }

    @Test
    @DisplayName("Test de Builder con todos los campos")
    void testBuilderConTodosLosCampos() {
        // Act
        Solicitud solicitudCompleta = new Solicitud.Builder()
                .id("SOL003")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.BAJA)
                .titulo("Sugerencia de mejora")
                .mensaje("Sería útil agregar una función de búsqueda")
                .respuesta("Gracias por la sugerencia, la consideraremos")
                .build();

        // Assert
        assertEquals("SOL003", solicitudCompleta.getId());
        assertEquals(NivelPrioridad.BAJA, solicitudCompleta.getNivelPrioridad());
        assertEquals("Sugerencia de mejora", solicitudCompleta.getTitulo());
        assertEquals("Sería útil agregar una función de búsqueda", solicitudCompleta.getMensaje());
        assertEquals("Gracias por la sugerencia, la consideraremos", solicitudCompleta.getRespuesta());
    }

    @Test
    @DisplayName("Test de setters y getters")
    void testSettersYGetters() {
        // Arrange
        Usuario nuevoPropietario = new Usuario("456", "María García", "maria@test.com", 
                                             "3009876543", "maria123", TipoUsuario.ESTUDIANTE);

        // Act
        solicitud.setId("SOL004");
        solicitud.setPropietario(nuevoPropietario);
        solicitud.setNivelPrioridad(NivelPrioridad.MEDIA);
        solicitud.setTitulo("Nuevo título");
        solicitud.setMensaje("Nuevo mensaje");
        solicitud.setRespuesta("Nueva respuesta");

        // Assert
        assertEquals("SOL004", solicitud.getId());
        assertEquals(nuevoPropietario, solicitud.getPropietario());
        assertEquals(NivelPrioridad.MEDIA, solicitud.getNivelPrioridad());
        assertEquals("Nuevo título", solicitud.getTitulo());
        assertEquals("Nuevo mensaje", solicitud.getMensaje());
        assertEquals("Nueva respuesta", solicitud.getRespuesta());
    }

    @Test
    @DisplayName("Test de diferentes niveles de prioridad")
    void testDiferentesNivelesPrioridad() {
        // Act & Assert - ALTA
        Solicitud solicitudAlta = new Solicitud.Builder()
                .id("SOL_ALTA")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.ALTA)
                .titulo("Urgente")
                .mensaje("Problema crítico")
                .build();
        assertEquals(NivelPrioridad.ALTA, solicitudAlta.getNivelPrioridad());

        // Act & Assert - MEDIA
        Solicitud solicitudMedia = new Solicitud.Builder()
                .id("SOL_MEDIA")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.MEDIA)
                .titulo("Normal")
                .mensaje("Consulta estándar")
                .build();
        assertEquals(NivelPrioridad.MEDIA, solicitudMedia.getNivelPrioridad());

        // Act & Assert - BAJA
        Solicitud solicitudBaja = new Solicitud.Builder()
                .id("SOL_BAJA")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.BAJA)
                .titulo("Sugerencia")
                .mensaje("Mejora opcional")
                .build();
        assertEquals(NivelPrioridad.BAJA, solicitudBaja.getNivelPrioridad());
    }

    @Test
    @DisplayName("Test de toString")
    void testToString() {
        // Act
        String resultado = solicitud.toString();

        // Assert
        assertNotNull(resultado, "toString() no debería retornar null");
        assertTrue(resultado.contains("Solicitud"), "Debería contener 'Solicitud'");
        assertTrue(resultado.contains("SOL001"), "Debería contener el ID");
        assertTrue(resultado.contains("Juan Pérez"), "Debería contener el nombre del propietario");
        assertTrue(resultado.contains("ALTA"), "Debería contener la prioridad");
        assertTrue(resultado.contains("Problema con el sistema"), "Debería contener el título");
        assertTrue(resultado.contains("No puedo acceder a mi cuenta"), "Debería contener el mensaje");
    }

    @Test
    @DisplayName("Test de Builder con campos mínimos")
    void testBuilderCamposMinimos() {
        // Act
        Solicitud solicitudMinima = new Solicitud.Builder()
                .id("SOL_MIN")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.MEDIA)
                .titulo("Título mínimo")
                .mensaje("Mensaje mínimo")
                .build();

        // Assert
        assertNotNull(solicitudMinima, "La solicitud mínima no debería ser null");
        assertEquals("SOL_MIN", solicitudMinima.getId());
        assertEquals(propietario, solicitudMinima.getPropietario());
        assertEquals(NivelPrioridad.MEDIA, solicitudMinima.getNivelPrioridad());
        assertEquals("Título mínimo", solicitudMinima.getTitulo());
        assertEquals("Mensaje mínimo", solicitudMinima.getMensaje());
        assertNull(solicitudMinima.getRespuesta(), "La respuesta debería ser null");
    }

    @Test
    @DisplayName("Test de flujo completo de solicitud")
    void testFlujoCompletoSolicitud() {
        // Arrange - Crear solicitud sin respuesta
        Solicitud solicitudSinRespuesta = new Solicitud.Builder()
                .id("SOL_FLUJO")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.ALTA)
                .titulo("Solicitud de prueba")
                .mensaje("Mensaje de prueba")
                .build();

        // Assert - Estado inicial
        assertNull(solicitudSinRespuesta.getRespuesta(), "Inicialmente no debería tener respuesta");

        // Act - Agregar respuesta
        solicitudSinRespuesta.setRespuesta("Respuesta del administrador");

        // Assert - Estado final
        assertEquals("Respuesta del administrador", solicitudSinRespuesta.getRespuesta(), 
                    "Debería tener la respuesta asignada");
    }

    @Test
    @DisplayName("Test de validación de campos obligatorios")
    void testValidacionCamposObligatorios() {
        // Verificar que los campos no sean null después de la construcción
        assertNotNull(solicitud.getId(), "ID no debería ser null");
        assertNotNull(solicitud.getPropietario(), "Propietario no debería ser null");
        assertNotNull(solicitud.getNivelPrioridad(), "Nivel de prioridad no debería ser null");
        assertNotNull(solicitud.getTitulo(), "Título no debería ser null");
        assertNotNull(solicitud.getMensaje(), "Mensaje no debería ser null");
        // La respuesta puede ser null inicialmente
    }

    @Test
    @DisplayName("Test de Builder con encadenamiento de métodos")
    void testBuilderEncadenamientoMetodos() {
        // Act - Usar encadenamiento fluido
        Solicitud solicitudFluida = new Solicitud.Builder()
                .id("SOL_FLUIDA")
                .propietario(propietario)
                .nivelPrioridad(NivelPrioridad.BAJA)
                .titulo("Título fluido")
                .mensaje("Mensaje fluido")
                .respuesta("Respuesta fluida")
                .build();

        // Assert
        assertEquals("SOL_FLUIDA", solicitudFluida.getId());
        assertEquals("Título fluido", solicitudFluida.getTitulo());
        assertEquals("Mensaje fluido", solicitudFluida.getMensaje());
        assertEquals("Respuesta fluida", solicitudFluida.getRespuesta());
    }
} 