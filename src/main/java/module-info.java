module com.example.socialnetwork {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.socialnetwork to javafx.fxml;
    exports com.example.socialnetwork;
}