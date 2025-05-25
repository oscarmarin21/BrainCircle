package co.edu.uniquindio.braincircle.controlers;

import co.edu.uniquindio.braincircle.Services.Parametrizable;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.Enums.Tipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PublicacionesControlador implements Parametrizable, Initializable {
    @FXML
    public GridPane gridPaneContenido;
    @FXML
    public Button bntVolver;
    @FXML
    public Button bntGrupo;
    @FXML
    public ComboBox<Tipo> comboBoxTipo;
    @FXML
    public TextField textFieldFiltro;
    
    private ControladorPrincipal controladorPrincipal;
    private List<Contenido> contenidosOriginales;
    private int tipoVista = 1; // 1 = todas las publicaciones, 2 = mis publicaciones

    public PublicacionesControlador() throws Exception {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    private String idUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cargar las opciones del enum Tipo en el ComboBox
        comboBoxTipo.getItems().add(null); // Opción para "TODOS"
        comboBoxTipo.getItems().addAll(Tipo.values());
        
        // Configurar el texto mostrado para la opción null
        comboBoxTipo.setButtonCell(new ListCell<Tipo>() {
            @Override
            protected void updateItem(Tipo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("TODOS");
                } else {
                    setText(item.toString());
                }
            }
        });
        
        comboBoxTipo.setCellFactory(listView -> new ListCell<Tipo>() {
            @Override
            protected void updateItem(Tipo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("TODOS");
                } else {
                    setText(item.toString());
                }
            }
        });
        
        // Agregar listeners para filtrado en tiempo real
        comboBoxTipo.setOnAction(e -> aplicarFiltros());
        textFieldFiltro.textProperty().addListener((observable, oldValue, newValue) -> aplicarFiltros());
    }

    @Override
    public void datosBrainCircle(Object... parametros) {
        if (parametros == null || parametros.length == 0 || parametros[0] == null) {
            System.out.println("⚠️ No se enviaron parámetros al cargar la vista.");
            return;
        }
        idUsuario = parametros[0].toString();
        System.out.println("✅ Usuario autenticado con ID: " + idUsuario);
        
        // Cargar todas las publicaciones excepto las del usuario actual
        List<Contenido> todos = controladorPrincipal.cargarContenidos();
        contenidosOriginales = todos.stream()
                .filter(c -> !c.getAutor().equals(idUsuario))
                .collect(Collectors.toList());
        tipoVista = 1;
        
        // Establecer "TODOS" como valor inicial
        comboBoxTipo.setValue(null);
        
        cargarContenidoEnGrid(contenidosOriginales, tipoVista);
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

        contenidosOriginales = todosLosContenidos.stream()
                .filter(contenido -> contenido.getAutor().equals(idUsuario))
                .collect(Collectors.toList());
        tipoVista = 2;
        
        // Limpiar filtros al cambiar a mis publicaciones
        comboBoxTipo.setValue(null); // Esto mostrará "TODOS"
        textFieldFiltro.clear();
        
        cargarContenidoEnGrid(contenidosOriginales, tipoVista);
        bntVolver.setVisible(true);
    }

    public void volver(ActionEvent actionEvent) {
        List<Contenido> contenidos = controladorPrincipal.cargarContenidos();
        contenidosOriginales = contenidos.stream()
                .filter(c -> !c.getAutor().equals(idUsuario))
                .collect(Collectors.toList());
        tipoVista = 1;
        
        // Limpiar filtros al volver
        comboBoxTipo.setValue(null); // Esto mostrará "TODOS"
        textFieldFiltro.clear();
        
        cargarContenidoEnGrid(contenidosOriginales, tipoVista);
        bntVolver.setVisible(false);
    }

    public void buscarPublicaciones(ActionEvent actionEvent) {
        // El filtrado se aplica automáticamente por los listeners, 
        // pero este método puede usarse para acciones adicionales si es necesario
        aplicarFiltros();
    }

    private void aplicarFiltros() {
        if (contenidosOriginales == null) return;
        
        List<Contenido> filtrados = contenidosOriginales.stream()
                .filter(c -> {
                    // Filtro por tipo
                    boolean matchesTipo = comboBoxTipo.getValue() == null || 
                                        c.getTipo().toString().equals(comboBoxTipo.getValue().toString());
                    
                    // Filtro por título o tema
                    String textoBusqueda = textFieldFiltro.getText().toLowerCase().trim();
                    boolean matchesTexto = textoBusqueda.isEmpty() || 
                                         c.getTitulo().toString().toLowerCase().contains(textoBusqueda) ||
                                         c.getTema().toString().toLowerCase().contains(textoBusqueda);
                    
                    return matchesTipo && matchesTexto;
                })
                .collect(Collectors.toList());
        
        cargarContenidoEnGrid(filtrados, tipoVista);
    }
}
