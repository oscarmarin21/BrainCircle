package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.ChatListener;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;

public class ChatControlador implements Parametrizable,ChatListener {

    @FXML
    private ListView<String> listaMensajes;

    @FXML
    private TextField campoMensaje;
    private String idUsuario;
    private Usuario usuarioActual;
    private Usuario receptor;
    private ControladorPrincipal controladorPrincipal;

    public ChatControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
        controladorPrincipal.registrarChatListener(this); // Se registra como listener
    }

    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length < 2 || parametros[0] == null || parametros[1] == null) {
            System.out.println("no se enviaron correctamente los parámetros.");
            return;
        }

        usuarioActual = (Usuario) parametros[0];
        receptor = (Usuario) parametros[1];
        controladorPrincipal.registrarChatListener(this);
        Platform.runLater(this::cargarMensajes);
    }


    private void cargarMensajes() {
        List<String> mensajes = controladorPrincipal.obtenerConversacion(usuarioActual, receptor);
        listaMensajes.getItems().setAll(mensajes);
    }
    public void volverAlInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml", "Inicio", usuarioActual.getId());
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    @FXML
    public void enviarMensaje() {
        String texto = campoMensaje.getText().trim();
        if (!texto.isEmpty()) {
            controladorPrincipal.enviarMensaje(usuarioActual, receptor, texto);
            campoMensaje.clear();
            cargarMensajes();
        }
    }

    @Override
    public void onMensajeNuevo(String mensaje) {
        if (mensaje.contains(usuarioActual.getNombre()) || mensaje.contains(receptor.getNombre())) {
            Platform.runLater(this::cargarMensajes);
        }
    }
}
