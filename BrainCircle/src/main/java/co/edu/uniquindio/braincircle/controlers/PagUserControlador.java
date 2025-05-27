package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PagUserControlador implements Parametrizable {
    @FXML
    public GridPane gridPaneUsersGeneral;
    private String idUsuario;
    private ControladorPrincipal controladorPrincipal;

    public PagUserControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista.");
            return;
        }

        idUsuario = parametros[0].toString();
        System.out.println("✅ Usuario autenticado con ID: " + idUsuario);
        Usuario usuarioActual = controladorPrincipal.obtenerUsuarioPorId(idUsuario);

        if (usuarioActual instanceof Estudiante estudianteActual) {
            // Todos los usuarios registrados
            List<Usuario> todosLosUsuarios = controladorPrincipal.cargarUsuarios();

            Set<String> idsConexiones = estudianteActual.getConexiones()
                    .stream()
                    .map(Usuario::getId)
                    .collect(Collectors.toSet());

            List<Estudiante> estudiantesSinConexion = new ArrayList<>();

            for (Usuario u : todosLosUsuarios) {
                if (u instanceof Estudiante e) {
                    boolean esElMismo = e.getId().equals(idUsuario);
                    boolean yaConectado = idsConexiones.contains(e.getId());

                    if (!esElMismo && !yaConectado) {
                        estudiantesSinConexion.add(e);
                    }
                }
            }

            cargarConexEnGrid(estudiantesSinConexion, 2, idUsuario);
        }

    }

    void cargarConexEnGrid(List<Estudiante> estudiantes, int tipo, String idUsuario_act) {
        gridPaneUsersGeneral.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Estudiante estudiante : estudiantes) {
            try {
                FXMLLoader fxmlLoader = controladorPrincipal.cargarVista("/co/edu/uniquindio/braincircle/SugerenciasConectados.fxml");
                if (fxmlLoader != null) {
                    HBox pagSugerenciasUser = fxmlLoader.getRoot();
                    SugerenciasConectados PagUserControler = fxmlLoader.getController();
                    PagUserControler.setData(estudiante, tipo, idUsuario_act);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPaneUsersGeneral.add(pagSugerenciasUser, column++, row);
                    GridPane.setMargin(pagSugerenciasUser, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el estudiante: " + estudiante.getNombre());
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

    public void abrirPublicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/Publicaciones.fxml","Inicio", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void gruposEstudi(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioEstudiante.fxml","Inicio", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
