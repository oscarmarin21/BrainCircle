package co.edu.uniquindio.braincircle;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.controlers.ControladorPrincipal;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Obtener el controlador principal
        ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

        // Registrar usuario
        String id = "1";
        String nombre = "Anye";
        String correo = "1";
        String telefono = "3210001111";
        String password = "1";
        controlador.registrar(id, nombre, correo, telefono, password);

        // Crear contenidos y agregarlos al sistema
        Contenido<String> c1 = new Contenido<>("1", "Estructuras", "Programación", "PDF", id, "dd");
        Contenido<String> c2 = new Contenido<>("2", "Álgebra", "Matemáticas", "Video", id, "www");
        Contenido<String> c3 = new Contenido<>("3", "Sistemas", "Informática", "Archivo", "Luisa", "www");

        controlador.agregarContenido(c1);
        controlador.agregarContenido(c2);
        controlador.agregarContenido(c3);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 315);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}