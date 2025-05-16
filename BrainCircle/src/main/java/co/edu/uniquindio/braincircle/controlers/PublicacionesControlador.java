package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.util.List;

public class PublicacionesControlador implements Parametrizable {
    private ControladorPrincipal controladorPrincipal;

    public PublicacionesControlador()throws Exception {
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


    public void añadirMiPublicacon(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/AñadirPublicación.fxml", "Mipublicación", idUsuario);
    }
}
