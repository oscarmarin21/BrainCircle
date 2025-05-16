package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class InicioEstudiantesControlador implements Parametrizable {
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
    }

    public void abrirPublicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/Publicaciones.fxml","Publicaciones", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
