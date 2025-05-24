package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.GrupoEstudio;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GruposEstudioControlador implements Parametrizable {
    public GridPane gridPaneGrupos;
    private ControladorPrincipal controladorPrincipal;

    public GruposEstudioControlador() throws Exception {
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
        Estudiante estudiante = (Estudiante) controladorPrincipal.obtenerUsuarioPorId(idUsuario);
        List<GrupoEstudio> todosLosGrupos = controladorPrincipal.cargarGrupos();
        List<GrupoEstudio> gruposDisponibles = todosLosGrupos.stream()
                .filter(grupo -> !estudiante.getGrupos().contains(grupo.getIdGrupo()))
                .toList();
        cargarContenidoEnGrid(gruposDisponibles,1);
    }
    void cargarContenidoEnGrid(List<GrupoEstudio> grupoEstudios, int tipo) {
        gridPaneGrupos.getChildren().clear();

        int column = 0;
        int row = 0;

        for (GrupoEstudio grupoEstudio : grupoEstudios) {
            try {
                FXMLLoader fxmlLoader = controladorPrincipal.cargarVista("/co/edu/uniquindio/braincircle/GrupoEstudioPag.fxml");
                if (fxmlLoader != null) {
                    HBox pagGrupos = fxmlLoader.getRoot();
                    GrupoEstudioPagControlador grupoEstudioPagControlador = fxmlLoader.getController();
                    grupoEstudioPagControlador.setData(grupoEstudio, tipo, idUsuario);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPaneGrupos.add(pagGrupos, column++, row);
                    GridPane.setMargin(pagGrupos, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el grupo: " + grupoEstudio.getNombre());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void volverAlInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml","Inicio", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void misPublicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/Publicaciones.fxml","Publicaciones", idUsuario );
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void volver(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioEstudiante.fxml","Admin Contenido", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void misGrupos(ActionEvent actionEvent) {
        Estudiante estudiante = (Estudiante) controladorPrincipal.obtenerUsuarioPorId(idUsuario);
        List<GrupoEstudio> todosLosGrupos = controladorPrincipal.cargarGrupos();
        List<GrupoEstudio> gruposDisponibles = todosLosGrupos.stream()
                .filter(grupo -> estudiante.getGrupos().contains(grupo.getIdGrupo()))
                .toList();
        cargarContenidoEnGrid(gruposDisponibles,2);
    }
}
