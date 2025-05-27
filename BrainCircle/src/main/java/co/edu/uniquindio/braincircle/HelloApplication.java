package co.edu.uniquindio.braincircle;

import co.edu.uniquindio.braincircle.Arbol.ArbolBinarioContenido;
import co.edu.uniquindio.braincircle.Enums.Materia;
import co.edu.uniquindio.braincircle.Enums.NivelPrioridad;
import co.edu.uniquindio.braincircle.controlers.ControladorPrincipal;
import co.edu.uniquindio.braincircle.models.Contenido;
import co.edu.uniquindio.braincircle.models.Estudiante;
import co.edu.uniquindio.braincircle.models.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

        controlador.registrar("1", "Anye", "1", "3210001111", "1");
        controlador.registrar("2", "Luisa", "2", "3210002222", "2");
        controlador.registrar("3", "Carlos", "carlos@uniquindio.edu.co", "3210003333", "1234");
        controlador.registrar("4", "Mafe", "mafe@uniquindio.edu.co", "3210004444", "1234");
        controlador.registrar("5", "Santiago", "santiago@uniquindio.edu.co", "3210004444", "1234");

        Usuario anye = controlador.ObtenerUserAutenticado("1", "1");
        Usuario luisa = controlador.ObtenerUserAutenticado("2", "2");
        Usuario carlos = controlador.ObtenerUserAutenticado("carlos@uniquindio.edu.co", "1234");
        Usuario mafe = controlador.ObtenerUserAutenticado("mafe@uniquindio.edu.co", "1234");
        Usuario santiago = controlador.ObtenerUserAutenticado("santiago@uniquindio.edu.co", "1234");

        controlador.conectarUsuarios(anye, luisa);
        controlador.conectarUsuarios(luisa, carlos);
        controlador.conectarUsuarios(luisa, mafe);

        controlador.crearGrupoEstudio("g1", "Ingles I", "Grupo de estudio de estructuras básicas de ingles", Materia.INGLES);
        controlador.crearGrupoEstudio("g2", "Estructura", "Grupo de estudio de estructuras básicas de programación", Materia.ESTRUCTURA);
        controlador.crearGrupoEstudio("g3", "Biología I", "Grupo de estudio de teoria fundamental bilogica", Materia.BIOLOGIA);

        controlador.crearSolicitud(anye, NivelPrioridad.ALTA, "Nececito ayuda con una asesoria para ingles", "Hola, me llamo anye, estoy estudiando ingles y necesito ayuda para un taller. Agradesco inmediates");
        controlador.crearSolicitud(santiago, NivelPrioridad.BAJA, "Nececito un grupo de matematicas", "Hola, me llamo santiago, es qeu no encuentro ningun grupo que contenga información de la asgnatura matemáticas");
        controlador.crearSolicitud(carlos, NivelPrioridad.MEDIA, "Nececito ayuda con una asesoria para estructura", "Hola, me llamo carlos, estoy estudiando estructura en la universida y necesito ayuda para un parcial. Gracias por la colaboración");
        controlador.crearSolicitud(mafe, NivelPrioridad.BAJA, "No encuentro un grupo de sociales", "Hola, me llamo mafe, es qeu no encuentro ningun grupo que contenga información de la asgnatura de cieencias sociales");

        controlador.agregarMiembro("g1", (Estudiante) luisa);
        controlador.agregarMiembro("g1", (Estudiante) carlos);
        controlador.agregarMiembro("g1", (Estudiante) anye);
        controlador.agregarMiembro("g1", (Estudiante) mafe);
        controlador.agregarMiembro("g2", (Estudiante) anye);
        controlador.agregarMiembro("g2", (Estudiante) santiago);
        controlador.agregarMiembro("g2", (Estudiante) carlos);

        controlador.enviarMensajeAGrupo("g1", luisa.getId(), "Hola tienen el taller pasado");
        controlador.enviarMensajeAGrupo("g1", mafe.getId(), "Si ya lo mando");
        controlador.enviarMensajeAGrupo("g1", mafe.getId(), "doc.docx");
        controlador.enviarMensajeAGrupo("g1", carlos.getId(), "Hola mando preguntas del listening");
        controlador.enviarMensajeAGrupo("g1", anye.getId(), "Hay un video en youtube que nos puede ayudar");
        controlador.enviarMensajeAGrupo("g2", anye.getId(), "Hola, cómo van con el repositorio");
        controlador.enviarMensajeAGrupo("g2", carlos.getId(), "Bien ya subi los cambios");


        controlador.enviarMensaje(anye, luisa, "Hola me podrias mandar el taller que subieron que se me borro");
        controlador.enviarMensaje(luisa, anye, "Hola, claro ya te lo mando");
        controlador.enviarMensaje(anye, luisa, "¿Cómo te fue en el parcial?");

        controlador.agregarContenido(new Contenido<>("1", "Estructuras", Materia.ESTRUCTURA.toString(), "ARCHIVO", anye.getId(), "/C:/Users/anyie/OneDrive/Documentos/2025-000390.pdf"));
        controlador.agregarContenido(new Contenido<>("2", "Álgebra", Materia.MATEMATICAS.toString(), "VIDEO", anye.getId(), "video.mp4"));
        controlador.agregarContenido(new Contenido<>("3", "Sistemas", Materia.ESTRUCTURA.toString(), "ARCHIVO", luisa.getId(), "C:/Users/anyie/OneDrive/Documentos/2025-000390.pdf"));
        controlador.agregarContenido(new Contenido<>("4", "Ecuaciones", Materia.BIOLOGIA.toString(), "ARCHIVO", mafe.getNombre(), "doc.docx"));


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

