package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
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

public class InicioEstudiantesControlador implements Parametrizable {
    @FXML
    public GridPane gridSugerenciasUser;
    @FXML
    public GridPane gridConexUser;
    private ControladorPrincipal controladorPrincipal;

    public InicioEstudiantesControlador()throws Exception {
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

        Usuario usuarioActual = controladorPrincipal.obtenerUsuarioPorId(idUsuario);

        if (usuarioActual instanceof Estudiante estudianteActual) {
            Set<Usuario> conexiones = controladorPrincipal.obtenerConexiones(usuarioActual);
            List<Usuario> sugerencias = controladorPrincipal.sugerenciasDeAmistad(usuarioActual);
            System.out.println("AQUIIIIIIIIIII" + conexiones);
            List<Estudiante> conexionesEstudiantes = new ArrayList<>();
            for (Usuario u : conexiones) {
                if (u instanceof Estudiante e) {
                    conexionesEstudiantes.add(e);
                }
            }
            List<Estudiante> sugerenciasEstudiantes = new ArrayList<>();
            for (Usuario u : sugerencias) {
                if (u instanceof Estudiante e) {
                    sugerenciasEstudiantes.add(e);
                }
            }
            cargarConexEnGrid(conexionesEstudiantes, 1,idUsuario);
            cargarSugeEnGrid(sugerenciasEstudiantes, 2 , idUsuario);
        }
    }

    public void abrirPublicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/Publicaciones.fxml","Publicaciones", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    void cargarSugeEnGrid(List<Estudiante> estudiantes, int tipo, String idUsuario_act) {
        gridSugerenciasUser.getChildren().clear();

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

                    gridSugerenciasUser.add(pagSugerenciasUser, column++, row);
                    GridPane.setMargin(pagSugerenciasUser, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el estudiante: " + estudiante.getNombre());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    void cargarConexEnGrid(List<Estudiante> estudiantes, int tipo, String idUsuario_act) {
        gridConexUser.getChildren().clear();

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

                    gridConexUser.add(pagSugerenciasUser, column++, row);
                    GridPane.setMargin(pagSugerenciasUser, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el estudiante: " + estudiante.getNombre());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
