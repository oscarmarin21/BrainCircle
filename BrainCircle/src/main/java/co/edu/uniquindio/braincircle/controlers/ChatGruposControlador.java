package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.ChatListener;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.GrupoEstudio;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;

public class ChatGruposControlador implements Parametrizable, ChatListener {
    @FXML
    public TextArea chatArea;
    @FXML
    public TextField messageField;
    @FXML
    public Button sendButton;
    @FXML
    public Button uploadButton;
    @FXML
    public GridPane gridUser;
    private List<Estudiante> miembros;

    private ControladorPrincipal controladorPrincipal;
    @FXML
    public FlowPane contenidosPane;
    public ChatGruposControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    private String idUsuario;
    private GrupoEstudio grupoEstudioActual;
    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null || parametros.length == 1 || parametros[1] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista.");
            return;
        }
        idUsuario = parametros[0].toString();
        grupoEstudioActual = GrupoEstudio.buscarPorId((String) parametros[1]);
        System.out.println("✅ Usuario autenticado con ID: " + idUsuario);
        List<Estudiante> participantes = grupoEstudioActual.getMiembros();
        cargarusernGrid(participantes,3,idUsuario);
        grupoEstudioActual.agregarListener(this);
//        Platform.runLater(this::actualizarChat);
        Platform.runLater(() -> {
            actualizarChat();
            actualizarContenidosCompartidos();
        });
    }
    void cargarusernGrid(List<Estudiante> estudiantes, int tipo, String idUsuario_act) {
        gridUser.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Estudiante estudiante : estudiantes) {
            try {
                FXMLLoader fxmlLoader = controladorPrincipal.cargarVista("/co/edu/uniquindio/braincircle/SugerenciasConectados.fxml");
                if (fxmlLoader != null) {
                    HBox pagSugerenciasUser = fxmlLoader.getRoot();
                    SugerenciasConectados PagUserControler = fxmlLoader.getController();
                    PagUserControler.setData(estudiante, tipo, idUsuario_act);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridUser.add(pagSugerenciasUser, column++, row);
                    GridPane.setMargin(pagSugerenciasUser, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el estudiante: " + estudiante.getNombre());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Enviar(ActionEvent actionEvent) {
        String texto = messageField.getText().trim();

        if (texto.isEmpty()) return;

        boolean enviado = controladorPrincipal.enviarMensajeAGrupo(
                grupoEstudioActual.getIdGrupo(),
                idUsuario,
                texto
        );

        if (enviado) {
            actualizarChat();
            messageField.clear();
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo enviar el mensaje", Alert.AlertType.ERROR);
        }
    }

    private void actualizarChat() {
        StringBuilder chat = new StringBuilder();
        for (String m : grupoEstudioActual.getMensajesChat()) {
            chat.append(m).append("\n");
        }
        chatArea.setText(chat.toString());
    }
    public void subirRecursos(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo para compartir");
        File archivo = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

        if (archivo != null) {
            grupoEstudioActual.getContenidos().add(archivo.getAbsolutePath());
            grupoEstudioActual.enviarMensaje("[📎] " + archivo.getName() + " ha sido compartido.");
            grupoEstudioActual.notificarOyentes("__actualizar_contenidos__");
        }
    }

    private void actualizarContenidosCompartidos() {
        contenidosPane.getChildren().clear();

        for (String rutaCompleta : grupoEstudioActual.getContenidos()) {
            File archivo = new File(rutaCompleta);
            String nombreArchivo = archivo.getName();
            Label label = new Label(nombreArchivo);
            label.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
            label.setOnMouseClicked(event -> {
                System.out.println("📥 Descargar archivo: " + rutaCompleta);
                descargarArchivo(rutaCompleta);
            });
            contenidosPane.getChildren().add(label);
        }
    }

    private void descargarArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar archivo como...");
                fileChooser.setInitialFileName(archivo.getName());

                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
                );

                File destino = fileChooser.showSaveDialog(new Stage());
                if (destino != null) {
                    Files.copy(archivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    controladorPrincipal.mostrarMensaje("Éxito", "Archivo descargado como: " + destino.getName(), Alert.AlertType.INFORMATION);
                }
            } else {
                controladorPrincipal.mostrarMensaje("Error", "Archivo no encontrado", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo descargar el archivo", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    @Override
    public void onMensajeNuevo(String mensaje) {
        Platform.runLater(() -> {
            if (mensaje.equals("__actualizar_usuarios__")) {
                cargarusernGrid(grupoEstudioActual.getMiembros(), 3, idUsuario);
            } else if (mensaje.equals("__actualizar_contenidos__")) {
                actualizarContenidosCompartidos();
            } else {
                actualizarChat();
            }
        });
    }

}


