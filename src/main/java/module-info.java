module com.example.geniustask1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires servlet.api;
    requires javax.faces.api;
    requires java.mail;


    opens com.example.geniustask1 to javafx.fxml;
    exports com.example.geniustask1;

}