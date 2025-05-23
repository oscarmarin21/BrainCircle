package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Enums.Materia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class GruposEstudioAdminControlador {

    @FXML
    public ComboBox<Materia> cmbMateria;
    private ControladorPrincipal controladorPrincipal;
    private Materia materiaSeleccionada;

    public GruposEstudioAdminControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    public void initialize() {
        inicializarMateria();
    }
    public void Publicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PublicacionesAdmin.fxml","Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());

    }

    public void Usuarios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/UsuariosAdmin.fxml","Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void btnInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void inicializarMateria(){
        cmbMateria.getItems().addAll(Materia.values());
        listenerMateria();
    }

    public void listenerMateria() {
        cmbMateria.setOnAction(event -> {
            materiaSeleccionada = (Materia) cmbMateria.getValue();

        });
    }
}
