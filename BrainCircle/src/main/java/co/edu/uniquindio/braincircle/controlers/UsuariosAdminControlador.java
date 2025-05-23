package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Nodo.Arista;
import co.edu.uniquindio.braincircle.Nodo.Nodo;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuariosAdminControlador {
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
    private PanelAdminControlador panelAdminControlador;



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
    }

    public void inicializarTabla() {
        tvUsuarios.setItems(FXCollections.observableArrayList(controladorPrincipal.cargarUsuarios()));
        tvUsuarios.refresh();
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
            Circle circle = new Circle(nodo.x, nodo.y, 20, Color.LIGHTBLUE);
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
