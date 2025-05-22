package co.edu.uniquindio.braincircle.controlers;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class UsuariosAdminControlador {

    private ControladorPrincipal controladorPrincipal;

    public UsuariosAdminControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void Publicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PublicacionesAdmin.fxml","Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());

    }

    public void btnInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void btnGruposEstudios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
