package co.edu.uniquindio.braincircle;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.controlers.ControladorPrincipal;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

        controlador.registrar("1", "Anye", "1", "3210001111", "1");
        controlador.registrar("2", "Luisa", "2", "3210002222", "2");
        controlador.registrar("3", "Carlos", "carlos@uniquindio.edu.co", "3210003333", "1234");
        controlador.registrar("4", "Mafe", "mafe@uniquindio.edu.co", "3210004444", "1234");

        Usuario anye = controlador.ObtenerUserAutenticado("1", "1");
        Usuario luisa = controlador.ObtenerUserAutenticado("2", "2");
        Usuario carlos = controlador.ObtenerUserAutenticado("carlos@uniquindio.edu.co", "1234");
        Usuario mafe = controlador.ObtenerUserAutenticado("mafe@uniquindio.edu.co", "1234");

        controlador.conectarUsuarios(anye, luisa);
        controlador.conectarUsuarios(luisa, carlos);
        controlador.conectarUsuarios(luisa, mafe);

        controlador.agregarContenido(new Contenido<>("1", "Estructuras", "Programación", "PDF", anye.getId(), "ruta1.pdf"));
        controlador.agregarContenido(new Contenido<>("2", "Álgebra", "Matemáticas", "Video", anye.getId(), "video.mp4"));
        controlador.agregarContenido(new Contenido<>("3", "Sistemas", "Informática", "Archivo", luisa.getId(), "doc.docx"));

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PanelAdmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 315);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

