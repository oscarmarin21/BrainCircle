package co.edu.uniquindio.braincircle.enums;

import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;
import co.edu.uniquindio.braincircle.Enums.Tipo;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para todos los enums del proyecto
 */
class EnumsTest {

    // ========== TESTS PARA ENUM MATERIA ==========

    @Test
    @DisplayName("Test de valores del enum Materia")
    void testValoresMateria() {
        // Act & Assert
        Materia[] materias = Materia.values();
        
        assertEquals(4, materias.length, "Debería tener 4 materias");
        
        // Verificar que existen todas las materias esperadas
        assertTrue(contains(materias, Materia.BIOLOGIA), "Debería contener BIOLOGIA");
        assertTrue(contains(materias, Materia.INGLES), "Debería contener INGLES");
        assertTrue(contains(materias, Materia.MATEMATICAS), "Debería contener MATEMATICAS");
        assertTrue(contains(materias, Materia.ESTRUCTURA), "Debería contener ESTRUCTURA");
    }

    @Test
    @DisplayName("Test de valueOf para enum Materia")
    void testValueOfMateria() {
        // Act & Assert
        assertEquals(Materia.BIOLOGIA, Materia.valueOf("BIOLOGIA"));
        assertEquals(Materia.INGLES, Materia.valueOf("INGLES"));
        assertEquals(Materia.MATEMATICAS, Materia.valueOf("MATEMATICAS"));
        assertEquals(Materia.ESTRUCTURA, Materia.valueOf("ESTRUCTURA"));
        
        // Test de excepción para valor inválido
        assertThrows(IllegalArgumentException.class, () -> {
            Materia.valueOf("FISICA");
        }, "Debería lanzar excepción para materia inexistente");
    }

    @Test
    @DisplayName("Test de toString para enum Materia")
    void testToStringMateria() {
        // Act & Assert
        assertEquals("BIOLOGIA", Materia.BIOLOGIA.toString());
        assertEquals("INGLES", Materia.INGLES.toString());
        assertEquals("MATEMATICAS", Materia.MATEMATICAS.toString());
        assertEquals("ESTRUCTURA", Materia.ESTRUCTURA.toString());
    }

    // ========== TESTS PARA ENUM NIVEL_PRIORIDAD ==========

    @Test
    @DisplayName("Test de valores del enum NivelPrioridad")
    void testValoresNivelPrioridad() {
        // Act & Assert
        NivelPrioridad[] prioridades = NivelPrioridad.values();
        
        assertEquals(3, prioridades.length, "Debería tener 3 niveles de prioridad");
        
        // Verificar que existen todos los niveles esperados
        assertTrue(contains(prioridades, NivelPrioridad.ALTA), "Debería contener ALTA");
        assertTrue(contains(prioridades, NivelPrioridad.MEDIA), "Debería contener MEDIA");
        assertTrue(contains(prioridades, NivelPrioridad.BAJA), "Debería contener BAJA");
    }

    @Test
    @DisplayName("Test de valueOf para enum NivelPrioridad")
    void testValueOfNivelPrioridad() {
        // Act & Assert
        assertEquals(NivelPrioridad.ALTA, NivelPrioridad.valueOf("ALTA"));
        assertEquals(NivelPrioridad.MEDIA, NivelPrioridad.valueOf("MEDIA"));
        assertEquals(NivelPrioridad.BAJA, NivelPrioridad.valueOf("BAJA"));
        
        // Test de excepción para valor inválido
        assertThrows(IllegalArgumentException.class, () -> {
            NivelPrioridad.valueOf("URGENTE");
        }, "Debería lanzar excepción para prioridad inexistente");
    }

    @Test
    @DisplayName("Test de toString para enum NivelPrioridad")
    void testToStringNivelPrioridad() {
        // Act & Assert
        assertEquals("ALTA", NivelPrioridad.ALTA.toString());
        assertEquals("MEDIA", NivelPrioridad.MEDIA.toString());
        assertEquals("BAJA", NivelPrioridad.BAJA.toString());
    }

    // ========== TESTS PARA ENUM TIPO ==========

