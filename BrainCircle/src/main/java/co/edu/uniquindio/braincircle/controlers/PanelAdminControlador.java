package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Nodo.Arista;
import co.edu.uniquindio.braincircle.Nodo.Nodo;
import co.edu.uniquindio.braincircle.Services.ServicioBrainCircle;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;
import java.util.stream.Collectors;

public class PanelAdminControlador {

    private ControladorPrincipal controladorPrincipal;
    private Contenido contenido;

    @FXML
    private Pane pane;
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

        // Obtener lista de contenidos desde el modelo accedido por ControladorPrincipal
        List<Contenido<?>> listaContenidos = controladorPrincipal.getBrainCircle().cargarContenidos();

        // Ordenar los contenidos por cantidad de likes (de mayor a menor)
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

        // Obtener la lista de contenidos desde el modelo
        List<Contenido<?>> listaContenidos = controladorPrincipal.getBrainCircle().cargarContenidos();

        // Agrupar por autor y contar cuántas publicaciones hizo cada uno
        Map<String, Long> publicacionesPorAutor = listaContenidos.stream()
                .collect(Collectors.groupingBy(
                        contenido -> contenido.getAutor().toString(),  // Convertimos a String si T es genérico
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

    public void Solicitudes(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/SolicitudesAdmin.fxml","Gestión de Solicitudes", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
