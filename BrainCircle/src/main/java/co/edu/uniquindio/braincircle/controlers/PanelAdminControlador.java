package co.edu.uniquindio.braincircle.controlers;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Usuario;
import co.edu.uniquindio.braincircle.models.enums.TipoUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import java.util.*;
import java.util.stream.Collectors;

public class PanelAdminControlador {

    private ControladorPrincipal controladorPrincipal;
    private Contenido contenido;

    @FXML
    public ComboBox cmbEstadisticas;
    @FXML
    private BarChart<String, Number> barChart;

    /**
     * Toma el vontenido de la combobox y filtra la gráfica que se quiere mostrar
     */

    @FXML
    public void buscarAction(ActionEvent event) {
        String seleccion = (String) cmbEstadisticas.getValue();
        barChart.getData().clear();

        if ("Contenidas más valorados".equals(seleccion)) {
            mostrarContenidosMasValorados();
        } else if ("Niveles de paricipación".equals(seleccion)) {
            mostrarNivelParticipacion();
        } else if ("Estudianes con más conexiones".equals(seleccion)) {
            mostrarEstudiantesConMasConexiones();
        } else {
            controladorPrincipal.mostrarMensaje("Selección no válida", "Error", javafx.scene.control.Alert.AlertType.WARNING);
        }
    }

    public void PanelAdminControlador() throws Exception {controladorPrincipal= ControladorPrincipal.getInstancia();}

    /**
     * Muestra la gráfica de barras de los contenidos más valorados por otros estudiantes
     */

    private void mostrarContenidosMasValorados() {
        barChart.getData().clear();

        List<Contenido<?>> listaContenidos = controladorPrincipal.getBrainCircle().cargarContenidos();

        listaContenidos.sort((c1, c2) -> Integer.compare(c2.getLikes(), c1.getLikes()));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Contenidos más valorados");

        for (Contenido<?> contenido : listaContenidos) {
            String titulo = contenido.getTitulo().toString();
            int likes = contenido.getLikes();
            series.getData().add(new XYChart.Data<>(titulo, likes));
        }

        barChart.getData().add(series);
    }

    /**
     * Muestra la gráfica de barras de los niveles de participación de los estudiantes
     */
    private void mostrarNivelParticipacion() {
        barChart.getData().clear();

        List<Contenido<?>> listaContenidos = controladorPrincipal.getBrainCircle().cargarContenidos();

        Map<String, Long> publicacionesPorAutor = listaContenidos.stream()
                .collect(Collectors.groupingBy(
                        contenido -> contenido.getAutor().toString(),
                        Collectors.counting()
                ));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nivel de Participación por Estudiante");

        publicacionesPorAutor.forEach((autor, cantidad) -> {
            series.getData().add(new XYChart.Data<>(autor, cantidad));
        });

        barChart.getData().add(series);
    }

    /**
     * Muestra la gráfica de barras de los estudiantes con más conexiones
     */

    private void mostrarEstudiantesConMasConexiones() {
        barChart.getData().clear();

        List<Usuario> todosLosUsuarios = ControladorPrincipal.getInstancia().getBrainCircle().cargarUsuarios();

        List<Usuario> estudiantes = todosLosUsuarios.stream()
                .filter(usuario -> usuario.getTipoUsuario() == TipoUsuario.ESTUDIANTE)
                .collect(Collectors.toList());

        estudiantes.sort(Comparator.comparingInt(usuario -> usuario.getConexiones().size()));
        Collections.reverse(estudiantes);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estudiantes con más conexiones");

        for (Usuario estudiante : estudiantes) {
            String nombre = estudiante.getNombre();
            int conexiones = estudiante.getConexiones().size();
            series.getData().add(new XYChart.Data<>(nombre, conexiones));
        }

        barChart.getData().add(series);
    }


    /**
     * Métodos de redirección del administrador
     */

    public PanelAdminControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    public void Publicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PublicacionesAdmin.fxml","Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void Usuarios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/UsuariosAdmin.fxml","Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void btnGruposEstudios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
