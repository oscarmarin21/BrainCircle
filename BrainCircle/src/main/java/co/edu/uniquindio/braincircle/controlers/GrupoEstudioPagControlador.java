package co.edu.uniquindio.braincircle.controlers;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.GrupoEstudio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
public class GrupoEstudioPagControlador implements Parametrizable {
    @FXML
    public Label lblTitulo;
    @FXML
    public Label lblDescrip;
    @FXML
    public Button btnConect;
    @FXML
    public Button btnChat;
    private ControladorPrincipal controladorPrincipal;
    private GrupoEstudio grupoEstudioActual;
    public GrupoEstudioPagControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    
    private String idUsuario;
    @Override
    public void datosBrainCircle(Object[] parametros) {

    }
    public void setData(GrupoEstudio grupoEstudio, Integer habil, String idUser) {
        idUsuario = idUser;
        if (habil ==1){
            btnChat.setVisible(false);
        }
        if (habil==2){
            btnConect.setVisible(false);
        }
        if (grupoEstudio == null) {
            System.out.println("El grupoEstudio es null");
            return;
        }
        lblTitulo.setText("Titulo: "+ grupoEstudio.getNombre());
        lblDescrip.setText("Tema: "+ grupoEstudio.getDescripcion());

        grupoEstudioActual = grupoEstudio;
    }

    public void Conectar(ActionEvent actionEvent) {
        Estudiante estudiante = (Estudiante) controladorPrincipal.obtenerUsuarioPorId(idUsuario);

        if (grupoEstudioActual == null || estudiante == null) {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo conectar al grupo", Alert.AlertType.ERROR);
            return;
        }
        boolean agregado = controladorPrincipal.agregarMiembro(grupoEstudioActual.getIdGrupo(), estudiante);
        if (agregado) {
            controladorPrincipal.mostrarMensaje("Conectado", "Te has unido al grupo correctamente", Alert.AlertType.INFORMATION);
            controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioEstudiante.fxml","",idUsuario);
            controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());

        } else {
            controladorPrincipal.mostrarMensaje("Ya conectado", "Ya perteneces a este grupo", Alert.AlertType.WARNING);
        }
    }

    public void Chat(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/chat.fxml","",idUsuario,grupoEstudioActual.getIdGrupo());
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
