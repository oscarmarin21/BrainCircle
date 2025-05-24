package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.util.List;

public class SugerenciasConectados  {
    @FXML
    public Label lblAutor;
    @FXML
    public Button btnConect;
    @FXML
    public Button btnChat;
    private ControladorPrincipal controladorPrincipal;
    private Estudiante estudianteActual;
    
    public SugerenciasConectados() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    private String idUsuario;
    public void setData(Estudiante estudiante, Integer habil, String idUsuario_act) {

        if (estudiante == null) {
            System.out.println("El contenido es null");
            return;
        }
        if(habil ==1){
            btnConect.setVisible(false);
            btnChat.setVisible(true);
        }
        else if(habil ==2){
            btnConect.setVisible(true);
            btnChat.setVisible(false);
        }
        else if(habil ==3){
            btnConect.setVisible(false);
            btnChat.setVisible(false);
        }
        lblAutor.setText(estudiante.getNombre());
        idUsuario = idUsuario_act;
        estudianteActual = estudiante;
    }

    public void Conectar(ActionEvent actionEvent) {
        Usuario logueado = controladorPrincipal.obtenerUsuarioPorId(idUsuario);
        if (logueado != null && estudianteActual != null) {
            controladorPrincipal.conectarUsuarios(logueado, estudianteActual);
            btnConect.setDisable(true);
            controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml","Inicio Estudiantes", idUsuario);
            controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
            System.out.println("Conectado con " + estudianteActual.getNombre());
        } else {
            System.out.println("No se pudo conectar: usuario logueado o sugerido null");
        }
    }

    public void Chat(ActionEvent actionEvent) {
        Usuario logueado = controladorPrincipal.obtenerUsuarioPorId(idUsuario);

        controladorPrincipal.navegar(
                "/co/edu/uniquindio/braincircle/ChatViewsEstudent.xml.fxml",
                "MENSAJES",
                logueado,
                estudianteActual
        );
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