    @Test
    @DisplayName("Test de valueOf para enum Tipo")
    void testValueOfTipo() {
        // Act & Assert
        assertEquals(Tipo.ARCHIVO, Tipo.valueOf("ARCHIVO"));
        assertEquals(Tipo.IMAGEN, Tipo.valueOf("IMAGEN"));
        assertEquals(Tipo.VIDEO, Tipo.valueOf("VIDEO"));
        
        // Test de excepción para valor inválido
        assertThrows(IllegalArgumentException.class, () -> {
            Tipo.valueOf("AUDIO");
        }, "Debería lanzar excepción para tipo inexistente");
    }

    @Test
    @DisplayName("Test de toString para enum Tipo")
    void testToStringTipo() {
        // Act & Assert
        assertEquals("ARCHIVO", Tipo.ARCHIVO.toString());
        assertEquals("IMAGEN", Tipo.IMAGEN.toString());
        assertEquals("VIDEO", Tipo.VIDEO.toString());
    }

    // ========== TESTS PARA ENUM TIPO_USUARIO ==========

    @Test
    @DisplayName("Test de valores del enum TipoUsuario")
    void testValoresTipoUsuario() {
        // Act & Assert
        TipoUsuario[] tiposUsuario = TipoUsuario.values();
        
        assertEquals(2, tiposUsuario.length, "Debería tener 2 tipos de usuario");
        
        // Verificar que existen todos los tipos esperados
        assertTrue(contains(tiposUsuario, TipoUsuario.ADMIN), "Debería contener ADMIN");
        assertTrue(contains(tiposUsuario, TipoUsuario.ESTUDIANTE), "Debería contener ESTUDIANTE");
    }

    @Test
    @DisplayName("Test de valueOf para enum TipoUsuario")
    void testValueOfTipoUsuario() {
        // Act & Assert
        assertEquals(TipoUsuario.ADMIN, TipoUsuario.valueOf("ADMIN"));
        assertEquals(TipoUsuario.ESTUDIANTE, TipoUsuario.valueOf("ESTUDIANTE"));
        
        // Test de excepción para valor inválido
        assertThrows(IllegalArgumentException.class, () -> {
            TipoUsuario.valueOf("PROFESOR");
        }, "Debería lanzar excepción para tipo de usuario inexistente");
    }

    @Test
    @DisplayName("Test de toString para enum TipoUsuario")
    void testToStringTipoUsuario() {
        // Act & Assert
        assertEquals("ADMIN", TipoUsuario.ADMIN.toString());
        assertEquals("ESTUDIANTE", TipoUsuario.ESTUDIANTE.toString());
    }

    // ========== TESTS DE COMPARACIÓN ENTRE ENUMS ==========

    @Test
    @DisplayName("Test de comparación de enums")
    void testComparacionEnums() {
        // Test de igualdad
        assertEquals(Materia.BIOLOGIA, Materia.BIOLOGIA, "Mismo enum debería ser igual");
        assertEquals(NivelPrioridad.ALTA, NivelPrioridad.ALTA, "Mismo enum debería ser igual");
        assertEquals(Tipo.ARCHIVO, Tipo.ARCHIVO, "Mismo enum debería ser igual");
        assertEquals(TipoUsuario.ADMIN, TipoUsuario.ADMIN, "Mismo enum debería ser igual");
        
        // Test de desigualdad
        assertNotEquals(Materia.BIOLOGIA, Materia.MATEMATICAS, "Diferentes enums no deberían ser iguales");
        assertNotEquals(NivelPrioridad.ALTA, NivelPrioridad.BAJA, "Diferentes enums no deberían ser iguales");
        assertNotEquals(Tipo.ARCHIVO, Tipo.VIDEO, "Diferentes enums no deberían ser iguales");
        assertNotEquals(TipoUsuario.ADMIN, TipoUsuario.ESTUDIANTE, "Diferentes enums no deberían ser iguales");
    }

    @Test
    @DisplayName("Test de name() de enums")
    void testNameEnums() {
        // Act & Assert
        assertEquals("BIOLOGIA", Materia.BIOLOGIA.name());
        assertEquals("ALTA", NivelPrioridad.ALTA.name());
        assertEquals("ARCHIVO", Tipo.ARCHIVO.name());
        assertEquals("ADMIN", TipoUsuario.ADMIN.name());
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Método auxiliar para verificar si un array contiene un elemento específico
     */
    private <T> boolean contains(T[] array, T element) {
        for (T item : array) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }
} 