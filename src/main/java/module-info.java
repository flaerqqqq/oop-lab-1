module com.example.librarymanagementservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.librarymanagementservice to javafx.fxml;
    exports com.example.librarymanagementservice;
}