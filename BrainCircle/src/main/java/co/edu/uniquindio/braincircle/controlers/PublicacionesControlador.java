package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.util.List;

public class PublicacionesControlador implements Parametrizable {
    @FXML
    public GridPane gridPaneContenido;
    @FXML
    public Button bntVolver;
    @FXML
    public Button bntGrupo;
    private ControladorPrincipal controladorPrincipal;

    public PublicacionesControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    private String idUsuario;


    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista.");
            return;
        }
        idUsuario = parametros[0].toString();
        System.out.println("✅ Usuario autenticado con ID: " + idUsuario);
        List<Contenido> todos = controladorPrincipal.cargarContenidos();
        List<Contenido> filtrados = todos.stream()
                .filter(c -> !c.getAutor().equals(idUsuario))
                .toList();
        cargarContenidoEnGrid(filtrados, 1);
    }

    /**
     * Método para cargar las publicaciones en el panel del administrador
     */

    void cargarContenidoEnGrid(List<Contenido> contenidos, int tipo) {
        gridPaneContenido.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Contenido contenido : contenidos) {
            try {
                FXMLLoader fxmlLoader = controladorPrincipal.cargarVista("/co/edu/uniquindio/braincircle/PublicacionesPag.fxml");
                if (fxmlLoader != null) {
                    HBox pagContenido = fxmlLoader.getRoot();
                    PublicacionesPagControlador pagConteControlador = fxmlLoader.getController();
                    pagConteControlador.setData(contenido, tipo);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPaneContenido.add(pagContenido, column++, row);
                    GridPane.setMargin(pagContenido, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el contenido: " + contenido.getTitulo());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Métodos de redirección del administrador
     */

    public void añadirMiPublicacon(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/AñadirPublicación.fxml", "Mipublicación", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void volverAlInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml", "Inicio", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    public void btnGruposEstudio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioEstudiante.fxml","",idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    public void misPublicacon(ActionEvent actionEvent) {
        List<Contenido> todosLosContenidos = controladorPrincipal.cargarContenidos();

        List<Contenido> misContenidos = todosLosContenidos.stream()
                .filter(contenido -> contenido.getAutor().equals(idUsuario))
                .toList();
        cargarContenidoEnGrid(misContenidos, 2);
        bntVolver.setVisible(true);
    }

    public void volver(ActionEvent actionEvent) {
        List<Contenido> contenidos = controladorPrincipal.cargarContenidos();
        List<Contenido> filtrados = contenidos.stream()
                .filter(c -> !c.getAutor().equals(idUsuario))
                .toList();
        cargarContenidoEnGrid(filtrados, 1);
        bntVolver.setVisible(false);
    }
}
