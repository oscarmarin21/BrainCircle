module co.edu.uniquindio.braincircle {
    requires javafx.controls;
    requires javafx.fxml;
    requires demo;
    requires java.desktop;


    opens co.edu.uniquindio.braincircle to javafx.fxml;
    exports co.edu.uniquindio.braincircle;
    exports co.edu.uniquindio.braincircle.controlers;
    opens co.edu.uniquindio.braincircle.controlers to javafx.fxml;
}