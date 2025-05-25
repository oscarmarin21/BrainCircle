package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Enums.Tipo;
import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class PublicacionesControlador implements Parametrizable {
    @FXML
    public ComboBox<Tipo> cmbTipo;
    @FXML
    public TextField txtNombre;
    @FXML
    public GridPane gridPaneContenido;
    @FXML
    public Button bntVolver;
    @FXML
    public Button bntGrupo;

    private ControladorPrincipal controladorPrincipal;
    private String idUsuario;
    private Tipo tipoSeleccionado;
    private List<Contenido> contenidos;

    public PublicacionesControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }



    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista.");
            return;
        }

        idUsuario = parametros[0].toString();
        System.out.println("✅ Usuario autenticado con ID: " + idUsuario);

        // Obtener el tipo seleccionado desde el ComboBox
        Object tipoSeleccionado = cmbTipo.getSelectionModel().getSelectedItem();

        String tipo = tipoSeleccionado != null ? tipoSeleccionado.toString().toLowerCase() : "";

        // Obtener el texto del nombre desde el TextField
        String nombreContenido = txtNombre.getText().toLowerCase();

        // 1. Cargar todos los contenidos
        List<Contenido> todos = controladorPrincipal.cargarContenidos();

        // 2. Filtrar contenidos que no son del usuario actual
        List<Contenido> filtrados = todos.stream()
                .filter(c -> !c.getAutor().equals(idUsuario))
                .toList();

        // 3. Aplicar búsqueda por nombre y tipo sobre los filtrados anteriores
        List<Contenido> filtradosBusqueda = filtrados.stream()
                .filter(c -> nombreContenido.isEmpty() || c.getTitulo().toString().toLowerCase().contains(nombreContenido))
                .filter(c -> tipo.isEmpty() || c.getTipo().toString().toLowerCase().contains(tipo))
                .collect(Collectors.toList());

        // 4. Cargar el contenido filtrado y buscado
        cargarContenidoEnGrid(filtradosBusqueda, 1);
        inicializarTipo();
    }

    /**
     * Método para cargar las publicaciones en el panel del administrador
     */

    void cargarContenidoEnGrid(List<Contenido> contenidos, int tipo) {
        gridPaneContenido.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Contenido contenido : contenidos) {
            try {
                FXMLLoader fxmlLoader = controladorPrincipal.cargarVista("/co/edu/uniquindio/braincircle/PublicacionesPag.fxml");
                if (fxmlLoader != null) {
                    HBox pagContenido = fxmlLoader.getRoot();
                    PublicacionesPagControlador pagConteControlador = fxmlLoader.getController();
                    pagConteControlador.setData(contenido, tipo, idUsuario);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    gridPaneContenido.add(pagContenido, column++, row);
                    GridPane.setMargin(pagContenido, new Insets(10));
                } else {
                    System.out.println("No se pudo cargar la vista para el contenido: " + contenido.getTitulo());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Métodos de buscador
     */

    public void inicializarTipo() {
        cmbTipo.getItems().addAll(Tipo.values());
        listenerTipoEvento();
    }

    // Metodo listener para capturar los datos del tipo de evento
    public void listenerTipoEvento() {
        cmbTipo.setOnAction(event -> {
            tipoSeleccionado = (Tipo) cmbTipo.getValue();

        });
    }

    private void filtrarContenidos() {

        Object tipoSeleccionado = cmbTipo.getSelectionModel().getSelectedItem();
        String tipo = tipoSeleccionado != null ? tipoSeleccionado.toString().toLowerCase() : "";
        String nombreContenido = txtNombre.getText().toLowerCase();

        List<Contenido> contenidosFiltrados = contenidos.stream()
                .filter(contenido ->
                        (nombreContenido.isEmpty() || contenido.getTitulo().toString().toLowerCase().contains(nombreContenido)) &&
                                (tipo.isEmpty() || contenido.getTipo().toString().toLowerCase().contains(tipo))
                )
                .collect(Collectors.toList());

        cargarContenidoEnGrid(contenidosFiltrados, 0);
    }

    /**
     * Métodos de redirección del administrador
     */

    public void añadirMiPublicacon(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/AñadirPublicación.fxml", "Mipublicación", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }

    public void volverAlInicio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/InicioEstudiantes.fxml", "Inicio", idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    public void btnGruposEstudio(ActionEvent actionEvent) {
        controladorPrincipal.navegar("/co/edu/uniquindio/braincircle/GruposEstudioEstudiante.fxml","",idUsuario);
        controladorPrincipal.cerrarVentana((Node) actionEvent.getSource());
    }
    public void misPublicacon(ActionEvent actionEvent) {
        List<Contenido> todosLosContenidos = controladorPrincipal.cargarContenidos();

        List<Contenido> misContenidos = todosLosContenidos.stream()
                .filter(contenido -> contenido.getAutor().equals(idUsuario))
                .toList();
        cargarContenidoEnGrid(misContenidos, 2);
        bntVolver.setVisible(true);
    }

    public void volver(ActionEvent actionEvent) {
        List<Contenido> contenidos = controladorPrincipal.cargarContenidos();
        List<Contenido> filtrados = contenidos.stream()
                .filter(c -> !c.getAutor().equals(idUsuario))
                .toList();
        cargarContenidoEnGrid(filtrados, 1);
        bntVolver.setVisible(false);
    }
}
