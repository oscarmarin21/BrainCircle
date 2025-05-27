package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Enums.Tipo;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.UUID;

public class PublicacionesAdminControlador {
    @FXML
    public TableView<Contenido> tvPublicaciones;
    @FXML
    public TableColumn<Contenido, String> tcNombre;
    @FXML
    public TableColumn <Contenido, String>tcDescripcion;
    @FXML
    public TableColumn <Contenido, String>tcTipo;
    @FXML
    public TableColumn<Contenido, String> tcArchivo;
    @FXML
    public TextField txtNombre;
//    @FXML
//    public TextField txtDescripcion;
    @FXML
    public ComboBox<Materia> cmbTipo;
    @FXML
    public TextField txtLink;
    @FXML
    public Button btnArchivo;
    @FXML
    public Button btnImagen;
    @FXML
    public Button btnVideo;
    @FXML
    public CheckBox chkArchivo;
    @FXML
    public CheckBox chkImagen;
    @FXML
    public CheckBox chkVideo;
    @FXML
    public CheckBox chkLink;
    @FXML
    public Button btnAgregar;
    private File archivoSeleccionado;
    private File imagenSeleccionada;
    private File videoSeleccionado;
    private Contenido contenidoSeleccionado;
    private ControladorPrincipal controladorPrincipal;

    public PublicacionesAdminControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    public void initialize() {
        chkLink.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkLink, newVal));
        chkArchivo.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkArchivo, newVal));
        chkImagen.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkImagen, newVal));
        chkVideo.selectedProperty().addListener((obs, oldVal, newVal) -> onChange(chkVideo, newVal));
        inicializarValoresTabla();
        dobleClick();
        cmbTipo.getItems().addAll(Materia.values());

    }
    public void inicializarValoresTabla() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().toString()));
        tcDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTema().toString()));
        tcTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        tcArchivo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getConte().toString()));
        inicializarTabla();
    }
    public void inicializarTabla() {
        tvPublicaciones.setItems(FXCollections.observableArrayList(controladorPrincipal.cargarContenidos()));
        tvPublicaciones.refresh();
    }
    public void dobleClick() {
        tvPublicaciones.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    btnAgregar.setDisable(false);
                    System.out.println("Click");
                    contenidoSeleccionado = tvPublicaciones.getSelectionModel().getSelectedItem();
                    mostrarcontenidoSeleccionado();
                }
            }
        });
    }
    public void mostrarcontenidoSeleccionado() {
        if (contenidoSeleccionado != null) {
            txtNombre.setText(contenidoSeleccionado.getAutor().toString());
            cmbTipo.setValue(Materia.valueOf(contenidoSeleccionado.getTema().toString()));
            if(contenidoSeleccionado.getTipo().toString().equals("ARCHIVO")){
                btnArchivo.setText(contenidoSeleccionado.getConte().toString());
                archivoSeleccionado = new File(contenidoSeleccionado.getConte().toString());
                chkArchivo.setSelected(true);
            }
            if(contenidoSeleccionado.getTipo().toString().equals("IMAGEN")){
                btnImagen.setText(contenidoSeleccionado.getConte().toString());
                imagenSeleccionada = new File(contenidoSeleccionado.getConte().toString());
                chkImagen.setSelected(true);
            }
            if(contenidoSeleccionado.getTipo().toString().equals("VIDEO")){
                btnVideo.setText(contenidoSeleccionado.getConte().toString());
                videoSeleccionado = new File(contenidoSeleccionado.getConte().toString());
                chkVideo.setSelected(true);
            }
            if(contenidoSeleccionado.getTipo().toString().equals("ENLACE")) {
                txtLink.setText(contenidoSeleccionado.getConte().toString());
                chkLink.setSelected(true);
            }
        }
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
        cmbTipo.setValue(null);
        txtLink.clear();
        archivoSeleccionado = null;
        imagenSeleccionada = null;
        videoSeleccionado = null;
        btnAgregar.setDisable(true);
        chkLink.setSelected(false);
        chkArchivo.setSelected(false);
        chkImagen.setSelected(false);
        chkVideo.setSelected(false);

        txtLink.setDisable(true);
        btnArchivo.setDisable(true);
        btnImagen.setDisable(true);
        btnVideo.setDisable(true);
        btnImagen.setText("Seleccionar...");
        btnArchivo.setText("Seleccionar...");
        btnVideo.setText("Seleccionar...");
        chkLink.setDisable(false);
        chkArchivo.setDisable(false);
        chkImagen.setDisable(false);
        chkVideo.setDisable(false);
        contenidoSeleccionado = null;
    }

    public void Limpiar(ActionEvent actionEvent) {
        limpiarFormulario();
    }

    public void AgregarConte(ActionEvent actionEvent) {
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
        nuevoContenido.setAutor("Admin");
        nuevoContenido.setConte(contenidoFinal);

        controladorPrincipal.agregarContenido(nuevoContenido);
        inicializarTabla();
        System.out.println("🎉 Contenido creado:");
        System.out.println("Título: " + titulo);
        System.out.println("Tema: " + tema);
        System.out.println("Tipo: " + tipoSeleccionado);
        System.out.println("Ruta/contenido: " + contenidoFinal);

        limpiarFormulario();

    }

    public void ActualizarConte(ActionEvent actionEvent) {
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
        Contenido<String> contenido = controladorPrincipal.obtenerContenidoPorId(contenidoSeleccionado.getId());
        boolean actualizado = controladorPrincipal.actualizarContenido(
                contenidoSeleccionado.getId(), titulo, tema, tipoSeleccionado, contenido.getAutor(), contenidoFinal
        );

        if (actualizado) {
            controladorPrincipal.mostrarMensaje("Éxito", "¡Contenido actualizado correctamente!", Alert.AlertType.INFORMATION);
            tvPublicaciones.refresh();
            limpiarFormulario();
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo actualizar el contenido.", Alert.AlertType.ERROR);
        }
    }

    public void EliminarConte(ActionEvent actionEvent) {
        if (contenidoSeleccionado == null) {
            controladorPrincipal.mostrarMensaje("Error", "No hay contenido seleccionado para eliminar", javafx.scene.control.Alert.AlertType.ERROR);
            return;
        }
        boolean eliminado = controladorPrincipal.eliminarContenidoPorId(contenidoSeleccionado.getId());

        if (eliminado) {
            inicializarTabla();
            limpiarFormulario();
            controladorPrincipal.mostrarMensaje("Éxito", "Contenido eliminado correctamente", javafx.scene.control.Alert.AlertType.INFORMATION);

        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo eliminar el contenido", javafx.scene.control.Alert.AlertType.ERROR);
        }
    }


    public void btnGruposEstudio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void InicioAdmin(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void Usuarios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/UsuariosAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void Solicitudes(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/SolicitudesAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
