package co.edu.uniquindio.braincircle;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ArbolBinarioContenido<String> arbol = new ArbolBinarioContenido<>();

        Contenido<String> c1 = new Contenido<>("1", "Estructuras", "Programación", "PDF", "Anye");
        Contenido<String> c2 = new Contenido<>("2", "Álgebra", "Matemáticas", "Video", "Pedro");
        Contenido<String> c3 = new Contenido<>("3", "Sistemas", "Informática", "Archivo", "Luisa");

        arbol.agregarContenido(c1);
        arbol.agregarContenido(c2);
        arbol.agregarContenido(c3);

        System.out.println("📚 Contenidos actuales (desde JavaFX):");
        arbol.cargarContenidos();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}