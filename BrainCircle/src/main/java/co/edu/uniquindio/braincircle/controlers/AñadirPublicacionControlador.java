package co.edu.uniquindio.braincircle.controlers;
import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Enums.Tipo;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.UUID;

public class AñadirPublicacionControlador implements Parametrizable {
    @FXML
    public ComboBox<Materia> cmbTipo;
    @FXML
    public TextField txtNombre;
//    @FXML
//    public TextField txtDescription;
    @FXML
    public TextField txtLink;
    @FXML
    public Button btnArchivo;
    @FXML
    public Button btnImagen;
    @FXML
    public Button btnVideo;
    @FXML
    public CheckBox chkVideo;
    @FXML
    public CheckBox chkLink;
    @FXML
    public CheckBox chkArchivo;
    @FXML
    public CheckBox chkImagen;

    private File archivoSeleccionado;
    private File imagenSeleccionada;
    private File videoSeleccionado;

    private ControladorPrincipal controladorPrincipal;
    private String idUsuario;

    public AñadirPublicacionControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void volver (ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml", "Inicio", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista.");
            return;
        }

        idUsuario = parametros[0].toString();
        System.out.println("✅ Usuario autenticado con ID: " + idUsuario);
    }

    @FXML
    public void initialize() {
        chkLink.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkLink, newVal));
        chkArchivo.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkArchivo, newVal));
        chkImagen.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkImagen, newVal));
        chkVideo.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkVideo, newVal));
        cmbTipo.getItems().addAll(Materia.values());
    }

    private void onChange(CheckBox checkBoxActivo, boolean isSelected) {
        CheckBox[] todos = {chkLink, chkArchivo, chkImagen, chkVideo};

        // Desactivar otros CheckBox si uno está activo
        for (CheckBox chk : todos) {
            if (chk != checkBoxActivo) {
                chk.setDisable(isSelected);
            }
        }

        // Manejo de campos relacionados
        if (checkBoxActivo == chkLink) {
            txtLink.setDisable(!isSelected);
            if (!isSelected) txtLink.clear();
            btnArchivo.setDisable(true);
            btnImagen.setDisable(true);
            btnVideo.setDisable(true);
        } else if (checkBoxActivo == chkArchivo) {
            btnArchivo.setDisable(!isSelected);
            if (!isSelected) archivoSeleccionado = null;
            txtLink.setDisable(true);
            btnImagen.setDisable(true);
            btnVideo.setDisable(true);
        } else if (checkBoxActivo == chkImagen) {
            btnImagen.setDisable(!isSelected);
            if (!isSelected) imagenSeleccionada = null;
            txtLink.setDisable(true);
            btnArchivo.setDisable(true);
            btnVideo.setDisable(true);
        } else if (checkBoxActivo == chkVideo) {
            btnVideo.setDisable(!isSelected);
            if (!isSelected) videoSeleccionado = null;
            txtLink.setDisable(true);
            btnArchivo.setDisable(true);
            btnImagen.setDisable(true);
        }

        // Si se desactiva el check, reactivar los demás
        if (!isSelected) {
            for (CheckBox chk : todos) chk.setDisable(false);
        }
    }

    public void seleccionarArchivo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");
        archivoSeleccionado = fileChooser.showOpenDialog(new Stage());
        if (archivoSeleccionado != null) {
            btnArchivo.setText(archivoSeleccionado.getName());
            System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
        }
    }

    public void seleccionarImagen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        imagenSeleccionada = fileChooser.showOpenDialog(new Stage());
        if (imagenSeleccionada != null) {
            btnImagen.setText(imagenSeleccionada.getName());
            System.out.println("Imagen seleccionada: " + imagenSeleccionada.getAbsolutePath());
        }
    }

    public void seleccionarVideo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar video");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Videos", "*.mp4", "*.avi", "*.mov")
        );
        videoSeleccionado = fileChooser.showOpenDialog(new Stage());
        if (videoSeleccionado != null) {
            btnVideo.setText(videoSeleccionado.getName());
            System.out.println("Video seleccionado: " + videoSeleccionado.getAbsolutePath());
        }
    }

    public void crearMiPublicacion(ActionEvent actionEvent) {
        if (validarChecksCampos()) {
            return;
        }

        String titulo = txtNombre.getText();
        String tema = cmbTipo.getValue().toString();
        String tipoSeleccionado = "";
        String contenidoFinal = "";

        if (chkLink.isSelected()) {
            tipoSeleccionado = "ENLACE";
            contenidoFinal = txtLink.getText();
        } else if (chkArchivo.isSelected()) {
            tipoSeleccionado = "ARCHIVO";
            contenidoFinal = archivoSeleccionado.getAbsolutePath();
        } else if (chkImagen.isSelected()) {
            tipoSeleccionado = "IMAGEN";
            contenidoFinal = imagenSeleccionada.getAbsolutePath();
        } else if (chkVideo.isSelected()) {
            tipoSeleccionado = "VIDEO";
            contenidoFinal = videoSeleccionado.getAbsolutePath();
        }

        // Crear y agregar contenido
        Contenido<String> nuevoContenido = new Contenido<>();
        nuevoContenido.setId(UUID.randomUUID().toString());
        nuevoContenido.setTitulo(titulo);
        nuevoContenido.setTema(tema);
        nuevoContenido.setTipo(tipoSeleccionado);
        nuevoContenido.setAutor(idUsuario);
        nuevoContenido.setConte(contenidoFinal);

        controladorPrincipal.agregarContenido(nuevoContenido);

        System.out.println("🎉 Contenido creado:");
        System.out.println("Título: " + titulo);
        System.out.println("Tema: " + tema);
        System.out.println("Tipo: " + tipoSeleccionado);
        System.out.println("Ruta/contenido: " + contenidoFinal);
        System.out.println("Autor: " + idUsuario);

        controladorPrincipal.mostrarMensaje("Éxito", "¡Publicación creada correctamente!", Alert.AlertType.INFORMATION);
        limpiarFormulario();
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/Publicaciones.fxml","Publicaciones", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    private boolean validarChecksCampos() {
        if (chkLink.isSelected()) {
            if (txtLink.getText() == null || txtLink.getText().trim().isEmpty()) {
                controladorPrincipal.mostrarMensaje("Cuidado", "¡Debes ingresar un enlace!", Alert.AlertType.WARNING);
                return true;
            }
        } else if (chkArchivo.isSelected()) {
            if (archivoSeleccionado == null) {
                controladorPrincipal.mostrarMensaje("Cuidado", "¡Debes seleccionar un archivo!", Alert.AlertType.WARNING);
                return true;
            }
        } else if (chkImagen.isSelected()) {
            if (imagenSeleccionada == null) {
                controladorPrincipal.mostrarMensaje("Cuidado", "¡Debes seleccionar una imagen!", Alert.AlertType.WARNING);
                return true;
            }
        } else if (chkVideo.isSelected()) {
            if (videoSeleccionado == null) {
                controladorPrincipal.mostrarMensaje("Cuidado", "¡Debes seleccionar un video!", Alert.AlertType.WARNING);
                return true;
            }
        } else {
            controladorPrincipal.mostrarMensaje("Cuidado", "¡Debes seleccionar al menos un tipo de publicación!", Alert.AlertType.WARNING);
            return true;
        }

        return false;
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        cmbTipo.setItems(null);
        txtLink.clear();
        archivoSeleccionado = null;
        imagenSeleccionada = null;
        videoSeleccionado = null;

        chkLink.setSelected(false);
        chkArchivo.setSelected(false);
        chkImagen.setSelected(false);
        chkVideo.setSelected(false);

        txtLink.setDisable(true);
        btnArchivo.setDisable(true);
        btnImagen.setDisable(true);
        btnVideo.setDisable(true);

        chkLink.setDisable(false);
        chkArchivo.setDisable(false);
        chkImagen.setDisable(false);
        chkVideo.setDisable(false);
    }
}
