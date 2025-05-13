package co.edu.uniquindio.braincircle;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.models.Contenido;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

        System.out.println("Contenidos actuales (desde JavaFX):");
        arbol.cargarContenidos();
        arbol.actualizarContenido(
                "2",
                "Python Pro",
                "Ciencia de Datos",
                "Video",
                "Profesor Anye"
        );
        List<Contenido<String>> contenidos = arbol.cargarContenidos();
        System.out.println("Contenidos actuales (desde JavaFX):");
        for (Contenido<String> c : contenidos) {
            System.out.println("Título: " + c.getTitulo());
            System.out.println("Autor: " + c.getAutor());
            System.out.println("--------------------");
        }

        boolean fueEliminado = arbol.eliminarContenidoPorId("2");
        System.out.println(fueEliminado ? "Nodo eliminado" : " Nodo no encontrado");

        Contenido<String> contenido = arbol.obtenerContenidoPorId("3");

        if (contenido != null) {
            System.out.println("Contenido encontrado:");
            System.out.println("Título: " + contenido.getTitulo());
            System.out.println("Tema: " + contenido.getTema());
        } else {
            System.out.println("Contenido no encontrado.");
        }
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