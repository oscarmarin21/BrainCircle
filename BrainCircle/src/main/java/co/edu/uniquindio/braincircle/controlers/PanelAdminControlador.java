package co.edu.uniquindio.braincircle.controlers;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class PanelAdminControlador {
    private ControladorPrincipal controladorPrincipal;

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
