package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests para la clase Estudiante
 */
class EstudianteTest {

    private Estudiante estudiante;

    @BeforeEach
    void setUp() {
        estudiante = new Estudiante.Builder()
                .setId("123")
                .setNombre("Juan Pérez")
                .setCorreo("juan@test.com")
                .setTelefono("3001234567")
                .setContraseña("password123")
                .setTipoUsuario(TipoUsuario.ESTUDIANTE)
                .setEstichayuda("Ayuda disponible")
                .build();
    }

    @Test
    @DisplayName("Test de creación de estudiante con Builder")
    void testCreacionEstudianteConBuilder() {
        // Assert
        assertNotNull(estudiante, "El estudiante no debería ser null");
        assertEquals("123", estudiante.getId(), "El ID debería coincidir");
        assertEquals("Juan Pérez", estudiante.getNombre(), "El nombre debería coincidir");
        assertEquals("juan@test.com", estudiante.getCorreo(), "El correo debería coincidir");
        assertEquals("3001234567", estudiante.getTelefono(), "El teléfono debería coincidir");
        assertEquals("password123", estudiante.getContraseña(), "La contraseña debería coincidir");
        assertEquals(TipoUsuario.ESTUDIANTE, estudiante.getTipoUsuario(), "El tipo debería ser ESTUDIANTE");
        assertEquals("Ayuda disponible", estudiante.getEstichayuda(), "La ayuda debería coincidir");
        
        // Verificar que las listas se inicializan correctamente
        assertNotNull(estudiante.getHistoriasConsenso(), "Historias consenso no debería ser null");
        assertNotNull(estudiante.getConsensios(), "Consensios no debería ser null");
        assertNotNull(estudiante.getGrupos(), "Grupos no debería ser null");
        assertNotNull(estudiante.getMensajes(), "Mensajes no debería ser null");
        assertNotNull(estudiante.getPracticas(), "Prácticas no debería ser null");
        
        assertTrue(estudiante.getHistoriasConsenso().isEmpty(), "Historias consenso debería estar vacía");
        assertTrue(estudiante.getConsensios().isEmpty(), "Consensios debería estar vacía");
        assertTrue(estudiante.getGrupos().isEmpty(), "Grupos debería estar vacía");
        assertTrue(estudiante.getMensajes().isEmpty(), "Mensajes debería estar vacía");
        assertTrue(estudiante.getPracticas().isEmpty(), "Prácticas debería estar vacía");
    }

    @Test
    @DisplayName("Test de Builder con listas personalizadas")
    void testBuilderConListasPersonalizadas() {
        // Arrange
        List<String> historias = List.of("Historia1", "Historia2");
        List<String> consensios = List.of("Consensio1");
        List<String> grupos = List.of("Grupo1", "Grupo2", "Grupo3");
        List<String> mensajes = List.of("Mensaje1");
        List<String> practicas = List.of("Practica1", "Practica2");

        // Act
        Estudiante estudianteConListas = new Estudiante.Builder()
                .setId("456")
                .setNombre("María García")
                .setCorreo("maria@test.com")
                .setTelefono("3009876543")
                .setContraseña("maria123")
                .setTipoUsuario(TipoUsuario.ESTUDIANTE)
                .setHistoriasConsenso(historias)
                .setConsensios(consensios)
                .setGrupos(grupos)
                .setMensajes(mensajes)
                .setPracticas(practicas)
                .setEstichayuda("Ayuda especializada")
                .build();

        // Assert
        assertEquals(2, estudianteConListas.getHistoriasConsenso().size());
        assertEquals(1, estudianteConListas.getConsensios().size());
        assertEquals(3, estudianteConListas.getGrupos().size());
        assertEquals(1, estudianteConListas.getMensajes().size());
        assertEquals(2, estudianteConListas.getPracticas().size());
        assertEquals("Ayuda especializada", estudianteConListas.getEstichayuda());
    }

    @Test
    @DisplayName("Test de métodos de valoración")
    void testMetodosValoracion() {
        // Estos métodos solo imprimen, verificamos que no lancen excepciones
        assertDoesNotThrow(() -> estudiante.valorarConsensato("Excelente"), 
                          "valorarConsensato() no debería lanzar excepción");
        assertDoesNotThrow(() -> estudiante.valorarAyudo("Muy bueno"), 
                          "valorarAyudo() no debería lanzar excepción");
        assertDoesNotThrow(() -> estudiante.valorSuperficial("Bueno"), 
                          "valorSuperficial() no debería lanzar excepción");
    }

