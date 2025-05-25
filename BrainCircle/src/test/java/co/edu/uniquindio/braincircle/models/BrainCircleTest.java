package co.edu.uniquindio.braincircle.models;

import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests para la clase BrainCircle (Sistema principal)
 */
class BrainCircleTest {

    private BrainCircle<String> brainCircle;
    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        brainCircle = new BrainCircle<>();
        
        // Crear usuarios de prueba
        brainCircle.registrar("123", "Juan Pérez", "juan@test.com", "3001234567", "password123");
        brainCircle.registrar("456", "María García", "maria@test.com", "3009876543", "maria123");
        
        usuario1 = brainCircle.obtenerUsuarioPorId("123");
        usuario2 = brainCircle.obtenerUsuarioPorId("456");
    }

    // ========== TESTS DE AUTENTICACIÓN ==========

    @Test
    @DisplayName("Test de autenticación exitosa")
    void testAutenticacionExitosa() {
        // Act & Assert
        assertTrue(brainCircle.autenticar("juan@test.com", "password123"), 
                  "La autenticación debería ser exitosa");
        assertTrue(brainCircle.autenticar("maria@test.com", "maria123"), 
                  "La autenticación debería ser exitosa");
    }

    @Test
    @DisplayName("Test de autenticación fallida")
    void testAutenticacionFallida() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            brainCircle.autenticar("juan@test.com", "wrongpassword");
        }, "Debería lanzar excepción con contraseña incorrecta");

        assertThrows(RuntimeException.class, () -> {
            brainCircle.autenticar("wrong@test.com", "password123");
        }, "Debería lanzar excepción con correo incorrecto");
    }

    @Test
    @DisplayName("Test de obtener usuario autenticado")
    void testObtenerUsuarioAutenticado() {
        // Act
        Usuario usuarioAutenticado = brainCircle.ObtenerUserAutenticado("juan@test.com", "password123");
        Usuario usuarioNoAutenticado = brainCircle.ObtenerUserAutenticado("juan@test.com", "wrongpassword");

        // Assert
        assertNotNull(usuarioAutenticado, "Debería retornar el usuario autenticado");
        assertEquals("Juan Pérez", usuarioAutenticado.getNombre(), "El nombre debería coincidir");
        assertNull(usuarioNoAutenticado, "No debería retornar usuario con credenciales incorrectas");
    }

    // ========== TESTS DE GESTIÓN DE USUARIOS ==========

    @Test
    @DisplayName("Test de registro de usuario")
    void testRegistroUsuario() {
        // Act
        boolean resultado = brainCircle.registrar("789", "Carlos López", "carlos@test.com", "3001111111", "carlos123");

        // Assert
        assertTrue(resultado, "El registro debería ser exitoso");
        
        Usuario usuarioRegistrado = brainCircle.obtenerUsuarioPorId("789");
        assertNotNull(usuarioRegistrado, "El usuario debería existir después del registro");
        assertEquals("Carlos López", usuarioRegistrado.getNombre(), "El nombre debería coincidir");
        assertEquals("carlos@test.com", usuarioRegistrado.getCorreo(), "El correo debería coincidir");
        assertEquals(TipoUsuario.ESTUDIANTE, usuarioRegistrado.getTipoUsuario(), "Debería ser ESTUDIANTE");
    }

    @Test
    @DisplayName("Test de obtener usuario por ID")
    void testObtenerUsuarioPorId() {
        // Act
        Usuario usuarioEncontrado = brainCircle.obtenerUsuarioPorId("123");
        Usuario usuarioNoEncontrado = brainCircle.obtenerUsuarioPorId("999");

        // Assert
        assertNotNull(usuarioEncontrado, "Debería encontrar el usuario");
        assertEquals("Juan Pérez", usuarioEncontrado.getNombre(), "El nombre debería coincidir");
        assertNull(usuarioNoEncontrado, "No debería encontrar usuario inexistente");
    }

    @Test
    @DisplayName("Test de cargar usuarios")
    void testCargarUsuarios() {
        // Act
        List<Usuario> usuarios = brainCircle.cargarUsuarios();

        // Assert
        assertNotNull(usuarios, "La lista de usuarios no debería ser null");
        assertEquals(2, usuarios.size(), "Debería tener 2 usuarios");
        assertTrue(usuarios.stream().anyMatch(u -> u.getNombre().equals("Juan Pérez")), 
                  "Debería contener a Juan Pérez");
        assertTrue(usuarios.stream().anyMatch(u -> u.getNombre().equals("María García")), 
                  "Debería contener a María García");
    }

    @Test
    @DisplayName("Test de editar usuario")
    void testEditarUsuario() {
        // Act
        boolean resultado = brainCircle.editarUsuario("Juan Carlos Pérez", "juancarlos@test.com", 
                                                     "3001234568", "newpassword", usuario1);

        // Assert
        assertTrue(resultado, "La edición debería ser exitosa");
        assertEquals("Juan Carlos Pérez", usuario1.getNombre(), "El nombre debería haberse actualizado");
        assertEquals("juancarlos@test.com", usuario1.getCorreo(), "El correo debería haberse actualizado");
        assertEquals("3001234568", usuario1.getTelefono(), "El teléfono debería haberse actualizado");
        assertEquals("newpassword", usuario1.getContraseña(), "La contraseña debería haberse actualizado");
    }

    @Test
    @DisplayName("Test de eliminar usuario")
    void testEliminarUsuario() {
        // Arrange
        int usuariosIniciales = brainCircle.cargarUsuarios().size();

        // Act
        boolean resultado = brainCircle.eliminarUsuario(usuario1);

        // Assert
        assertTrue(resultado, "La eliminación debería ser exitosa");
        assertEquals(usuariosIniciales - 1, brainCircle.cargarUsuarios().size(), 
                    "Debería tener un usuario menos");
        assertNull(brainCircle.obtenerUsuarioPorId("123"), 
                  "El usuario eliminado no debería encontrarse");
    }

    // ========== TESTS DE GESTIÓN DE CONTENIDOS ==========

    @Test
    @DisplayName("Test de agregar contenido")
    void testAgregarContenido() {
        // Arrange
        Contenido<String> contenido = new Contenido<>("1", "Título Test", "Tema Test", "ARCHIVO", "123", "archivo.pdf");

        // Act
        brainCircle.agregarContenido(contenido);

        // Assert
        Contenido<String> contenidoObtenido = brainCircle.obtenerContenidoPorId("1");
        assertNotNull(contenidoObtenido, "El contenido debería existir");
        assertEquals("Título Test", contenidoObtenido.getTitulo(), "El título debería coincidir");
    }

    @Test
    @DisplayName("Test de cargar contenidos")
    void testCargarContenidos() {
        // Arrange
        Contenido<String> contenido1 = new Contenido<>("1", "Título 1", "Tema 1", "ARCHIVO", "123", "archivo1.pdf");
        Contenido<String> contenido2 = new Contenido<>("2", "Título 2", "Tema 2", "IMAGEN", "456", "imagen2.jpg");
        
        brainCircle.agregarContenido(contenido1);
        brainCircle.agregarContenido(contenido2);

        // Act
        List<Contenido<String>> contenidos = brainCircle.cargarContenidos();

        // Assert
        assertNotNull(contenidos, "La lista de contenidos no debería ser null");
        assertEquals(2, contenidos.size(), "Debería tener 2 contenidos");
    }

    @Test
    @DisplayName("Test de actualizar contenido")
    void testActualizarContenido() {
        // Arrange
        Contenido<String> contenido = new Contenido<>("1", "Título Original", "Tema Original", "ARCHIVO", "123", "archivo.pdf");
        brainCircle.agregarContenido(contenido);

        // Act
        boolean resultado = brainCircle.actualizarContenido("1", "Título Actualizado", "Tema Actualizado", 
                                                           "IMAGEN", "123", "imagen.jpg");

        // Assert
        assertTrue(resultado, "La actualización debería ser exitosa");
        Contenido<String> contenidoActualizado = brainCircle.obtenerContenidoPorId("1");
        assertEquals("Título Actualizado", contenidoActualizado.getTitulo(), "El título debería haberse actualizado");
        assertEquals("Tema Actualizado", contenidoActualizado.getTema(), "El tema debería haberse actualizado");
    }

    @Test
    @DisplayName("Test de eliminar contenido")
    void testEliminarContenido() {
        // Arrange
        Contenido<String> contenido = new Contenido<>("1", "Título Test", "Tema Test", "ARCHIVO", "123", "archivo.pdf");
        brainCircle.agregarContenido(contenido);

        // Act
        boolean resultado = brainCircle.eliminarContenidoPorId("1");

        // Assert
        assertTrue(resultado, "La eliminación debería ser exitosa");
        assertNull(brainCircle.obtenerContenidoPorId("1"), "El contenido no debería existir después de eliminarlo");
    }

    @Test
    @DisplayName("Test de dar like a contenido")
    void testDarLikeAContenido() {
        // Arrange
        Contenido<String> contenido = new Contenido<>("1", "Título Test", "Tema Test", "ARCHIVO", "123", "archivo.pdf");
        brainCircle.agregarContenido(contenido);

        // Act
        boolean resultado = brainCircle.darLikeAContenido("1", "456");

        // Assert
        assertTrue(resultado, "Dar like debería ser exitoso");
        Contenido<String> contenidoConLike = brainCircle.obtenerContenidoPorId("1");
        assertEquals(1, contenidoConLike.getLikes(), "Debería tener 1 like");
    }

    @Test
    @DisplayName("Test de comentar contenido")
    void testComentarContenido() {
        // Arrange
        Contenido<String> contenido = new Contenido<>("1", "Título Test", "Tema Test", "ARCHIVO", "123", "archivo.pdf");
        brainCircle.agregarContenido(contenido);

        // Act
        boolean resultado = brainCircle.comentarContenido("1", "Excelente contenido");

        // Assert
        assertTrue(resultado, "Comentar debería ser exitoso");
        Contenido<String> contenidoConComentario = brainCircle.obtenerContenidoPorId("1");
        assertEquals(1, contenidoConComentario.getComentarios().size(), "Debería tener 1 comentario");
        assertTrue(contenidoConComentario.getComentarios().contains("Excelente contenido"), 
                  "Debería contener el comentario");
    }

    // ========== TESTS DE GESTIÓN DE SOLICITUDES ==========

    @Test
    @DisplayName("Test de crear solicitud")
    void testCrearSolicitud() {
        // Act
        Solicitud solicitud = brainCircle.crearSolicitud(usuario1, NivelPrioridad.ALTA, 
                                                        "Problema urgente", "Necesito ayuda inmediata");

        // Assert
        assertNotNull(solicitud, "La solicitud no debería ser null");
        assertEquals(usuario1, solicitud.getPropietario(), "El propietario debería coincidir");
        assertEquals(NivelPrioridad.ALTA, solicitud.getNivelPrioridad(), "La prioridad debería coincidir");
        assertEquals("Problema urgente", solicitud.getTitulo(), "El título debería coincidir");
        assertEquals("Necesito ayuda inmediata", solicitud.getMensaje(), "El mensaje debería coincidir");
        assertNotNull(solicitud.getId(), "El ID debería haberse generado");
    }

    @Test
    @DisplayName("Test de cola de prioridad de solicitudes")
    void testColaPrioridadSolicitudes() {
        // Arrange - Crear solicitudes con diferentes prioridades
        brainCircle.crearSolicitud(usuario1, NivelPrioridad.BAJA, "Baja", "Mensaje baja");
        brainCircle.crearSolicitud(usuario2, NivelPrioridad.ALTA, "Alta", "Mensaje alta");
        brainCircle.crearSolicitud(usuario1, NivelPrioridad.MEDIA, "Media", "Mensaje media");

        // Act & Assert - La solicitud de prioridad ALTA debería salir primero
        Solicitud proximaSolicitud = brainCircle.verProximaSolicitud();
        assertNotNull(proximaSolicitud, "Debería haber una próxima solicitud");
        assertEquals(NivelPrioridad.ALTA, proximaSolicitud.getNivelPrioridad(), 
                    "La próxima solicitud debería ser de prioridad ALTA");
        assertEquals("Alta", proximaSolicitud.getTitulo(), "El título debería coincidir");
    }

    @Test
    @DisplayName("Test de obtener próxima solicitud")
    void testObtenerProximaSolicitud() {
        // Arrange
        brainCircle.crearSolicitud(usuario1, NivelPrioridad.MEDIA, "Solicitud Media", "Mensaje");
        int solicitudesPendientesAntes = brainCircle.obtenerSolicitudesPendientes().size();

        // Act
        Solicitud solicitudObtenida = brainCircle.obtenerProximaSolicitud();

        // Assert
        assertNotNull(solicitudObtenida, "Debería obtener una solicitud");
        assertEquals("Solicitud Media", solicitudObtenida.getTitulo(), "El título debería coincidir");
        assertEquals(solicitudesPendientesAntes - 1, brainCircle.obtenerSolicitudesPendientes().size(), 
                    "Debería tener una solicitud pendiente menos");
    }

    @Test
    @DisplayName("Test de responder solicitud")
    void testResponderSolicitud() {
        // Arrange
        Solicitud solicitud = brainCircle.crearSolicitud(usuario1, NivelPrioridad.ALTA, "Problema", "Mensaje");

        // Act
        boolean resultado = brainCircle.responderSolicitud(solicitud, "Respuesta del administrador");

        // Assert
        assertTrue(resultado, "Responder solicitud debería ser exitoso");
        assertEquals("Respuesta del administrador", solicitud.getRespuesta(), "La respuesta debería coincidir");
        assertEquals(1, brainCircle.obtenerSolicitudesRespondidas().size(), 
                    "Debería tener 1 solicitud respondida");
        assertEquals(0, brainCircle.obtenerSolicitudesPendientes().size(), 
                    "No debería tener solicitudes pendientes");
    }

    @Test
    @DisplayName("Test de responder próxima solicitud")
    void testResponderProximaSolicitud() {
        // Arrange
        brainCircle.crearSolicitud(usuario1, NivelPrioridad.ALTA, "Urgente", "Mensaje urgente");

        // Act
        Solicitud solicitudRespondida = brainCircle.responderProximaSolicitud("Respuesta rápida");

        // Assert
        assertNotNull(solicitudRespondida, "Debería retornar la solicitud respondida");
        assertEquals("Respuesta rápida", solicitudRespondida.getRespuesta(), "La respuesta debería coincidir");
        assertEquals(1, brainCircle.obtenerSolicitudesRespondidas().size(), 
                    "Debería tener 1 solicitud respondida");
    }

    @Test
    @DisplayName("Test de obtener solicitudes por usuario")
    void testObtenerSolicitudesPorUsuario() {
        // Arrange
        brainCircle.crearSolicitud(usuario1, NivelPrioridad.ALTA, "Solicitud 1", "Mensaje 1");
        brainCircle.crearSolicitud(usuario2, NivelPrioridad.MEDIA, "Solicitud 2", "Mensaje 2");
        brainCircle.crearSolicitud(usuario1, NivelPrioridad.BAJA, "Solicitud 3", "Mensaje 3");

        // Act
        List<Solicitud> solicitudesUsuario1 = brainCircle.obtenerSolicitudesPorUsuario(usuario1);
        List<Solicitud> solicitudesUsuario2 = brainCircle.obtenerSolicitudesPorUsuario(usuario2);

        // Assert
        assertEquals(2, solicitudesUsuario1.size(), "Usuario1 debería tener 2 solicitudes");
        assertEquals(1, solicitudesUsuario2.size(), "Usuario2 debería tener 1 solicitud");
    }

    // ========== TESTS DE GESTIÓN DE GRUPOS ==========

    @Test
    @DisplayName("Test de agregar miembro a grupo")
    void testAgregarMiembroAGrupo() {
        // Arrange
        brainCircle.crearGrupoEstudio("GRUPO001", "Biología", "Grupo de biología", Materia.BIOLOGIA);
        Estudiante estudiante = (Estudiante) usuario1;

        // Act
        boolean resultado = brainCircle.agregarMiembro("GRUPO001", estudiante);

        // Assert
        assertTrue(resultado, "Agregar miembro debería ser exitoso");
        
        GrupoEstudio grupo = GrupoEstudio.buscarPorId("GRUPO001");
        assertNotNull(grupo, "El grupo debería existir");
        assertEquals(1, grupo.getMiembros().size(), "El grupo debería tener 1 miembro");
        assertTrue(grupo.getMiembros().contains(estudiante), "El grupo debería contener al estudiante");
    }

    @Test
    @DisplayName("Test de enviar mensaje a grupo")
    void testEnviarMensajeAGrupo() {
        // Arrange
        brainCircle.crearGrupoEstudio("GRUPO001", "Inglés", "Grupo de inglés", Materia.INGLES);
        Estudiante estudiante = (Estudiante) usuario1;
        brainCircle.agregarMiembro("GRUPO001", estudiante);

        // Act
        boolean resultado = brainCircle.enviarMensajeAGrupo("GRUPO001", "123", "Hola a todos!");

        // Assert
        assertTrue(resultado, "Enviar mensaje debería ser exitoso");
        
        GrupoEstudio grupo = GrupoEstudio.buscarPorId("GRUPO001");
        List<String> mensajes = grupo.getMensajesChat();
        assertEquals(1, mensajes.size(), "Debería tener 1 mensaje");
        assertTrue(mensajes.get(0).contains("Juan Pérez: Hola a todos!"), 
                  "El mensaje debería contener el nombre del usuario y el contenido");
    }

    // ========== TESTS DE CONECTIVIDAD ==========

    @Test
    @DisplayName("Test de conectar usuarios")
    void testConectarUsuarios() {
        // Act
        assertDoesNotThrow(() -> brainCircle.conectarUsuarios(usuario1, usuario2), 
                          "Conectar usuarios no debería lanzar excepción");
    }

    @Test
    @DisplayName("Test de sugerencias de amistad")
    void testSugerenciasAmistad() {
        // Act
        List<Usuario> sugerencias = brainCircle.sugerenciasDeAmistad(usuario1);

        // Assert
        assertNotNull(sugerencias, "Las sugerencias no deberían ser null");
        // El comportamiento específico depende de la implementación del grafo
    }

    @Test
    @DisplayName("Test de obtener conversación")
    void testObtenerConversacion() {
        // Act
        List<String> conversacion = brainCircle.obtenerConversacion(usuario1, usuario2);

        // Assert
        assertNotNull(conversacion, "La conversación no debería ser null");
        // El contenido específico depende de la implementación
    }
} 