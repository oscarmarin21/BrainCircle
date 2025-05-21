package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;

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
    }

    public void misPublicacon(ActionEvent actionEvent) {
    }

    public void volver(ActionEvent actionEvent) {
    }
}
