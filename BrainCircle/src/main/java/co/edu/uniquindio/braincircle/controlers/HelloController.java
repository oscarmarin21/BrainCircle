package co.edu.uniquindio.braincircle.controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void redirigirAInicioSesion(ActionEvent event) {
        // Close the previous window
        Stage ventanaActual = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ventanaActual.close();

        redireccionamiento("/co/edu/uniquindio/braincircle/InicioSesion.fxml");
    }

    public void redirigirARegistro(ActionEvent event) {
        // Close the previous window
        Stage ventanaActual = (Stage) ((Node)event.getSource()).getScene().getWindow();
        ventanaActual.close();

        redireccionamiento("/co/edu/uniquindio/braincircle/Registro.fxml");
    }

    public void redireccionamiento(String ruta) {
        try {
            // Set the resource path as absolute from the classpath root
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Create and show the new window with the login screen
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(scene);
            nuevaVentana.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}