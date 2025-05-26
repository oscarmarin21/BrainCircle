package co.edu.uniquindio.braincircle.controlers;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class PagUserControlador {


    private String idUsuario;
    private ControladorPrincipal controladorPrincipal;

    public PagUserControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
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
