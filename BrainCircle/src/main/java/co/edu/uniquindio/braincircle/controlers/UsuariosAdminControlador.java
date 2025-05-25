package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Nodo.Arista;
import co.edu.uniquindio.braincircle.Nodo.Nodo;
import co.edu.uniquindio.braincircle.models.GrupoEstudio;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;

public class UsuariosAdminControlador {

    @FXML
    public TextField txtId;
    @FXML
    public TextField txtCorreo;
    @FXML
    public TextField txtTelefono;
    @FXML
    public TextField txtNombre;
    @FXML
    public TextField txtPass;
    @FXML
    public TableView<Usuario> tvUsuarios;
    @FXML
    public TableColumn<Usuario, String> tcNombre;
    @FXML
    public TableColumn <Usuario, String> tcId;
    @FXML
    public TableColumn <Usuario, String>tcCorreo;
    @FXML
    public TableColumn<Usuario, String> tcTelefono;

    @FXML
    private Pane pane;

    private ControladorPrincipal controladorPrincipal;
    private Usuario usuarioSeleccionado;




    @FXML
    public void initialize() {
        inicializarValoresTabla();
        construirGrafo();
    }
    public void inicializarValoresTabla() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre().toString()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo().toString()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono().toString()));
        inicializarTabla();
        dobleClick();
    }

    public void inicializarTabla() {
        tvUsuarios.setItems(FXCollections.observableArrayList(controladorPrincipal.cargarUsuarios()));
        tvUsuarios.refresh();
    }

    public void dobleClick() {
        tvUsuarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    System.out.println("Click");
                    usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
                    mostrarcontenidoSeleccionado();
                }
            }
        });
    }

    public void registrarUsuario(ActionEvent actionEvent) {
        controladorPrincipal.registrar(txtId.getId(), txtNombre.getText(), txtCorreo.getText(),txtTelefono.getText(), txtPass.getText());
        controladorPrincipal.mostrarMensaje("Exito", "Usuario Creado con exito", Alert.AlertType.INFORMATION);
        inicializarTabla();
        limpiarFormulario();
    }

    public void mostrarcontenidoSeleccionado() {
        txtId.setText(usuarioSeleccionado.getId());
        txtNombre.setText(usuarioSeleccionado.getNombre());
        txtCorreo.setText(usuarioSeleccionado.getCorreo());
        txtTelefono.setText(usuarioSeleccionado.getTelefono());
        txtPass.setText(usuarioSeleccionado.getContraseña());
    }

    public void editarUsuario(ActionEvent actionEvent) {
        Usuario usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado == null) {
            controladorPrincipal.mostrarMensaje("Error", "Debes seleccionar un usuario para actualizar", Alert.AlertType.ERROR);
            return;
        }

        boolean actualizado = controladorPrincipal.editarUsuario(
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText(),
                txtPass.getText(),
                usuarioSeleccionado
        );

        if (actualizado) {
            controladorPrincipal.mostrarMensaje("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
            inicializarTabla();
            limpiarFormulario();
        } else {
            controladorPrincipal.mostrarMensaje("Error", "No se pudo actualizar el usuario", Alert.AlertType.ERROR);
        }
    }

    public void eliminarUsuario(ActionEvent actionEvent) {
        if (usuarioSeleccionado == null) {
            controladorPrincipal.mostrarMensaje("Error", "Debes seleccionar un usuario para eliminar", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar el usuario '" + usuarioSeleccionado.getNombre() + "'?", ButtonType.YES, ButtonType.NO);
        confirmacion.showAndWait();

        if (confirmacion.getResult() == ButtonType.YES) {
            boolean eliminado = controladorPrincipal.eliminarGrupo(usuarioSeleccionado.getId());

            if (eliminado) {
                controladorPrincipal.mostrarMensaje("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
                inicializarTabla();
            } else {
                controladorPrincipal.mostrarMensaje("Error", "No se pudo eliminar el usuario", Alert.AlertType.ERROR);
            }
        }
    }

    private void limpiarFormulario() {
        usuarioSeleccionado = null;
        txtId.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPass.clear();
    }

    public void Limpiar(ActionEvent actionEvent) {
        limpiarFormulario();
    }

    public UsuariosAdminControlador()throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void Publicaciones(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PublicacionesAdmin.fxml","Admin Contenido", null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());

    }

    public void construirGrafo() {
        List<Usuario> usuarios = FXCollections.observableArrayList(controladorPrincipal.cargarUsuarios());
        HashMap<String, List<String>> grafo = new HashMap<>();
        for (Usuario usuario : usuarios) {
            List<String> conexiones = new ArrayList<>();
            for (Usuario idConexion : usuario.getConexiones()) {
                conexiones.add(idConexion.getId());
            }
            grafo.put(usuario.getId(), conexiones);
        }

        dibujarGrafo(grafo);
    }


    public void dibujarGrafo(HashMap<String, List<String>> grafo) {
        pane.getChildren().clear(); // Limpia antes de dibujar

        Map<String, Nodo> nodosMap = new HashMap<>();
        List<Arista> conexiones = new ArrayList<>();

        int totalNodos = grafo.size();
        double centroX = 150;
        double centroY = 170 ;
        double radio = 80;

        int index = 0;
        for (String id : grafo.keySet()) {
            double angle = 2 * Math.PI * index / totalNodos;
            double x = centroX + radio * Math.cos(angle);
            double y = centroY + radio * Math.sin(angle);
            Nodo nodo = new Nodo(id, x, y);
            nodosMap.put(id, nodo);
            index++;
        }

        for (Map.Entry<String, List<String>> entry : grafo.entrySet()) {
            Nodo origen = nodosMap.get(entry.getKey());
            for (String destinoId : entry.getValue()) {
                Nodo destino = nodosMap.get(destinoId);
                if (destino != null) {
                    conexiones.add(new Arista(origen, destino));
                }
            }
        }

        // Dibujar aristas
        for (Arista edge : conexiones) {
            Line line = new Line(edge.from.x, edge.from.y, edge.to.x, edge.to.y);
            line.setStroke(Color.GRAY);
            pane.getChildren().add(line);
        }

        // Dibujar nodos
        for (Nodo nodo : nodosMap.values()) {
            Circle circle = new Circle(nodo.x, nodo.y, 20, Color.LIGHTGREEN);
            circle.setStroke(Color.BLACK);
            Text label = new Text(nodo.x - 10, nodo.y + 5, nodo.id);
            pane.getChildren().addAll(circle, label);
        }
    }



    public void btnInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/PanelAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void btnGruposEstudios(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioAdmin.fxml","",null);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
}
