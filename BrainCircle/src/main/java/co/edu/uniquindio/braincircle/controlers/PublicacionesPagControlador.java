package co.edu.uniquindio.braincircle.controlers;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PublicacionesPagControlador<T extends Comparable<T>> implements Parametrizable {
    @FXML
    public Label lblTitulo;
    @FXML
    public Label lblDescrip;
    @FXML
    public Label lblArchivo;
    @FXML
    public Button btnEdit;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnLike;
    @FXML
    public Label lblLikes;
    @FXML
    public TextField txtComentario;
    @FXML
    public VBox boxComentarios;
    @FXML
    public HBox like;
    @FXML
    public HBox coment;
    @FXML
    private Label lblAutor;

    private ControladorPrincipal controladorPrincipal;
    private Contenido contenidoActual;
    public PublicacionesPagControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    private int cantidadLikes = 0;
    private List<String> comentarios = new ArrayList<>();
    private String idUsuario;
    @Override
    public void datosBrainCircle(Object[] parametros) {

    }
    public void setData(Contenido contenido, Integer habil) {
        
        if (contenido == null) {
            System.out.println("El contenido es null");
            return;
        }
        if(habil ==1){
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
        }
        else if(habil ==2){
            btnEdit.setVisible(true);
            btnDelete.setVisible(false);
            like.setVisible(false);
            coment.setVisible(false);
            boxComentarios.setVisible(false);
        }
        String mostrarContenido = contenido.getTipo().toString();
        if (contenido.getTipo().equals("ENLACE")){
            lblArchivo.setStyle("-fx-text-fill: blue; -fx-underline: true;");
            lblArchivo.setOnMouseClicked(e -> abrirEnlace(contenido.getTipo().toString()));
        }
        else if (contenido.getTipo().equals("ARCHIVO") || contenido.getTipo().equals("IMAGEN") || contenido.getTipo().equals("VIDEO")) {
            File f = new File(contenido.getTipo().toString());
            mostrarContenido = f.getName();
            lblArchivo.setCursor(Cursor.HAND);
            lblArchivo.setOnMouseClicked(this::descargarContenido);
        } else {
            lblArchivo.setCursor(Cursor.DEFAULT);
            lblArchivo.setOnMouseClicked(null);
        }
        lblTitulo.setText("Titulo: "+ contenido.getTitulo().toString());
        lblDescrip.setText("Tema: "+ contenido.getTema().toString());
//        lblArchivo.setText("Contenido: "+ contenido.getConte().toString());
        lblArchivo.setText("Contenido: " + mostrarContenido);
        lblLikes.setText(String.valueOf(contenido.getLikes()));
        lblAutor.setText("Autor: "+contenido.getAutor().toString());
        boxComentarios.getChildren().clear();
        List<String> comentariosGuardados = contenido.getComentarios(); // Suponiendo que tienes este método en tu clase Contenido
        if (comentariosGuardados != null && !comentariosGuardados.isEmpty()) {
            for (String comentario : comentariosGuardados) {
                Label lblComentario = new Label("💬 " + comentario);
                boxComentarios.getChildren().add(lblComentario);
            }
        }
        contenidoActual = contenido;
    }

    public void editarConte(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/EditarPublicación.fxml", "Editar Contenido", contenidoActual.getId(), contenidoActual.getAutor());
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void Delete(ActionEvent actionEvent) {
        if (contenidoActual == null) {
            controladorPrincipal.mostrarMensaje("Error", "No hay contenido seleccionado para eliminar", javafx.scene.control.Alert.AlertType.ERROR);
            return;
        }
        boolean eliminado = controladorPrincipal.eliminarContenidoPorId(contenidoActual.getId());

        if (eliminado) {
            controladorPrincipal.mostrarMensaje("Éxito", "Contenido eliminado correctamente", javafx.scene.control.Alert.AlertType.INFORMATION);
            controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/Publicaciones.fxml", "Publicaciones", contenidoActual.getAutor());
            controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo eliminar el contenido", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }

    private void descargarContenido(MouseEvent event) {
        try {
            String rutaOriginal = (String) contenidoActual.getConte();
            File archivoOriginal = new File(rutaOriginal);

            if (!archivoOriginal.exists()) {
                controladorPrincipal.mostrarMensaje("Error", "El archivo no existe: " + rutaOriginal, Alert.AlertType.ERROR);
                return;
            }
            String nombreOriginal = archivoOriginal.getName();
            String extension = "";

            int i = nombreOriginal.lastIndexOf('.');
            if (i > 0) {
                extension = nombreOriginal.substring(i);
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar archivo como...");
            fileChooser.setInitialFileName(nombreOriginal);

            if (contenidoActual.getTipo().equals("IMAGEN")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            } else if (contenidoActual.getTipo().equals("VIDEO")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Videos", "*.mp4", "*.avi", "*.mov"));
            } else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos", "*.*"));
            }

            File destino = fileChooser.showSaveDialog(new Stage());

            if (destino != null) {
                String rutaFinal = destino.getAbsolutePath();
                if (!rutaFinal.toLowerCase().endsWith(extension.toLowerCase())) {
                    destino = new File(rutaFinal + extension);
                }
                Files.copy(archivoOriginal.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                controladorPrincipal.mostrarMensaje("Éxito", "Archivo guardado como: " + destino.getName(), Alert.AlertType.INFORMATION);
            }

        } catch (IOException e) {
            controladorPrincipal.mostrarMensaje("Error", "Ocurrió un error al guardar el archivo", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    private void abrirEnlace(String url) {
        try {
            if (!url.startsWith("http")) {
                url = "https://" + url;
            }
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo abrir el enlace: " + url, javafx.scene.control.Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    @FXML
    public void darLike(ActionEvent event) {
        if (!contenidoActual.getAutor().equals(idUsuario)) {
            boolean exito = controladorPrincipal.darLikeAContenido(contenidoActual.getId(), idUsuario);
            if (exito) {
                lblLikes.setText(controladorPrincipal.obtenerContenidoPorId(contenidoActual.getId()).getLikes() + " Likes");
                btnLike.setDisable(true);
            } else {
                controladorPrincipal.mostrarMensaje("Aviso", "Ya diste like o no se pudo dar like", Alert.AlertType.ERROR);
                btnLike.setDisable(true);
            }
        }
    }



    @FXML
    public void comentar(ActionEvent event) {
        String texto = txtComentario.getText().trim();
        if (!texto.isEmpty()) {
            boolean exito = controladorPrincipal.comentarContenido(contenidoActual.getId(), texto);
            if (exito) {
                Label comentarioLabel = new Label("💬 "+ idUsuario + texto);
                boxComentarios.getChildren().add(comentarioLabel);
                txtComentario.clear();
            } else {
                controladorPrincipal.mostrarMensaje("Error", "No se pudo guardar el comentario", Alert.AlertType.ERROR);
            }
        }
    }
}
