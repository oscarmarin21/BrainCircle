package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GruposEstudioControlador implements Parametrizable {
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

}