    @Test
    @DisplayName("Test de envío de mensajes")
    void testEnvioMensajes() {
        // Arrange
        String mensaje1 = "Hola, ¿cómo están?";
        String mensaje2 = "¿Alguien puede ayudarme con matemáticas?";
        
        int mensajesIniciales = estudiante.getMensajes().size();

        // Act
        estudiante.enviarMensaje(mensaje1);
        estudiante.enviarMensaje(mensaje2);

        // Assert
        assertEquals(mensajesIniciales + 2, estudiante.getMensajes().size(), 
                    "Debería tener 2 mensajes más");
        assertTrue(estudiante.getMensajes().contains(mensaje1), 
                  "Debería contener el primer mensaje");
        assertTrue(estudiante.getMensajes().contains(mensaje2), 
                  "Debería contener el segundo mensaje");
    }

    @Test
    @DisplayName("Test de setters y getters específicos de Estudiante")
    void testSettersYGettersEspecificos() {
        // Arrange
        List<String> nuevasHistorias = List.of("Nueva historia");
        List<String> nuevosConsensios = List.of("Nuevo consensio");
        List<String> nuevosGrupos = List.of("Nuevo grupo");
        List<String> nuevosMensajes = List.of("Nuevo mensaje");
        List<String> nuevasPracticas = List.of("Nueva práctica");

        // Act
        estudiante.setHistoriasConsenso(nuevasHistorias);
        estudiante.setConsensios(nuevosConsensios);
        estudiante.setGrupos(nuevosGrupos);
        estudiante.setMensajes(nuevosMensajes);
        estudiante.setPracticas(nuevasPracticas);
        estudiante.setEstichayuda("Nueva ayuda");

        // Assert
        assertEquals(nuevasHistorias, estudiante.getHistoriasConsenso());
        assertEquals(nuevosConsensios, estudiante.getConsensios());
        assertEquals(nuevosGrupos, estudiante.getGrupos());
        assertEquals(nuevosMensajes, estudiante.getMensajes());
        assertEquals(nuevasPracticas, estudiante.getPracticas());
        assertEquals("Nueva ayuda", estudiante.getEstichayuda());
    }

    @Test
    @DisplayName("Test de toString de Estudiante")
    void testToStringEstudiante() {
        // Act
        String resultado = estudiante.toString();

        // Assert
        assertNotNull(resultado, "toString() no debería retornar null");
        assertTrue(resultado.contains("Estudiante"), "Debería contener 'Estudiante'");
        assertTrue(resultado.contains("historiasConsenso"), "Debería contener 'historiasConsenso'");
        assertTrue(resultado.contains("grupos"), "Debería contener 'grupos'");
        assertTrue(resultado.contains("mensajes"), "Debería contener 'mensajes'");
    }

    @Test
    @DisplayName("Test de herencia de Usuario")
    void testHerenciaUsuario() {
        // Verificar que Estudiante hereda correctamente de Usuario
        assertTrue(estudiante instanceof Usuario, "Estudiante debería ser instancia de Usuario");
        
        // Verificar que los métodos heredados funcionan
        assertDoesNotThrow(() -> estudiante.buscat(), "Método heredado buscat() debería funcionar");
        assertDoesNotThrow(() -> estudiante.editLast(), "Método heredado editLast() debería funcionar");
        assertDoesNotThrow(() -> estudiante.edit("test"), "Método heredado edit() debería funcionar");
        assertDoesNotThrow(() -> estudiante.agregar(), "Método heredado agregar() debería funcionar");
    }

    @Test
    @DisplayName("Test de Builder con campos mínimos")
    void testBuilderCamposMinimos() {
        // Act - Crear estudiante solo con campos obligatorios
        Estudiante estudianteMinimo = new Estudiante.Builder()
                .setId("789")
                .setNombre("Carlos López")
                .setCorreo("carlos@test.com")
                .setTelefono("3001111111")
                .setContraseña("carlos123")
                .setTipoUsuario(TipoUsuario.ESTUDIANTE)
                .build();

        // Assert
        assertNotNull(estudianteMinimo, "El estudiante mínimo no debería ser null");
        assertEquals("789", estudianteMinimo.getId());
        assertEquals("Carlos López", estudianteMinimo.getNombre());
        assertNull(estudianteMinimo.getEstichayuda(), "Estichayuda debería ser null si no se establece");
        
        // Las listas deberían estar inicializadas pero vacías
        assertNotNull(estudianteMinimo.getGrupos());
        assertTrue(estudianteMinimo.getGrupos().isEmpty());
    }
} 