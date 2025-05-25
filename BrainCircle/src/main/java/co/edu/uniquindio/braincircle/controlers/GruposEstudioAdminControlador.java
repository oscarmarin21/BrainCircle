package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.GrupoEstudio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.UUID;

public class GruposEstudioAdminControlador {

    @FXML
    public ComboBox<Materia> cmbMateria;
    @FXML
    public TextArea txtDescripcion1;
    @FXML
    public TextField txtNombre;
    @FXML
    public TableView<GrupoEstudio> tvGruposEstudio;
    @FXML
    public TableColumn<GrupoEstudio, String> tblNombre;
    @FXML
    public TableColumn<GrupoEstudio, String> tblDescr;
    @FXML
    public TableColumn<GrupoEstudio, String> tblUsers;
    private ControladorPrincipal controladorPrincipal;
    private Materia materiaSeleccionada;
    private GrupoEstudio grupoSeleccionado;

    public GruposEstudioAdminControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    public void initialize() {
        inicializarMateria();
        inicializarValoresTabla();
    }

    public void inicializarValoresTabla() {
        tblNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tblDescr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        tblUsers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMateria().toString()));
        inicializarTabla();
        dobleClick();
    }

    public void inicializarTabla() {
        tvGruposEstudio.setItems(FXCollections.observableArrayList(controladorPrincipal.cargarGrupos()));
        tvGruposEstudio.refresh();
    }

    public void Publicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PublicacionesAdmin.fxml", "Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());

    }

    public void dobleClick() {
        tvGruposEstudio.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    System.out.println("Click");
                    grupoSeleccionado = tvGruposEstudio.getSelectionModel().getSelectedItem();
                    mostrarcontenidoSeleccionado();
                }
            }
        });
    }

    public void mostrarcontenidoSeleccionado() {
        txtNombre.setText(grupoSeleccionado.getNombre());
        txtDescripcion1.setText(grupoSeleccionado.getDescripcion());
        cmbMateria.setValue(grupoSeleccionado.getMateria());
    }

    public void Usuarios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/UsuariosAdmin.fxml", "Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void btnInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml", "", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    public void Solicitudes(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/SolicitudesAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void inicializarMateria() {
        cmbMateria.getItems().addAll(Materia.values());
        listenerMateria();
    }

    public void listenerMateria() {
        cmbMateria.setOnAction(event -> {
            materiaSeleccionada = (Materia) cmbMateria.getValue();

        });
    }

    public void cearGrupo(ActionEvent actionEvent) {
        String id = UUID.randomUUID().toString();
        controladorPrincipal.crearGrupoEstudio(id, txtNombre.getText(), txtDescripcion1.getText(), materiaSeleccionada);
        controladorPrincipal.mostrarMensaje("Exito", "Grupo de estudio Creado con exito", Alert.AlertType.INFORMATION);
        inicializarTabla();
        limpiarFormulario();
    }

    public void ActualizarGrupo(ActionEvent actionEvent) {
        GrupoEstudio grupoSeleccionado = tvGruposEstudio.getSelectionModel().getSelectedItem();

        if (grupoSeleccionado == null) {
            controladorPrincipal.mostrarMensaje("Error", "Debes seleccionar un grupo para actualizar", Alert.AlertType.ERROR);
            return;
        }

        boolean actualizado = controladorPrincipal.actualizarGrupo(
                grupoSeleccionado.getIdGrupo(),
                txtNombre.getText(),
                txtDescripcion1.getText(),
                materiaSeleccionada
        );

        if (actualizado) {
            controladorPrincipal.mostrarMensaje("Éxito", "Grupo actualizado correctamente", Alert.AlertType.INFORMATION);
            inicializarTabla();
            limpiarFormulario();
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo actualizar el grupo", Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        grupoSeleccionado = null;
        txtNombre.clear();
        txtDescripcion1.clear();
        cmbMateria.setValue(null);

    }

    public void Limpiar(ActionEvent actionEvent) {
        limpiarFormulario();
    }

    public void eliminarGrupo(ActionEvent actionEvent) {
        if (grupoSeleccionado == null) {
            controladorPrincipal.mostrarMensaje("Error", "Debes seleccionar un grupo para eliminar", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar el grupo '" + grupoSeleccionado.getNombre() + "'?", ButtonType.YES, ButtonType.NO);
        confirmacion.showAndWait();

        if (confirmacion.getResult() == ButtonType.YES) {
            boolean eliminado = controladorPrincipal.eliminarGrupo(grupoSeleccionado.getIdGrupo());

            if (eliminado) {
                controladorPrincipal.mostrarMensaje("Éxito", "Grupo eliminado correctamente", Alert.AlertType.INFORMATION);
                inicializarTabla();
            } else {
                controladorPrincipal.mostrarMensaje("Error", "No se pudo eliminar el grupo", Alert.AlertType.ERROR);
            }
        }
    }

}
