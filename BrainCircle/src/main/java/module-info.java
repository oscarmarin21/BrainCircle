module co.edu.uniquindio.braincircle {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.braincircle to javafx.fxml;
    exports co.edu.uniquindio.braincircle;
}