module com.example.guessnumberappdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.jlink;

    opens com.example.guessnumberappdemo to javafx.fxml;
    exports com.example.guessnumberappdemo;
}